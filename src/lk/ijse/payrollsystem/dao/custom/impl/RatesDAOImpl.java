package lk.ijse.payrollsystem.dao.custom.impl;

import lk.ijse.payrollsystem.dao.CrudUtil;
import lk.ijse.payrollsystem.dao.custom.RatesDAO;
import lk.ijse.payrollsystem.entity.Rates;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RatesDAOImpl implements RatesDAO {
    @Override
    public boolean save(Rates entity) throws Exception {
        return CrudUtil.executeUpdate("call addRates(?,?)",entity.getTypes(),entity.getPercentage()) > 0;
    }

    @Override
    public boolean update(Rates entity) throws Exception {
        return CrudUtil.executeUpdate("call updateRates(?,?)",entity.getTypes(),entity.getPercentage()) > 0;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.executeUpdate("call deleteRates(?)",s) > 0;
    }

    @Override
    public Rates search(String s) throws Exception {
        return null;
    }

    @Override
    public ArrayList<Rates> getAll() throws Exception {
        ResultSet rst = CrudUtil.executeQuery("call viewRates()");
        ArrayList<Rates> rateList=new ArrayList<>();
        while (rst.next()){
            rateList.add(new Rates(rst.getString(1),rst.getDouble(2)));
        }
        return rateList;
    }
}
