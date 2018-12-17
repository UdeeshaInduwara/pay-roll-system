package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.AttendenceDAO;
import lk.ijse.payrollsystem.entity.Attendence;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AttendenceDAOImpl implements AttendenceDAO {
    @Override
    public boolean save(String eid, int status) throws Exception {
        return CrudUtil.executeUpdate("call markIntime(?,?)",eid,status) > 0;
    }

    @Override
    public boolean save(Attendence entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(String eid) throws Exception {
        return CrudUtil.executeUpdate("call markOutTime(?)",eid) > 0;
    }

    @Override
    public Attendence search(String s) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call searchAttendence(?)",s);
        if (rst.next()){
            return new Attendence(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getDate(7),rst.getDouble(8),rst.getDouble(9));
        }else {
            return null;
        }
    }

    @Override
    public boolean update(Attendence entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Integer s) throws Exception {
        return CrudUtil.executeUpdate("call deleteAttendence(?)",s) > 0;
    }

    @Override
    public Attendence search(Integer s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Attendence> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewAttendence()");
        ArrayList<Attendence> aList=new ArrayList<>();
        while (rst.next()){
            aList.add(new Attendence(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6),rst.getDate(7),rst.getDouble(8),rst.getDouble(9)));
        }
        return aList;
    }
}
