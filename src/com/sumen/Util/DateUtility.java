package com.sumen.Util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateUtility {
    public boolean validateDate(LocalDate startDate, LocalDate endDate) {
        return !endDate.isBefore(startDate);
    }

    public LocalDate readDate(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }
}
