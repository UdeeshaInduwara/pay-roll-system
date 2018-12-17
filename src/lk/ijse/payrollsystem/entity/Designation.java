package lk.ijse.payrollsystem.entity;

public class Designation {
    private String did;
    private String levels;
    private double basicSalary;
    private double medicelAllowence;

    public Designation() {
    }

    public Designation(String did, String levels, double basicSalary, double medicelAllowence) {
        this.did = did;
        this.levels = levels;
        this.basicSalary = basicSalary;
        this.medicelAllowence = medicelAllowence;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getMedicelAllowence() {
        return medicelAllowence;
    }

    public void setMedicelAllowence(double medicelAllowence) {
        this.medicelAllowence = medicelAllowence;
    }

    @Override
    public String toString() {
        return "Designation{" +
                "did='" + did + '\'' +
                ", levels='" + levels + '\'' +
                ", basicSalary=" + basicSalary +
                ", medicelAllowence=" + medicelAllowence +
                '}';
    }
}
