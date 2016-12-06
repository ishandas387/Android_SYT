
package com.ishan387.models.seat;

import java.util.HashMap;
import java.util.Map;

public class Quota {

    private String quota_name;
    private String quota_code;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The quotaName
     */
    public String getQuotaName() {
        return quota_name;
    }

    /**
     * 
     * @param quotaName
     *     The quota_name
     */
    public void setQuotaName(String quotaName) {
        this.quota_name = quotaName;
    }

    /**
     * 
     * @return
     *     The quotaCode
     */
    public String getQuotaCode() {
        return quota_code;
    }

    /**
     * 
     * @param quotaCode
     *     The quota_code
     */
    public void setQuotaCode(String quotaCode) {
        this.quota_code = quotaCode;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
