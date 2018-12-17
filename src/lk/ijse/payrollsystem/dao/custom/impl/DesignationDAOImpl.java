package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.DesignationDAO;
import lk.ijse.payrollsystem.entity.Designation;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DesignationDAOImpl implements DesignationDAO {
    @Override
    public boolean save(Designation entity) throws Exception {
        return CrudUtil.executeUpdate("call addDesignation(?,?,?,?)",entity.getDid(),entity.getLevels(),entity.getBasicSalary(),entity.getMedicelAllowence()) > 0;
    }

    @Override
    public boolean update(Designation entity) throws Exception {
        return CrudUtil.executeUpdate("call updateDesignation(?,?,?,?)",entity.getDid(),entity.getLevels(),entity.getBasicSalary(),entity.getMedicelAllowence()) > 0;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.executeUpdate("call deleteDesignation(?)",s) > 0;
    }

    @Override
    public Designation search(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Designation> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewDesignation()");
        ArrayList<Designation> designationList=new ArrayList<>();
        while (rst.next()){
            designationList.add(new Designation(rst.getString(1),rst.getString(2),rst.getDouble(3),rst.getDouble(4)));
        }
        return designationList;
    }
}
