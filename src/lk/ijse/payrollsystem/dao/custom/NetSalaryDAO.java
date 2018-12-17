package lk.ijse.payrollsystem.dao.custom;

import lk.ijse.payrollsystem.dao.CrudDAO;
import lk.ijse.payrollsystem.entity.NetSalary;

public interface NetSalaryDAO extends CrudDAO<NetSalary,Integer> {
    public boolean save(String id) throws Exception;
    public NetSalary search(String eid) throws Exception;
}
