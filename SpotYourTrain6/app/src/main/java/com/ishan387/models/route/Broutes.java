
package com.ishan387.models.route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broutes {

    private long response_code;
    private List<Route> route = new ArrayList<Route>();
    private Train train;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The route
     */
    public List<Route> getRoute() {
        return route;
    }

    /**
     * 
     * @param route
     *     The route
     */
    public void setRoute(List<Route> route) {
        this.route = route;
    }

    /**
     * 
     * @return
     *     The train
     */
    public Train getTrain() {
        return train;
    }

    /**
     * 
     * @param train
     *     The train
     */
    public void setTrain(Train train) {
        this.train = train;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
