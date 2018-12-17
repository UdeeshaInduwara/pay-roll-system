package lk.ijse.payrollsystem.dao.custom;

import lk.ijse.payrollsystem.dao.CrudDAO;
import lk.ijse.payrollsystem.entity.YearlyLeaves;

public interface YearlyLeavesDAO extends CrudDAO<YearlyLeaves,Integer> {
    public boolean save(int yearNo, int noOfLeaves) throws Exception;
}
