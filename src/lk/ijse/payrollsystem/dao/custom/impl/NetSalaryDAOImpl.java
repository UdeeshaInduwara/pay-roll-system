package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.NetSalaryDAO;
import lk.ijse.payrollsystem.entity.NetSalary;

import java.sql.ResultSet;
import java.util.ArrayList;

public class NetSalaryDAOImpl implements NetSalaryDAO {
    @Override
    public boolean save(String id) throws Exception {
        return CrudUtil.executeUpdate("call calculateSalary(?)",id) > 0;
    }

    @Override
    public NetSalary search(String eid) throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call searchNetSalary(?)",eid);
        if (rst.next()){
            return new NetSalary(rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getDouble(8),
                    rst.getDouble(9),
                    rst.getDouble(10),
                    rst.getDouble(11),
                    rst.getDouble(12),
                    rst.getDouble(13));
        }else {
            return null;
        }
    }

    @Override
    public boolean save(NetSalary entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(NetSalary entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws Exception {
        return CrudUtil.executeUpdate("call deleteNetSalary(?)",integer) > 0;
    }

    @Override
    public NetSalary search(Integer integer) throws Exception {
        return null;
    }

    @Override
    public ArrayList<NetSalary> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewNetSalary()");
        ArrayList<NetSalary> sList=new ArrayList<>();
        while (rst.next()){
            sList.add(new NetSalary(rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getDouble(8),
                    rst.getDouble(9),
                    rst.getDouble(10),
                    rst.getDouble(11),
                    rst.getDouble(12),
                    rst.getDouble(13)));
        }
        return sList;
    }
}
