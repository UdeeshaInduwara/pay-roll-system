package lk.ijse.payrollsystem.entity;

public class Allowence {
    private String alid;
    private String types;
    private double pecentage;

    public Allowence() {
    }

    public Allowence(String alid, String types, double pecentage) {
        this.alid = alid;
        this.types = types;
        this.pecentage = pecentage;
    }

    public String getAlid() {
        return alid;
    }

    public void setAlid(String alid) {
        this.alid = alid;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public double getPecentage() {
        return pecentage;
    }

    public void setPecentage(double pecentage) {
        this.pecentage = pecentage;
    }

    @Override
    public String toString() {
        return "Allowence{" +
                "alid='" + alid + '\'' +
                ", types='" + types + '\'' +
                ", pecentage=" + pecentage +
                '}';
    }
}
