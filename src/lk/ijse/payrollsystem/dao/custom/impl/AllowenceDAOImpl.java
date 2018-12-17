package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.AllowenceDAO;
import lk.ijse.payrollsystem.entity.Allowence;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AllowenceDAOImpl implements AllowenceDAO {
    @Override
    public boolean save(Allowence entity) throws Exception {
        return CrudUtil.executeUpdate("call addallowence(?,?,?)",entity.getAlid(),entity.getTypes(),entity.getPecentage()) > 0;
    }

    @Override
    public boolean update(Allowence entity) throws Exception {
        return CrudUtil.executeUpdate("call updateAllowence(?,?,?)",entity.getAlid(),entity.getTypes(),entity.getPecentage()) > 0;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.executeUpdate("call deleteAllowence(?)",s) > 0;
    }

    @Override
    public Allowence search(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Allowence> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewAllowance()");
        ArrayList<Allowence> allowencesList=new ArrayList<>();
        while (rst.next()){
            allowencesList.add(new Allowence(rst.getString(1),rst.getString(2),rst.getDouble(3)));
        }
        return allowencesList;
    }
}
