package lk.ijse.payrollsystem.entity;

public class MonthlyWorkDays {
    private int month;
    private int year;
    private int noOfWorkDays;

    public MonthlyWorkDays() {
    }

    public MonthlyWorkDays(int month, int year, int noOfWorkDays) {
        this.month = month;
        this.year = year;
        this.noOfWorkDays = noOfWorkDays;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNoOfWorkDays() {
        return noOfWorkDays;
    }

    public void setNoOfWorkDays(int noOfWorkDays) {
        this.noOfWorkDays = noOfWorkDays;
    }

    @Override
    public String toString() {
        return "MonthlyWorkDays{" +
                "month=" + month +
                ", year=" + year +
                ", noOfWorkDays=" + noOfWorkDays +
                '}';
    }
}
