package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.MonthlyWorkDaysDAO;
import lk.ijse.payrollsystem.entity.MonthlyWorkDays;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MonthlyWorkDaysDAOImpl implements MonthlyWorkDaysDAO {
    @Override
    public boolean save(MonthlyWorkDays entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(MonthlyWorkDays entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Integer s) throws Exception {
        return CrudUtil.executeUpdate("call deleteMonthlyWorkDays(?)",s) > 0;
    }

    @Override
    public MonthlyWorkDays search(Integer s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<MonthlyWorkDays> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewMonthlyWorkDays()");
        ArrayList<MonthlyWorkDays> mwdList=new ArrayList<>();
        while (rst.next()){
            mwdList.add(new MonthlyWorkDays(rst.getInt(1),rst.getInt(2),rst.getInt(3)));
        }
        return mwdList;
    }

    @Override
    public boolean save(int monthNo, int noOfWorkDays) throws Exception {
        return CrudUtil.executeUpdate("call addMonthlyWorkDays(?,?)",monthNo,noOfWorkDays) > 0;
    }
}
