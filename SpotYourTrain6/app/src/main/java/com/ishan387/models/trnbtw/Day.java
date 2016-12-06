
package com.ishan387.models.trnbtw;

import java.util.HashMap;
import java.util.Map;

public class Day {

    private String runs;
    private String day_code;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The runs
     */
    public String getRuns() {
        return runs;
    }

    /**
     * 
     * @param runs
     *     The runs
     */
    public void setRuns(String runs) {
        this.runs = runs;
    }

    /**
     * 
     * @return
     *     The dayCode
     */
    public String getDayCode() {
        return day_code;
    }

    /**
     * 
     * @param dayCode
     *     The day-code
     */
    public void setDayCode(String dayCode) {
        this.day_code = dayCode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
