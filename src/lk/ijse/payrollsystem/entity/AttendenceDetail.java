package lk.ijse.payrollsystem.entity;

public class AttendenceDetail {
    private int adid;
    private String eid;
    private int monthNo;
    private int yearNo;
    private int currentMonthLeaves;

    public AttendenceDetail() {
    }

    public AttendenceDetail(int adid, String eid, int monthNo, int yearNo, int currentMonthLeaves) {
        this.adid = adid;
        this.eid = eid;
        this.monthNo = monthNo;
        this.yearNo = yearNo;
        this.currentMonthLeaves = currentMonthLeaves;
    }

    public int getAdid() {
        return adid;
    }

    public void setAdid(int adid) {
        this.adid = adid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public int getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(int monthNo) {
        this.monthNo = monthNo;
    }

    public int getYearNo() {
        return yearNo;
    }

    public void setYearNo(int yearNo) {
        this.yearNo = yearNo;
    }

    public int getCurrentMonthLeaves() {
        return currentMonthLeaves;
    }

    public void setCurrentMonthLeaves(int currentMonthLeaves) {
        this.currentMonthLeaves = currentMonthLeaves;
    }

    @Override
    public String toString() {
        return "AttendenceDetail{" +
                "adid=" + adid +
                ", eid='" + eid + '\'' +
                ", monthNo=" + monthNo +
                ", yearNo=" + yearNo +
                ", currentMonthLeaves=" + currentMonthLeaves +
                '}';
    }
}
