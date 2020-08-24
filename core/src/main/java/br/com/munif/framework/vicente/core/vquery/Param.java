package br.com.munif.framework.vicente.core.vquery;

import org.apache.commons.lang3.RandomStringUtils;

public class Param {
    private String key;
    private String field;
    private Object value;
    private Class type;

    public Param() {
    }

    public Param(String key, Object value, Class type) {
        onInit(key, value, type);
    }

    private void onInit(String key, Object value, Class type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public Param(Object value, Class type) {
        onInit(generateParamId(), value, type);
    }


    public Param(String key, Class<?> aClass, Object field) {
        onInit(generateParamId(), value, type);
        this.field = String.valueOf(field);
    }

    public static String generateParamId() {
        return ":" + RandomStringUtils.randomAlphabetic(10).toLowerCase().concat(RandomStringUtils.randomNumeric(10));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public String getKeyToSearch() {
        return key.replace(":", "");
    }

    public Object getValueToSearch() {
        if (String.class.equals(type)) return ((String) value).substring(1, ((String) value).length() - 1);
        if (Integer.class.equals(type)) value = Integer.valueOf(String.valueOf(value));
        if (type.isEnum()) value = Enum.valueOf(type, String.valueOf(value).replace("'",""));
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Param setBuilderValue(Object value) {
        this.value = value;
        return this;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
