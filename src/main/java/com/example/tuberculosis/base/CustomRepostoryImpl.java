package com.example.tuberculosis.base;

import com.google.common.collect.Iterators;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.query.JpaEntityGraph;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.LongSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created date：2017-08-31
 * @author niezhegang
 */
public class CustomRepostoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T,ID> implements CustomRepostory<T,ID> {

    public static final String querySurroundChar = "~";
    public static final String multiValueSeparator = ",";

    public final static String matchedRegex = "(^[a-zA-Z_$][a-zA-Z0-9_$]*)("+querySurroundChar+".+"+querySurroundChar+")(.+)$";
    public final static Pattern pattern = Pattern.compile(matchedRegex);
    public static ConversionService conversionService;

    private EntityManager entityManager;

    private JpaEntityInformation<T, ?> entityInformation;

    static {
        conversionService = new DefaultConversionService();
        if(conversionService instanceof ConverterRegistry) {
            ConverterRegistry converterRegistry = (ConverterRegistry) conversionService;
            DateFormatterRegistrar.addDateConverters(converterRegistry);
        }
    }

    public CustomRepostoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    public CustomRepostoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public PageResult<T> findAll(Paging paging, List<String> queryString, List<String> orderBy) {
        List<QueryExpression> queryExpressions = transfromFrom(queryString);
        Specification<T> specification = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if(CollectionUtils.isNotEmpty(queryExpressions)) {
                    for(QueryExpression queryExpression : queryExpressions){
                        predicate = cb.and(predicate,queryExpression.toPredicate(root,cb));
                    }
                }
                //处理关联关系
//                Attribute.PersistentAttributeType persistentAttributeType;
//                for(Attribute attribute : root.getModel().getAttributes()){
//                    if(attribute.isAssociation()){
//                       root.join(attribute.getName(),JoinType.LEFT);
//                    }
//                }
                return predicate;
            }
        };
        Pageable pageable = PageUtils.transform(paging,orderBy);
        TypedQuery<T> query = getQuery(specification, pageable);
        //设置查询参数值
        fillParamValues(query,queryExpressions);
        Page<T> page = pageable == null ? new PageImpl<>(query.getResultList())
                : readPage(query, getDomainClass(), pageable, specification,queryExpressions);
        return PageUtils.transformPageResult(page,paging);
    }

    protected <S extends T> Page<S> readPage(TypedQuery<S> query, final Class<S> domainClass, Pageable pageable,
                                             final Specification<S> spec, List<QueryExpression> queryExpressions) {

        query.setFirstResult((int)pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return PageableExecutionUtils.getPage(query.getResultList(), pageable, new LongSupplier() {

            @Override
            public long getAsLong() {
                return executeCountQuery(getCountQuery(spec, domainClass),queryExpressions);
            }
        });
    }

    private Long executeCountQuery(TypedQuery<Long> query, List<QueryExpression> queryExpressions) {

        Assert.notNull(query, "TypedQuery must not be null!");
        fillParamValues(query,queryExpressions);
        List<Long> totals = query.getResultList();
        Long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

    private List<QueryExpression> transfromFrom(List<String> queryStrings){
        List<QueryExpression> queryExpressions = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(queryStrings)) {
            queryStrings.forEach(queryString -> queryExpressions.add(parseTo(queryString)));
        }
        return queryExpressions;
    }

    private void fillParamValues(TypedQuery typedQuery, List<QueryExpression> queryExpressions){
        for (QueryExpression queryExpression : queryExpressions){
            queryExpression.fillParamValue(typedQuery);
        }
    }

    public static QueryExpression parseTo(String queryString) {
        Matcher m = pattern.matcher(queryString);
        String example = "key"+querySurroundChar+"eq"+querySurroundChar+"value";
        if(m.find()){
            String key = m.group(1);
            String op = m.group(2);
            String value = m.group(3);
            try {
                Operator operator = Operator.parse(op);
                return new QueryExpression(key, operator,value);
            }
            catch (IllegalArgumentException e){
                throw new IllegalArgumentException("传入的查询表达式"+queryString+"不合法！",e);
            }
        }
        else {
            throw new IllegalArgumentException("传入的查询表达式"+queryString+"不合法，示例："+example);
        }
    }

//    protected Map<String, Object> getQueryHints() {
//        Method invocationMethod = NearestEntityGraphUtils.getCurrentInvocationMethod();
//        if(invocationMethod != null){
//            EntityGraph entityGraph = findEntityGraph(invocationMethod);
//            if(entityGraph != null) {
//                Map<String, Object> hints = new HashMap<String, Object>();
//                hints.putAll(getRepositoryMethodMetadata().getQueryHints());
//                hints.putAll(Jpa21Utils.tryGetFetchGraphHints(entityManager, getJpaEntityGraph(entityGraph ,invocationMethod), getDomainClass()));
//                return hints;
//            }
//            else {
//                return super.getQueryHints();
//            }
//        }
//        else{
//            return super.getQueryHints();
//        }
//    }

    private JpaEntityGraph getJpaEntityGraph(EntityGraph entityGraph , Method method){
        String fallbackName = entityInformation.getEntityName() + "." + method.getName();
        return new JpaEntityGraph(entityGraph, fallbackName);
    }

    private EntityGraph findEntityGraph(Method method){
        return AnnotatedElementUtils.findMergedAnnotation(method, EntityGraph.class);
    }

    @Getter
    public static class QueryExpression {

        private String key;
        private Operator op;
        private String value;
        private Attribute attribute;
        transient String paramValueKey;

        public QueryExpression(String key,Operator op,String value) {
            this.key = key;
            this.op = op;
            this.value = value;
            paramValueKey = key+Math.abs(com.google.common.base.Objects.hashCode(key,op,value));
        }

        public void fillParamValue(TypedQuery query){
            Attribute.PersistentAttributeType persistentAttributeType = attribute.getPersistentAttributeType();
            Assert.notNull(persistentAttributeType,key+"不为持久化属性！");
            Assert.isTrue(persistentAttributeType == Attribute.PersistentAttributeType.BASIC,"目前不能处理非Basic的属性条件！");
            Class cls = attribute.getJavaType();
            Object paramValue = null;
            if(op == Operator.In || op == Operator.Nin) {
                String[] values = StringUtils.split(value,multiValueSeparator);
                List valueList = new ArrayList<>();
                for(String strValue:values){
                    valueList.add(parseParamValue(strValue,cls));
                }
                paramValue = valueList;
            } else if(op== Operator.IsNull||op== Operator.IsNotNull){
                return;
            }
            else {
                paramValue = parseParamValue(value,cls);
            }
            query.setParameter(paramValueKey,paramValue);
        }

        private Object parseParamValue(String value,Class cls) {
            Object paramValue = null;
            if(ClassUtils.isPrimitiveOrWrapper(cls) ||
                    cls == String.class ||
                    Enum.class.isAssignableFrom(cls) ||
                    cls == BigDecimal.class ||
                    cls == BigInteger.class ){
                paramValue = conversionService.convert(value,cls);
            }
            else if(cls == Date.class ||
                    cls == Calendar.class){
                if(StringUtils.isNumeric(value)){
                    Long longValue = conversionService.convert(value,Long.class);
                    paramValue = conversionService.convert(longValue,cls);
                }
                else {
                    Date date = DateUtils.parse(value);
                    paramValue = conversionService.convert(date,cls);
                }
            }
            else {
                throw new IllegalArgumentException("不支持的参数类型处理"+cls.getName());
            }
            return paramValue;
        }

        public Predicate toPredicate(Root root, CriteriaBuilder cb) {
            Path path = null;
            try {
                attribute = root.getModel().getAttribute(key);
                path = root.get(key);
            }
            catch (IllegalArgumentException e){
                throw new IllegalArgumentException(root.getModel().getName()+"中不存在"+key+"属性",e);
            }
            if(op== Operator.IsNull){
                return cb.isNull(path.as(path.getJavaType()));
            }else if(op== Operator.IsNotNull){
                return cb.isNotNull(path.as(path.getJavaType()));
            }else {
                ParameterExpression parameterExpression = cb.parameter(path.getJavaType(),paramValueKey);
                return op.toPredicate(path,parameterExpression,cb);
            }
        }

        @Override
        public String toString() {
            return "QueryExpression{" +
                    "key='" + key + '\'' +
                    ", op=" + op +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    private enum Operator {
        //等于
        Eq(querySurroundChar+"eq"+querySurroundChar) {
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.equal(path,parameterExpression);
            }
        },
        //不等于
        Neq(querySurroundChar+"neq"+querySurroundChar) {
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.notEqual(path,parameterExpression);
            }
        },
        //包含
        In(querySurroundChar+"in"+querySurroundChar) {
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.in(path).value(cb.parameter(List.class,parameterExpression.getName()));
            }
        },
        Nin(querySurroundChar+"nin"+querySurroundChar) {
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.in(path).value(cb.parameter(List.class,parameterExpression.getName())).not();
            }
        }
        ,
        //大于
        Gt(querySurroundChar+"gt"+querySurroundChar){
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.greaterThan(path,parameterExpression);
            }
        },
        //大于等于
        Ge(querySurroundChar+"ge"+querySurroundChar){
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(path,parameterExpression);
            }
        },
        //小于
        Lt(querySurroundChar+"lt"+querySurroundChar){
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.lessThan(path,parameterExpression);
            }
        },
        //小于等于
        Le(querySurroundChar+"le"+querySurroundChar){
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.lessThanOrEqualTo(path,parameterExpression);
            }
        },
        //为空
        IsNull(querySurroundChar+"isnull"+querySurroundChar){
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.isNull(parameterExpression);
            }
        },
        //不为空
        IsNotNull(querySurroundChar+"isnotnull"+querySurroundChar){
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.isNotNull(parameterExpression);
            }
        },
        //like匹配
        Like(querySurroundChar+"like"+querySurroundChar){
            @Override
            public Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb) {
                return cb.like(path,parameterExpression);
            }
        };

        Operator(String value) {
            this.value = value;
        }

        private String value;

        public String getValue(){
            return value;
        }

        public abstract Predicate toPredicate(Path path, ParameterExpression parameterExpression, CriteriaBuilder cb);

        public static Operator parse(String op) {
            Operator found ;
            List<Operator> operators = Arrays.asList(Operator.values());
            found = Iterators.find(operators.iterator(),new com.google.common.base.Predicate<Operator>(){
                @Override
                public boolean apply(Operator input) {
                    return StringUtils.equalsIgnoreCase(op,input.getValue());
                }
            },null);
            Assert.notNull(found,"传入的操作符["+op+"]不合法!");
            return found;
        }

    }
}
