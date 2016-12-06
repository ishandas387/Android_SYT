
package com.ishan387.models.pnr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PnrResult {

    private long response_code;
    private long total_passengers;
    private BoardingPoint boarding_point;
    private String train_name;
    private List<Passenger> passengers = new ArrayList<Passenger>();
    private String chart_prepared;
    private String train_num;
    private String doj;
    private String _class;
    private ReservationUpto reservation_upto;
    private ToStation to_station;
    private FromStation from_station;
    private String pnr;
    private boolean error;
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
     *     The totalPassengers
     */
    public long getTotalPassengers() {
        return total_passengers;
    }

    /**
     * 
     * @param totalPassengers
     *     The total_passengers
     */
    public void setTotalPassengers(long totalPassengers) {
        this.total_passengers = totalPassengers;
    }

    /**
     * 
     * @return
     *     The boardingPoint
     */
    public BoardingPoint getBoardingPoint() {
        return boarding_point;
    }

    /**
     * 
     * @param boardingPoint
     *     The boarding_point
     */
    public void setBoardingPoint(BoardingPoint boardingPoint) {
        this.boarding_point = boardingPoint;
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
     *     The passengers
     */
    public List<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * 
     * @param passengers
     *     The passengers
     */
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    /**
     * 
     * @return
     *     The chartPrepared
     */
    public String getChartPrepared() {
        return chart_prepared;
    }

    /**
     * 
     * @param chartPrepared
     *     The chart_prepared
     */
    public void setChartPrepared(String chartPrepared) {
        this.chart_prepared = chartPrepared;
    }

    /**
     * 
     * @return
     *     The trainNum
     */
    public String getTrainNum() {
        return train_num;
    }

    /**
     * 
     * @param trainNum
     *     The train_num
     */
    public void setTrainNum(String trainNum) {
        this.train_num = trainNum;
    }

    /**
     * 
     * @return
     *     The doj
     */
    public String getDoj() {
        return doj;
    }

    /**
     * 
     * @param doj
     *     The doj
     */
    public void setDoj(String doj) {
        this.doj = doj;
    }

    /**
     * 
     * @return
     *     The _class
     */
    public String getClass_() {
        return _class;
    }

    /**
     * 
     * @param _class
     *     The class
     */
    public void setClass_(String _class) {
        this._class = _class;
    }

    /**
     * 
     * @return
     *     The reservationUpto
     */
    public ReservationUpto getReservationUpto() {
        return reservation_upto;
    }

    /**
     * 
     * @param reservationUpto
     *     The reservation_upto
     */
    public void setReservationUpto(ReservationUpto reservationUpto) {
        this.reservation_upto = reservationUpto;
    }

    /**
     * 
     * @return
     *     The toStation
     */
    public ToStation getToStation() {
        return to_station;
    }

    /**
     * 
     * @param toStation
     *     The to_station
     */
    public void setToStation(ToStation toStation) {
        this.to_station = toStation;
    }

    /**
     * 
     * @return
     *     The fromStation
     */
    public FromStation getFromStation() {
        return from_station;
    }

    /**
     * 
     * @param fromStation
     *     The from_station
     */
    public void setFromStation(FromStation fromStation) {
        this.from_station = fromStation;
    }

    /**
     * 
     * @return
     *     The pnr
     */
    public String getPnr() {
        return pnr;
    }

    /**
     * 
     * @param pnr
     *     The pnr
     */
    public void setPnr(String pnr) {
        this.pnr = pnr;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }



}
