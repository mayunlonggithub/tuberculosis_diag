package com.example.tuberculosis.domain.enums;

/**
 * @author huangyj
 * @date 2019-03-19
 */
public interface EnumValue<K,V> extends BaseEnum<K> {

    public K getKey();

    public V getValue();
}
