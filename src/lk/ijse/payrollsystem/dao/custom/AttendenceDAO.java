package lk.ijse.payrollsystem.dao.custom;

import lk.ijse.payrollsystem.dao.CrudDAO;
import lk.ijse.payrollsystem.entity.Attendence;

public interface AttendenceDAO extends CrudDAO<Attendence,Integer> {
    public boolean save(String eid,int status) throws Exception;
    public boolean update(String eid) throws Exception;
    public Attendence search(String s) throws Exception;
}
