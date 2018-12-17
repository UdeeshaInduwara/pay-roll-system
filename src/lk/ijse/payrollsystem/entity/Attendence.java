package lk.ijse.payrollsystem.entity;


import java.sql.Date;

public class Attendence {
    private int atid;
    private String eid;
    private String employeeName;
    private String status;
    private String intime;
    private String outtime;
    private Date currentDate;
    private double ot;
    private double noPay;

    public Attendence() {
    }

    public Attendence(int atid, String eid, String employeeName, String status, String intime, String outtime, Date currentDate, double ot, double noPay) {
        this.atid = atid;
        this.eid = eid;
        this.employeeName = employeeName;
        this.status = status;
        this.intime = intime;
        this.outtime = outtime;
        this.currentDate = currentDate;
        this.ot = ot;
        this.noPay = noPay;
    }

    public int getAtid() {
        return atid;
    }

    public void setAtid(int atid) {
        this.atid = atid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public double getOt() {
        return ot;
    }

    public void setOt(double ot) {
        this.ot = ot;
    }

    public double getNoPay() {
        return noPay;
    }

    public void setNoPay(double noPay) {
        this.noPay = noPay;
    }

    @Override
    public String toString() {
        return "Attendence{" +
                "atid=" + atid +
                ", eid='" + eid + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", status='" + status + '\'' +
                ", intime='" + intime + '\'' +
                ", outtime='" + outtime + '\'' +
                ", currentDate=" + currentDate +
                ", ot=" + ot +
                ", noPay=" + noPay +
                '}';
    }
}
