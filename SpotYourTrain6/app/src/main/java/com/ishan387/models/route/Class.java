
package com.ishan387.models.route;

import java.util.HashMap;
import java.util.Map;

public class Class {

    private String available;
    private String classCode;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The available
     */
    public String getAvailable() {
        return available;
    }

    /**
     * 
     * @param available
     *     The available
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * 
     * @return
     *     The classCode
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * 
     * @param classCode
     *     The class-code
     */
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
