package lk.ijse.payrollsystem.entity;

public class YearlyLeaves {
    int year;
    int noOfLeaves;

    public YearlyLeaves() {
    }

    public YearlyLeaves(int year, int noOfLeaves) {
        this.year = year;
        this.noOfLeaves = noOfLeaves;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getNoOfLeaves() {
        return noOfLeaves;
    }

    public void setNoOfLeaves(int noOfLeaves) {
        this.noOfLeaves = noOfLeaves;
    }

    @Override
    public String toString() {
        return "YearlyLeaves{" +
                "year=" + year +
                ", noOfLeaves=" + noOfLeaves +
                '}';
    }
}
