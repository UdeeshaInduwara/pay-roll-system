package lk.ijse.payrollsystem.dao;

import lk.ijse.payrollsystem.dao.custom.impl.*;

public class DAOFactory {
    public enum DAOTypes{
        RATES,Allowence,DESIGNATION,EMPLOYEE,
        SALARYADVANCE,ATTENDENCE,ATTENDENCEDETAIL,MONTHLYWORKDAYS,YEARLYLEAVES,
        NETSALARY;
    }
    private static DAOFactory daoFactory;
    public static DAOFactory getInstance(){
        if(daoFactory==null){
            daoFactory=new DAOFactory();
        }
        return daoFactory;
    }
    private DAOFactory(){
    }
    public <T extends SuperDAO> T getDAO(DAOTypes daoType){
        switch(daoType){
            case RATES:
                return (T) new RatesDAOImpl();
            case Allowence:
                return (T) new AllowenceDAOImpl();
            case DESIGNATION:
                return (T) new DesignationDAOImpl();
            case EMPLOYEE:
                return (T) new EmployeeDAOImpl();
            case SALARYADVANCE:
                return (T) new SalaryAdvanceDAOImpl();
            case ATTENDENCE:
                return (T) new AttendenceDAOImpl();
            case ATTENDENCEDETAIL:
                return (T) new AttendenceDetailDAOImpl();
            case MONTHLYWORKDAYS:
                return (T) new MonthlyWorkDaysDAOImpl();
            case YEARLYLEAVES:
                return (T) new YearlyLeavesDAOImpl();
            case NETSALARY:
                return (T) new NetSalaryDAOImpl();
            default:
                return null;
        }
    }
}

