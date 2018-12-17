package lk.ijse.payrollsystem.entity;

import java.time.LocalDate;

public class DesignationDetail {
    private LocalDate dateSince;
    private String did;
    private String eid;

    public DesignationDetail() {
    }

    public DesignationDetail(LocalDate dateSince, String did, String eid) {
        this.dateSince = dateSince;
        this.did = did;
        this.eid = eid;
    }

    public LocalDate getDateSince() {
        return dateSince;
    }

    public void setDateSince(LocalDate dateSince) {
        this.dateSince = dateSince;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    @Override
    public String toString() {
        return "DesignationDetail{" +
                "dateSince=" + dateSince +
                ", did='" + did + '\'' +
                ", eid='" + eid + '\'' +
                '}';
    }
}
