/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se3prac8;

import java.util.Scanner;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import org.joda.time.Days;
import org.joda.time.Years;

/**
 *
 * @author Sebastian
 */
public class SE3Prac8 {

    private static DateTime unixTime = new DateTime(1970, 1, 1, 0, 0);
    private static int secInDay = 86400;

    /**
     * @param args the command line arguments
     */
    private enum Month {

        January, February, March, April, May, June, July, August, September, October, November, December
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String line = s.nextLine();
        DateTime parsedDate = null;
        // TODO code application logic here
        if (charCheck(line)) {
            if (line.indexOf(".") != -1) {
                parsedDate = parseYFrac(line);
            } else {
                parsedDate = d_wM_y(line);
            }
        } else {
            if (line.length() >= 8) {
                parsedDate = parseYMD(line);
            } else if (line.length() <= 4) {
                System.out.println("IsRunning");
                parsedDate = new DateTime(Integer.parseInt(line), 1, 1, 0, 0);
            } else {
                System.out.println("Error");
            }
        }
        Days days;

        days = Days.daysBetween(unixTime, parsedDate);
        long secondsPassed = days.getDays() * secInDay;

        System.out.println(secondsPassed);
    }

    private static DateTime parseYFrac(String line) {
        Scanner scanPeriod = new Scanner(line);
        scanPeriod.useDelimiter(".");
        System.out.println(line);
        if (line.indexOf(".") == 4) {
            int year = Integer.parseInt(line.substring(0, 4));
            int frac = Integer.parseInt(line.substring(5));
            DateTime t = new DateTime(year, 1, 1, 0, 0);
            t.plusDays(365 * frac);
            return t;
        } else {
            System.out.println("error");
            return null;
        }

    }

    private static DateTime parseYMD(String line) {
        int year = Integer.parseInt(line.substring(0, 4));
        int month = Integer.parseInt(line.substring(4, 6));
        int day = Integer.parseInt(line.substring(6, 8));

        if (month > 12 && day > 12) {
            //swap year with md
            int oldYear = year;
            year = month * 100 + day;
            month = oldYear / 100;
            day = oldYear % 100;
        }

        if (month > 12) {
            if (day > 12) {

            } else {
                int temp = month;
                month = day;
                day = temp;
            }
        }

        return new DateTime(year, month, day, 0, 0);
    }

    private static boolean charCheck(String dateInput) {
        for (int i = 0; i < dateInput.length(); i++) {
            if (dateInput.charAt(i) > '9' || dateInput.charAt(i) < '0') {
                return true;
            }
        }
        return false;
    }

    private static DateTime d_wM_y(String line) {

        Scanner lineScan = new Scanner(line);
        //read day of month
        int dayNum = lineScan.nextInt();
        //read month name
        String monthName = lineScan.next();
        int monthNum = toMonthNum(monthName);
        //read year
        int yearNum = lineScan.nextInt();

        DateTime t = new DateTime(yearNum, monthNum, dayNum, 0, 0);

        return t;

    }

    private static int toMonthNum(String monthName) {
        for (Month m : Month.values()) {
            if (monthName.equalsIgnoreCase(m.toString())) {
                return m.ordinal() + 1;
            }
        }
        return -1;
    }

}
