package com.bank.tcmb.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TCMBRequest implements Serializable {

    private String from;
    private String to;
    private BigDecimal amount;
    private Boolean error;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }
}
