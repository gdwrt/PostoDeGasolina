package com.postoGasolina.model;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javafx.scene.control.DatePicker;

public class ConverterDate {
	/**
     * Converte LocalDate para Date
     *
     * @param datePicker
     * @return date
     */
    public static Date toDate(DatePicker datePicker) {
        if(datePicker.getValue() == null){
            return null;
        }
        LocalDate ld = datePicker.getValue();
        Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        return date;
    }
    public static  java.sql.Date toDate(LocalDate localDate) {
        java.sql.Date date = java.sql.Date.valueOf(localDate);
        return date;
    }

    /**
     * Converte Date para LocalDate
     *
     * @param d
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date d) {
        Instant instant = Instant.ofEpochMilli(d.getTime());
        LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
}
