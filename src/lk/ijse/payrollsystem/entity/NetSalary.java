package lk.ijse.payrollsystem.entity;

public class NetSalary {
    private int sid;
    private String eid;
    private String monthName;
    private double ot;
    private double noPay;
    private double etf;
    private double epf;
    private double food;
    private double medicle;
    private double attendence;
    private double transport;
    private double salaryAdvance;
    private double total;

    public NetSalary() {
    }

    public NetSalary(int sid, String eid, String monthName, double ot, double noPay, double etf, double epf, double food, double medicle, double attendence, double transport, double salaryAdvance, double total) {
        this.sid = sid;
        this.eid = eid;
        this.monthName = monthName;
        this.ot = ot;
        this.noPay = noPay;
        this.etf = etf;
        this.epf = epf;
        this.food = food;
        this.medicle = medicle;
        this.attendence = attendence;
        this.transport = transport;
        this.salaryAdvance = salaryAdvance;
        this.total = total;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
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

    public double getEtf() {
        return etf;
    }

    public void setEtf(double etf) {
        this.etf = etf;
    }

    public double getEpf() {
        return epf;
    }

    public void setEpf(double epf) {
        this.epf = epf;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public double getMedicle() {
        return medicle;
    }

    public void setMedicle(double medicle) {
        this.medicle = medicle;
    }

    public double getAttendence() {
        return attendence;
    }

    public void setAttendence(double attendence) {
        this.attendence = attendence;
    }

    public double getTransport() {
        return transport;
    }

    public void setTransport(double transport) {
        this.transport = transport;
    }

    public double getSalaryAdvance() {
        return salaryAdvance;
    }

    public void setSalaryAdvance(double salaryAdvance) {
        this.salaryAdvance = salaryAdvance;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NetSalary{" +
                "sid=" + sid +
                ", eid='" + eid + '\'' +
                ", monthName='" + monthName + '\'' +
                ", ot=" + ot +
                ", noPay=" + noPay +
                ", etf=" + etf +
                ", epf=" + epf +
                ", food=" + food +
                ", medicle=" + medicle +
                ", attendence=" + attendence +
                ", transport=" + transport +
                ", salaryAdvance=" + salaryAdvance +
                ", total=" + total +
                '}';
    }
}
