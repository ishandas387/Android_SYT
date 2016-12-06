
package com.ishan387.models.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Autocomplete {

    private long respnse_code;
    private List<String> train = new ArrayList<String>();
    private long total;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The respnseCode
     */
    public long getRespnseCode() {
        return respnse_code;
    }

    /**
     * 
     * @param respnseCode
     *     The respnse_code
     */
    public void setRespnseCode(long respnseCode) {
        this.respnse_code = respnseCode;
    }

    /**
     * 
     * @return
     *     The train
     */
    public List<String> getTrain() {
        return train;
    }

    /**
     * 
     * @param train
     *     The train
     */
    public void setTrain(List<String> train) {
        this.train = train;
    }

    /**
     * 
     * @return
     *     The total
     */
    public long getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(long total) {
        this.total = total;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
