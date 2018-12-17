package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.AttendenceDetailDAO;
import lk.ijse.payrollsystem.entity.AttendenceDetail;
import lk.ijse.payrollsystem.entity.MonthlyWorkDays;
import lk.ijse.payrollsystem.entity.YearlyLeaves;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AttendenceDetailDAOImpl implements AttendenceDetailDAO {
    @Override
    public boolean save(AttendenceDetail entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(AttendenceDetail entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public AttendenceDetail search(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<AttendenceDetail> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewAttendenceDetail()");
        ArrayList<AttendenceDetail> adList=new ArrayList<>();
        while (rst.next()){
            adList.add(new AttendenceDetail(rst.getInt(1),rst.getString(2),rst.getInt(3),rst.getInt(4),rst.getInt(5)));
        }
        return adList;
    }
}
