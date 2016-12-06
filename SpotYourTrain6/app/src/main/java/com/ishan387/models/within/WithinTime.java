
package com.ishan387.models.within;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithinTime {

    private long total;
    private long response_code;
    private String station;
    private List<Train> train = new ArrayList<Train>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    /**
     * 
     * @return
     *     The responseCode
     */
    public long getResponseCode() {
        return response_code;
    }

    /**
     * 
     * @param responseCode
     *     The response_code
     */
    public void setResponseCode(long responseCode) {
        this.response_code = responseCode;
    }

    /**
     * 
     * @return
     *     The station
     */
    public String getStation() {
        return station;
    }

    /**
     * 
     * @param station
     *     The station
     */
    public void setStation(String station) {
        this.station = station;
    }

    /**
     * 
     * @return
     *     The train
     */
    public List<Train> getTrain() {
        return train;
    }

    /**
     * 
     * @param train
     *     The train
     */
    public void setTrain(List<Train> train) {
        this.train = train;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
