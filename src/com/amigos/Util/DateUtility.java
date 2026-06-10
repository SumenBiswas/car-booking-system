package com.amigos.Util;

import java.time.LocalDate;

public class DateUtility {
    public boolean validateDate(LocalDate startDate, LocalDate endDate){
        if(endDate.isBefore(startDate)){
            System.out.println("End date cannot be before start date");
            return true;
        }
        return false;
    }
}
