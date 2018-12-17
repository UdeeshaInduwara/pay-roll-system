package lk.ijse.payrollsystem.dao.custom;

import lk.ijse.payrollsystem.dao.CrudDAO;
import lk.ijse.payrollsystem.entity.SalaryAdvance;

public interface SalaryAdvanceDAO extends CrudDAO<SalaryAdvance,Integer> {
    public boolean save(String eid,double advance) throws Exception;
    public boolean update(int said,double advance) throws Exception;
    public SalaryAdvance search(String eid) throws Exception;
}
