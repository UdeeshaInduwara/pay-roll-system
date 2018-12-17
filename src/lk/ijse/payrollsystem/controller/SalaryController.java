package lk.ijse.payrollsystem.controller;

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
import lk.ijse.payrollsystem.dao.custom.NetSalaryDAO;
import lk.ijse.payrollsystem.entity.NetSalary;
import lk.ijse.payrollsystem.validation.Validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalaryController implements Initializable {
    private NetSalaryDAO netSalaryDAO;

    @FXML
    private TextField txtEmpID;

    @FXML
    private TableView<NetSalary> tblNetSalary;

    @FXML
    private TextField txtSearchEmpId;

    @FXML
    void searchUsingEmpId(KeyEvent event) {
        try {
            NetSalary sList = netSalaryDAO.search(txtSearchEmpId.getText());
            tblNetSalary.setItems(FXCollections.observableArrayList(sList));
        } catch (Exception e) {
            Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void calThisEmpSalary(ActionEvent event) {
        if (Validator.employeeIdValidation(txtEmpID.getText())){
            String empId = txtEmpID.getText();
            try {
                boolean isSaved = netSalaryDAO.save(empId);
                if (isSaved){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Salary Calculating Failed", ButtonType.OK);
                    a.show();
                }else {
                    loadDataToNetSalaryTbl();
                    txtEmpID.clear();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Salary Calculated Successfully", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, e);
            }
        }else {
            txtEmpID.requestFocus();
        }
    }

    @FXML
    void removeSalary(ActionEvent event) {
        try {
            NetSalary netSalary = tblNetSalary.getSelectionModel().getSelectedItem();
            boolean isDeleted = netSalaryDAO.delete(netSalary.getSid());
            if (isDeleted){
                loadDataToNetSalaryTbl();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblNetSalary.requestFocus();
        } catch (Exception e) {
            Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        netSalaryDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.NETSALARY);

        tblNetSalary.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("eid"));
        tblNetSalary.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("monthName"));
        tblNetSalary.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("ot"));
        tblNetSalary.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("noPay"));
        tblNetSalary.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("etf"));
        tblNetSalary.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("epf"));
        tblNetSalary.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("food"));
        tblNetSalary.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("medicle"));
        tblNetSalary.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("attendence"));
        tblNetSalary.getColumns().get(9).setCellValueFactory(new PropertyValueFactory<>("transport"));
        tblNetSalary.getColumns().get(10).setCellValueFactory(new PropertyValueFactory<>("salaryAdvance"));
        tblNetSalary.getColumns().get(11).setCellValueFactory(new PropertyValueFactory<>("total"));
        loadDataToNetSalaryTbl();
    }

    private void loadDataToNetSalaryTbl() {
        try {
            ArrayList<NetSalary> sList = netSalaryDAO.getAll();
            tblNetSalary.setItems(FXCollections.observableArrayList(sList));
        } catch (Exception e) {
            Logger.getLogger(SalaryController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
