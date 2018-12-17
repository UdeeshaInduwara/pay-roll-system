package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.YearlyLeavesDAO;
import lk.ijse.payrollsystem.entity.YearlyLeaves;

import java.sql.ResultSet;
import java.util.ArrayList;

public class YearlyLeavesDAOImpl implements YearlyLeavesDAO {
    @Override
    public boolean save(YearlyLeaves entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(YearlyLeaves entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws Exception {
        return CrudUtil.executeUpdate("call deleteYearlyLeaves(?)",integer) > 0;
    }

    @Override
    public YearlyLeaves search(Integer integer) throws Exception {
        return null;
    }

    @Override
    public ArrayList<YearlyLeaves> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewYearlyLeaves()");
        ArrayList<YearlyLeaves> yldList=new ArrayList<>();
        while (rst.next()){
            yldList.add(new YearlyLeaves(rst.getInt(1),rst.getInt(2)));
        }
        return yldList;
    }

    @Override
    public boolean save(int yearNo, int noOfLeaves) throws Exception {
        return CrudUtil.executeUpdate("call addYearlyLeaves(?,?)",yearNo,noOfLeaves) > 0;
    }
}
