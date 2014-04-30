package com.vipshop.microscope.storage.mysql.condition;

import javax.servlet.http.HttpServletRequest;

public class MsgReportCondition {

    private int year = -1;
    private int month = -1;
    private int week = -1;
    private int day = -1;
    private int hour = -1;

    public MsgReportCondition() {

    }

    public MsgReportCondition(HttpServletRequest request) {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String week = request.getParameter("week");
        String day = request.getParameter("day");

        if (year != null) {
            this.setYear(Integer.valueOf(year));
        }

        if (month != null) {
            this.setMonth(Integer.valueOf(month));
        }

        if (week != null) {
            this.setWeek(Integer.valueOf(week));
        }

        if (day != null) {
            this.setDay(Integer.valueOf(day));
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

}
