package lk.ijse.payrollsystem.dao.custom;

import lk.ijse.payrollsystem.dao.CrudDAO;
import lk.ijse.payrollsystem.entity.MonthlyWorkDays;

public interface MonthlyWorkDaysDAO extends CrudDAO<MonthlyWorkDays,Integer> {
    public boolean save(int monthNo, int noOfWorkDays) throws Exception;
}
