
package com.ishan387.models.trnbtw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Train {

    private List<Day> days = new ArrayList<Day>();
    private String number;
    private long no;
    private String dest_arrival_time;
    private String name;
    private To to;
    private From from;
    private String src_departure_time;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The days
     */
    public List<Day> getDays() {
        return days;
    }

    /**
     * 
     * @param days
     *     The days
     */
    public void setDays(List<Day> days) {
        this.days = days;
    }

    /**
     * 
     * @return
     *     The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(String number) {
        this.number = number;
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
     *     The destArrivalTime
     */
    public String getDestArrivalTime() {
        return dest_arrival_time;
    }

    /**
     * 
     * @param destArrivalTime
     *     The dest_arrival_time
     */
    public void setDestArrivalTime(String destArrivalTime) {
        this.dest_arrival_time = destArrivalTime;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
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
     *     The srcDepartureTime
     */
    public String getSrcDepartureTime() {
        return src_departure_time;
    }

    /**
     * 
     * @param srcDepartureTime
     *     The src_departure_time
     */
    public void setSrcDepartureTime(String srcDepartureTime) {
        this.src_departure_time = srcDepartureTime;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
