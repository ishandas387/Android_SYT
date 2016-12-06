
package com.ishan387.models.pnr;

import java.util.HashMap;
import java.util.Map;

public class Passenger {

    private long no;
    private String booking_status;
    private String current_status;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The bookingStatus
     */
    public String getBookingStatus() {
        return booking_status;
    }

    /**
     * 
     * @param bookingStatus
     *     The booking_status
     */
    public void setBookingStatus(String bookingStatus) {
        this.booking_status = bookingStatus;
    }

    /**
     * 
     * @return
     *     The currentStatus
     */
    public String getCurrentStatus() {
        return current_status;
    }

    /**
     * 
     * @param currentStatus
     *     The current_status
     */
    public void setCurrentStatus(String currentStatus) {
        this.current_status = currentStatus;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
