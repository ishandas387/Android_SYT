
package com.ishan387.models.seat;

import java.util.HashMap;
import java.util.Map;

public class Class {

    private String class_name;
    private String class_code;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The className
     */
    public String getClassName() {
        return class_name;
    }

    /**
     * 
     * @param className
     *     The class_name
     */
    public void setClassName(String className) {
        this.class_name = className;
    }

    /**
     * 
     * @return
     *     The classCode
     */
    public String getClassCode() {
        return class_code;
    }

    /**
     * 
     * @param classCode
     *     The class_code
     */
    public void setClassCode(String classCode) {
        this.class_code = classCode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
