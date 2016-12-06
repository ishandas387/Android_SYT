
package com.ishan387.models;

import java.util.HashMap;
import java.util.Map;

public class Route {

    private String actdep;
    private String station;
    private long no;
    private String actarr;
    private String status;
    private String schdep;
    private String scharr;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private Station_ station_;

    public void setStation_(Station_ station_) {
        this.station_ = station_;
    }

    public Station_ getStation_() {

        return station_;
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
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
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
