package com.example.tuberculosis.domain.enums;



import javax.persistence.AttributeConverter;

/**
 * @author huangyj
 * @date 2019-03-19
 */
public class BaseValue implements EnumValue<String,String> {
    private String key;
    private String value;


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public BaseValue() {
    }

    public BaseValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public BaseValue(String key) {
        this.key = key;

    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public static class BaseValueConvert implements AttributeConverter<BaseValue, String> {

        @Override
        public String convertToDatabaseColumn(BaseValue baseValue) {
            if (baseValue != null) {
                if (baseValue.getKey() != null) {
                    return baseValue.getKey();
                }
            }
            return null;
        }

        @Override
        public BaseValue convertToEntityAttribute(String s) {
            return new BaseValue(s);
        }
    }
}
