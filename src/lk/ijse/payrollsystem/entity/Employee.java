package lk.ijse.payrollsystem.entity;

public class Employee {
    private String eid;
    private String empName;
    private String nic;
    private String did;

    public Employee() {
    }

    public Employee(String eid, String empName, String nic, String did) {
        this.eid = eid;
        this.empName = empName;
        this.nic = nic;
        this.did = did;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid='" + eid + '\'' +
                ", empName='" + empName + '\'' +
                ", nic='" + nic + '\'' +
                ", did='" + did + '\'' +
                '}';
    }
}
