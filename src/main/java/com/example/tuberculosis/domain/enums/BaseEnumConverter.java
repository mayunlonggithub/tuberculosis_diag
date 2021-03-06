package com.example.tuberculosis.domain.enums;

import javax.persistence.AttributeConverter;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseEnum与数据库转换器
 * 使用方法 根据定义的枚举类型 创建一个转换器类继承此类
 * 并实现默认无参构造函数
 *
 * @Date: 2018/5/22
 * @Auther: chenxiao
 * @Description:枚举与数据库映射关系
 */
public abstract class BaseEnumConverter<T extends BaseEnum<K>, K> implements AttributeConverter<BaseEnum<K>, K> {

    private Map<K, T> enumMap = new HashMap<>();

    public BaseEnumConverter(Class<T> enumClass) {
        T[] enums = enumClass.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.getKey(), e);
        }
    }

    @Override
    public K convertToDatabaseColumn(BaseEnum<K> attribute) {
        return attribute == null ? null : attribute.getKey();
    }

    @Override
    public T convertToEntityAttribute(K dbData) {
        if (dbData == null) {
            return null;
        }
        T result = enumMap.get(dbData);
//        if (result == null) {
//            throw new IllegalArgumentException("No element matches " + dbData);
//        }
        return result;
    }

}
