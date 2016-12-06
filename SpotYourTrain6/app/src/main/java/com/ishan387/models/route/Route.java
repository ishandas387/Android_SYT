
package com.ishan387.models.route;

import java.util.HashMap;
import java.util.Map;

public class Route {

    private double lat;
    private long route;
    private double lng;
    private String fullname;
    private String scharr;
    private String state;
    private long distance;
    private long day;
    private long halt;
    private String schdep;
    private String code;
    private long no;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * 
     * @param lat
     *     The lat
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * 
     * @return
     *     The route
     */
    public long getRoute() {
        return route;
    }

    /**
     * 
     * @param route
     *     The route
     */
    public void setRoute(long route) {
        this.route = route;
    }

    /**
     * 
     * @return
     *     The lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * 
     * @param lng
     *     The lng
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * 
     * @return
     *     The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * 
     * @param fullname
     *     The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    /**
     * 
     * @return
     *     The state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The distance
     */
    public long getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    public void setDistance(long distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The day
     */
    public long getDay() {
        return day;
    }

    /**
     * 
     * @param day
     *     The day
     */
    public void setDay(long day) {
        this.day = day;
    }

    /**
     * 
     * @return
     *     The halt
     */
    public long getHalt() {
        return halt;
    }

    /**
     * 
     * @param halt
     *     The halt
     */
    public void setHalt(long halt) {
        this.halt = halt;
    }

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
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The no
     */
    public long getNo() {
        return no;
    }

    /**
     * 
     * @param no
     *     The no
     */
    public void setNo(long no) {
        this.no = no;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
