package lk.ijse.payrollsystem.entity;

public class Rates {
    private String types;
    private double percentage;

    public Rates() {
    }

    public Rates(String types, double percentage) {
        this.types = types;
        this.percentage = percentage;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Rates{" +
                "types='" + types + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
