
package com.ishan387.models.within;

import java.util.HashMap;
import java.util.Map;

public class Train {

    private String schdep;
    private String name;
    private String number;
    private String actdep;
    private String actarr;
    private String scharr;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The schdep
     */
    public String getSchdep() {
        return schdep;
    }

    /**
     * 
     * @param schdep
     *     The schdep
     */
    public void setSchdep(String schdep) {
        this.schdep = schdep;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The actdep
     */
    public String getActdep() {
        return actdep;
    }

    /**
     * 
     * @param actdep
     *     The actdep
     */
    public void setActdep(String actdep) {
        this.actdep = actdep;
    }

    /**
     * 
     * @return
     *     The actarr
     */
    public String getActarr() {
        return actarr;
    }

    /**
     * 
     * @param actarr
     *     The actarr
     */
    public void setActarr(String actarr) {
        this.actarr = actarr;
    }

    /**
     * 
     * @return
     *     The scharr
     */
    public String getScharr() {
        return scharr;
    }

    /**
     * 
     * @param scharr
     *     The scharr
     */
    public void setScharr(String scharr) {
        this.scharr = scharr;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
