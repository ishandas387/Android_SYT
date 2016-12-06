
package com.ishan387.models.seat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Seatavail {

    private From from;
    private To to;
    private Class _class;
    private String train_name;
    private long response_code;
    private List<Availability> availability = new ArrayList<Availability>();
    private Quota quota;
    private boolean error;
    private String train_number;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The from
     */
    public From getFrom() {
        return from;
    }

    /**
     * 
     * @param from
     *     The from
     */
    public void setFrom(From from) {
        this.from = from;
    }

    /**
     * 
     * @return
     *     The to
     */
    public To getTo() {
        return to;
    }

    /**
     * 
     * @param to
     *     The to
     */
    public void setTo(To to) {
        this.to = to;
    }

    /**
     * 
     * @return
     *     The _class
     */
    public Class getClass_() {
        return _class;
    }

    /**
     * 
     * @param _class
     *     The class
     */
    public void setClass_(Class _class) {
        this._class = _class;
    }

    /**
     * 
     * @return
     *     The trainName
     */
    public String getTrainName() {
        return train_name;
    }

    /**
     * 
     * @param trainName
     *     The train_name
     */
    public void setTrainName(String trainName) {
        this.train_name = trainName;
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
     *     The availability
     */
    public List<Availability> getAvailability() {
        return availability;
    }

    /**
     * 
     * @param availability
     *     The availability
     */
    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }

    /**
     * 
     * @return
     *     The quota
     */
    public Quota getQuota() {
        return quota;
    }

    /**
     * 
     * @param quota
     *     The quota
     */
    public void setQuota(Quota quota) {
        this.quota = quota;
    }

    /**
     * 
     * @return
     *     The error
     */
    public boolean isError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * 
     * @return
     *     The trainNumber
     */
    public String getTrainNumber() {
        return train_number;
    }

    /**
     * 
     * @param trainNumber
     *     The train_number
     */
    public void setTrainNumber(String trainNumber) {
        this.train_number = trainNumber;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
