package lk.ijse.payrollsystem.entity;

import java.sql.Date;

public class SalaryAdvance {
    private int said;
    private String eid;
    private String employeeName;
    private Date currentDate;
    private double advance;

    public SalaryAdvance() {
    }

    public SalaryAdvance(int said, String eid, String employeeName, Date currentDate, double advance) {
        this.said = said;
        this.eid = eid;
        this.employeeName = employeeName;
        this.currentDate = currentDate;
        this.advance = advance;
    }

    public int getSaid() {
        return said;
    }

    public void setSaid(int said) {
        this.said = said;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public double getAdvance() {
        return advance;
    }

    public void setAdvance(double advance) {
        this.advance = advance;
    }

    @Override
    public String toString() {
        return "SalaryAdvance{" +
                "said=" + said +
                ", eid='" + eid + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", currentDate=" + currentDate +
                ", advance=" + advance +
                '}';
    }
}
