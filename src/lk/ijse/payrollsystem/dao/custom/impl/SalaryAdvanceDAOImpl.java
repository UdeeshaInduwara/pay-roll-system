package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.SalaryAdvanceDAO;
import lk.ijse.payrollsystem.entity.SalaryAdvance;

import java.sql.ResultSet;
import java.util.ArrayList;

public class SalaryAdvanceDAOImpl implements SalaryAdvanceDAO {

    @Override
    public boolean save(String eid, double advance) throws Exception {
        return CrudUtil.executeUpdate("call setSalaryAdvance(?,?)",eid,advance) > 0;
    }

    @Override
    public boolean update(int said, double advance) throws Exception {
        return CrudUtil.executeUpdate("call updateSalaryAdvance(?,?)",said,advance) > 0;
    }


    @Override
    public SalaryAdvance search(String eid) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call searchSalaryAdvance(?)",eid);
        if (rst.next()){
            return new SalaryAdvance(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getDate(4),rst.getDouble(5));
        }else {
            return null;
        }
    }

    @Override
    public boolean save(SalaryAdvance entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(SalaryAdvance entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Integer s) throws Exception {
        return CrudUtil.executeUpdate("call deleteSalaryAdvance(?)",s) > 0;
    }

    @Override
    public SalaryAdvance search(Integer s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<SalaryAdvance> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewSalaryAdvance()");
        ArrayList<SalaryAdvance> saList=new ArrayList<>();
        while (rst.next()){
            saList.add(new SalaryAdvance(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getDate(4),rst.getDouble(5)));
        }
        return saList;
    }
}
