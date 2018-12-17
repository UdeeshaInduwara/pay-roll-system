package lk.ijse.payrollsystem.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.payrollsystem.dao.DAOFactory;
import lk.ijse.payrollsystem.dao.custom.AttendenceDAO;
import lk.ijse.payrollsystem.dao.custom.AttendenceDetailDAO;
import lk.ijse.payrollsystem.dao.custom.MonthlyWorkDaysDAO;
import lk.ijse.payrollsystem.dao.custom.YearlyLeavesDAO;
import lk.ijse.payrollsystem.entity.Attendence;
import lk.ijse.payrollsystem.entity.AttendenceDetail;
import lk.ijse.payrollsystem.entity.MonthlyWorkDays;
import lk.ijse.payrollsystem.entity.YearlyLeaves;
import lk.ijse.payrollsystem.validation.Validator;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AttendenceController implements Initializable {
    private AttendenceDAO attendenceDAO;
    private AttendenceDetailDAO attendenceDetailDAO;
    private MonthlyWorkDaysDAO monthlyWorkDaysDAO;
    private YearlyLeavesDAO yearlyLeavesDAO;

    @FXML
    private TextField txtInEmpID;

    @FXML
    private TextField txtOutEmpID;

    @FXML
    private TableView<Attendence> tblAttendence;

    @FXML
    private JFXComboBox<String> cmbMonth;

    @FXML
    private JFXTextField txtNoOfWorkDays;

    @FXML
    private JFXTextField txtAddYear;

    @FXML
    private JFXTextField txtNoOfLeaves;

    @FXML
    private TableView<AttendenceDetail> tblAttendenceDetail;

    @FXML
    private TableView<MonthlyWorkDays> tblMonth;

    @FXML
    private TableView<YearlyLeaves> tblYear;

    @FXML
    private TextField txtSearchEid;

    @FXML
    void searchUsingEid(KeyEvent event) {
        try {
            Attendence aList = attendenceDAO.search(txtSearchEid.getText());
            tblAttendence.setItems(FXCollections.observableArrayList(aList));
        } catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void cancelAdding(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void markPresent(ActionEvent event) {
        if (Validator.employeeIdValidation(txtInEmpID.getText())){
            String inEmpId = txtInEmpID.getText();
            try {
                boolean isSaved = attendenceDAO.save(inEmpId, 1);
                if (isSaved){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Marking Failed", ButtonType.OK);
                    a.show();
                }else {
                    loadDataToAttendenceTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Marking Successfully", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }else {
            txtInEmpID.requestFocus();
        }
    }

    @FXML
    void markAbsent(ActionEvent event) {
        if (Validator.employeeIdValidation(txtInEmpID.getText())){
            String inEmpId = txtInEmpID.getText();
            try {
                boolean isSaved = attendenceDAO.save(inEmpId, 0);
                if (isSaved){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Marking Failed", ButtonType.OK);
                    a.show();
                }else {
                    loadDataToAttendenceTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Marking Successfully", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }else {
            txtInEmpID.requestFocus();
        }
    }

    @FXML
    void markOUT(ActionEvent event) {
        if (Validator.employeeIdValidation(txtOutEmpID.getText())){
            String outEmpId = txtOutEmpID.getText();
            try {
                boolean isUpdated = attendenceDAO.update(outEmpId);
                if (isUpdated){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Updating Failed", ButtonType.OK);
                    a.show();
                }else {
                    loadDataToAttendenceTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }else {
            txtOutEmpID.requestFocus();
        }
    }

    @FXML
    void removeAttendence(ActionEvent event) {
        Attendence attendence = tblAttendence.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = attendenceDAO.delete(attendence.getAtid());
            if (isDeleted){
                loadDataToAttendenceTbl();
                clearTextFields();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblAttendence.requestFocus();
        }
        catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void selectAttendenceTbl(MouseEvent event) {
        try {
            Attendence attendence = tblAttendence.getSelectionModel().getSelectedItem();
            txtOutEmpID.setText(attendence.getEid());
        }catch (NullPointerException e){

        }
    }

    @FXML
    void setMonth(ActionEvent event) {
        boolean isOk=false;
        if (Validator.characterValidation(cmbMonth.getSelectionModel().getSelectedItem())){
            if (Validator.intValueValidation(txtNoOfWorkDays.getText())){
                isOk=true;
            }else {
                txtNoOfWorkDays.requestFocus();
            }
        }else {
            cmbMonth.requestFocus();
        }
        if (isOk){
            String monthName = cmbMonth.getSelectionModel().getSelectedItem();
            Month m=Month.valueOf(monthName);
            int noOfWorkDays = Integer.parseInt(txtNoOfWorkDays.getText());
            try {
                boolean isSaved = monthlyWorkDaysDAO.save(m.getValue(), noOfWorkDays);
                if (isSaved){
                    loadDataToMonthTbl();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully", ButtonType.OK);
                    a.show();
                }else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Adding Failed", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void setYear(ActionEvent event) {
        boolean isOk=false;
        if (Validator.yearValidation(txtAddYear.getText())){
            if (Validator.intValueValidation(txtNoOfLeaves.getText())){
                isOk=true;
            }else {
                txtNoOfLeaves.requestFocus();
            }
        }else {
            txtAddYear.requestFocus();
        }
        if (isOk){
            int year = Integer.parseInt(txtAddYear.getText());
            int noOfLeaves = Integer.parseInt(txtNoOfLeaves.getText());
            try {
                boolean isSaved = yearlyLeavesDAO.save(year, noOfLeaves);
                if (isSaved){
                    loadDataToYearTbl();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully", ButtonType.OK);
                    a.show();
                }else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Adding Failed", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    @FXML
    void removeMonthlyWorkDays(ActionEvent event) {
        MonthlyWorkDays monthlyWorkDays = tblMonth.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = monthlyWorkDaysDAO.delete(monthlyWorkDays.getMonth());
            if (isDeleted){
                loadDataToMonthTbl();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblMonth.requestFocus();
        }
        catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void removeYearlyLeaves(ActionEvent event) {
        YearlyLeaves yearlyLeaves = tblYear.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = yearlyLeavesDAO.delete(yearlyLeaves.getYear());
            if (isDeleted){
                loadDataToYearTbl();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblYear.requestFocus();
        }
        catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        attendenceDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ATTENDENCE);
        attendenceDetailDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ATTENDENCEDETAIL);
        monthlyWorkDaysDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.MONTHLYWORKDAYS);
        yearlyLeavesDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.YEARLYLEAVES);

        tblAttendence.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("atid"));
        tblAttendence.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("eid"));
        tblAttendence.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        tblAttendence.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("currentDate"));
        tblAttendence.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblAttendence.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("intime"));
        tblAttendence.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("outtime"));
        tblAttendence.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("ot"));
        tblAttendence.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("noPay"));
        loadDataToAttendenceTbl();

        tblAttendenceDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("adid"));
        tblAttendenceDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("eid"));
        tblAttendenceDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("monthNo"));
        tblAttendenceDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("yearNo"));
        tblAttendenceDetail.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("currentMonthLeaves"));
        loadDataToAttendenceDetailTbl();

        tblMonth.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("month"));
        tblMonth.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("year"));
        tblMonth.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("noOfWorkDays"));
        loadDataToMonthTbl();

        tblYear.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("year"));
        tblYear.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("noOfLeaves"));
        loadDataToYearTbl();

        try {
            ArrayList<String> monthList=new ArrayList<>();
            monthList.add(String.valueOf(Month.JANUARY));
            monthList.add(String.valueOf(Month.FEBRUARY));
            monthList.add(String.valueOf(Month.MARCH));
            monthList.add(String.valueOf(Month.APRIL));
            monthList.add(String.valueOf(Month.MAY));
            monthList.add(String.valueOf(Month.JUNE));
            monthList.add(String.valueOf(Month.JULY));
            monthList.add(String.valueOf(Month.AUGUST));
            monthList.add(String.valueOf(Month.SEPTEMBER));
            monthList.add(String.valueOf(Month.OCTOBER));
            monthList.add(String.valueOf(Month.NOVEMBER));
            monthList.add(String.valueOf(Month.DECEMBER));
            cmbMonth.setItems(FXCollections.observableArrayList(monthList));
        } catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadDataToYearTbl() {
        try {
            ArrayList<YearlyLeaves> allYLList = yearlyLeavesDAO.getAll();
            tblYear.setItems(FXCollections.observableArrayList(allYLList));
        } catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadDataToMonthTbl() {
        try {
            ArrayList<MonthlyWorkDays> allMWDList = monthlyWorkDaysDAO.getAll();
            tblMonth.setItems(FXCollections.observableArrayList(allMWDList));
        } catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadDataToAttendenceDetailTbl() {
        try {
            ArrayList<AttendenceDetail> adList = attendenceDetailDAO.getAll();
            tblAttendenceDetail.setItems(FXCollections.observableArrayList(adList));
        } catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadDataToAttendenceTbl() {
        try {
            ArrayList<Attendence> aList = attendenceDAO.getAll();
            tblAttendence.setItems(FXCollections.observableArrayList(aList));
        } catch (Exception e) {
            Logger.getLogger(AttendenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void clearTextFields() {
        txtInEmpID.clear();
        txtOutEmpID.clear();
        txtNoOfLeaves.clear();
        txtAddYear.clear();
        txtNoOfWorkDays.clear();
        cmbMonth.getSelectionModel().clearSelection();
    }
}
