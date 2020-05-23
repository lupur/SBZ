package com.sbz.agro.dto;

import java.util.Date;

public class ReadingDto {

    public String serialNo;
    public String name;
    public String value;
    public Date timestamp;

    public ReadingDto() {
        super();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNumber(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ReadingDto [serialNo=" + serialNo + ", name=" + name + ", value=" + value + ", timestamp=" + timestamp
                + "]";
    }

    public boolean isValid() {
        if (serialNo == null || name == null || value == null || timestamp == null)
            return false;
        if (serialNo.isEmpty() || name.isEmpty() || value.isEmpty())
            return false;
        return true;
    }

}
