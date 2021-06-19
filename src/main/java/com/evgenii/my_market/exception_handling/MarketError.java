package com.evgenii.my_market.exception_handling;

import lombok.Data;

import java.util.Date;

/**
 * Market error class for exceptions.
 *
 * @author Boznyakov Evgenii
 */
@Data
public class MarketError {
    private int status;
    private String message;
    private Date timestamp;

    /**
     * Constructor for creating new instance of this class.
     *
     * @param status status of error
     * @param message message of error
     */
    public MarketError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
