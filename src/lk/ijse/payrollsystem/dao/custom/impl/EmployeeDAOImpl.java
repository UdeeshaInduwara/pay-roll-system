package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.EmployeeDAO;
import lk.ijse.payrollsystem.entity.Employee;

import java.sql.ResultSet;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public boolean save(Employee entity) throws Exception {
        return CrudUtil.executeUpdate("call addEmployee(?,?,?,?)",entity.getEid(),entity.getEmpName(),entity.getNic(),entity.getDid()) > 0;
    }

    @Override
    public boolean update(Employee entity) throws Exception {
        return CrudUtil.executeUpdate("call updateEmployee(?,?,?,?)",entity.getEid(),entity.getEmpName(),entity.getNic(),entity.getDid()) > 0;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.executeUpdate("call deleteEmployee(?)",s) > 0;
    }

    @Override
    public Employee search(String s) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call searchEmployee(?)",s);
        if (rst.next()){
            return new Employee(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<Employee> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewEmployee()");
        ArrayList<Employee> employeeList=new ArrayList<>();
        while (rst.next()){
            employeeList.add(new Employee(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }
        return employeeList;
    }
}
