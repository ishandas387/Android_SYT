
package com.ishan387.models.stationname;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationName {

    private long total;
    private long response_code;
    private List<Station> station = new ArrayList<Station>();
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
    public List<Station> getStation() {
        return station;
    }

    /**
     * 
     * @param station
     *     The station
     */
    public void setStation(List<Station> station) {
        this.station = station;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
