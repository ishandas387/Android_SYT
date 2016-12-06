
package com.ishan387.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Running {

    private String train_number;
    private long response_code;
    private List<Route> route = new ArrayList<Route>();
    private long total;
    private Object position;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();





    /**
     * 
     * @return
     *     The trainNumber
     */
    public String getTrain_number() {
        return train_number;
    }

    /**
     * 
     * @param train_number
     *     The train_number
     */
    public void setTrain_number(String train_number) {
        this.train_number = train_number;
    }

    /**
     * 
     * @return
     *     The responseCode
     */
    public long getResponse_code() {
        return response_code;
    }

    /**
     * 
     * @param response_code
     *     The response_code
     */
    public void setResponse_code(long response_code) {
        this.response_code = response_code;
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
     *     The position
     */
    public Object getPosition() {
        return position;
    }

    /**
     * 
     * @param position
     *     The position
     */
    public void setPosition(Object position) {
        this.position = position;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
