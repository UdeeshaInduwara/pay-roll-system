package lk.ijse.payrollsystem.controller;

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
import lk.ijse.payrollsystem.dao.custom.SalaryAdvanceDAO;
import lk.ijse.payrollsystem.entity.SalaryAdvance;
import lk.ijse.payrollsystem.validation.Validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalaryAdvanceController implements Initializable {
    private SalaryAdvanceDAO salaryAdvanceDAO;

    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtAdvancePrice;

    @FXML
    private TableView<SalaryAdvance> tblSalaryAdvance;

    @FXML
    private TextField txtSearchEmpId;

    @FXML
    void searchUsingEid(KeyEvent event) {
        try {
            SalaryAdvance saList = salaryAdvanceDAO.search(txtSearchEmpId.getText());
            tblSalaryAdvance.setItems(FXCollections.observableArrayList(saList));
        } catch (Exception e) {
            Logger.getLogger(SalaryAdvanceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    void addSalaryAdvance(ActionEvent event) {
        boolean isOk=false;
        if (Validator.employeeIdValidation(txtEmployeeId.getText())){
            if (Validator.doubleValueValidation(txtAdvancePrice.getText())){
                isOk=true;
            }else {
                txtAdvancePrice.requestFocus();
            }
        }else {
            txtEmployeeId.requestFocus();
        }
        if (isOk){
            String eid = txtEmployeeId.getText();
            double advance = Double.parseDouble(txtAdvancePrice.getText());
            try {
                boolean isSaved=salaryAdvanceDAO.save(eid,advance);
                if (isSaved){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Adding Failed", ButtonType.OK);
                    a.show();
                }else {
                    loadDataToSalaryAdvanceTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(SalaryAdvanceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void cancelAdding(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void removeSalaryAdvance(ActionEvent event) {
        SalaryAdvance salaryAdvance = tblSalaryAdvance.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = salaryAdvanceDAO.delete(salaryAdvance.getSaid());
            if (isDeleted){
                loadDataToSalaryAdvanceTbl();
                clearTextFields();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblSalaryAdvance.requestFocus();
        } catch (Exception e) {
            Logger.getLogger(SalaryAdvanceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void updateSalaryAdvance(ActionEvent event) {
        try{
            SalaryAdvance salaryAdvance = tblSalaryAdvance.getSelectionModel().getSelectedItem();
            int said = salaryAdvance.getSaid();
            if (Validator.doubleValueValidation(txtAdvancePrice.getText())){
                double advance = Double.parseDouble(txtAdvancePrice.getText());
                try {
                    boolean isUpdated=salaryAdvanceDAO.update(said,advance);
                    if (isUpdated){
                        loadDataToSalaryAdvanceTbl();
                        clearTextFields();
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully", ButtonType.OK);
                        a.show();
                    }else {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Updating Failed", ButtonType.OK);
                        a.show();
                    }
                } catch (Exception e) {
                    Logger.getLogger(SalaryAdvanceController.class.getName()).log(Level.SEVERE, null, e);
                }
            }else {
                txtAdvancePrice.requestFocus();
            }
        }catch (NullPointerException e){
            tblSalaryAdvance.requestFocus();
        }
    }

    @FXML
    void selectSalaryAdvanceTbl(MouseEvent event) {
        try {
            SalaryAdvance salaryAdvance = tblSalaryAdvance.getSelectionModel().getSelectedItem();
            txtEmployeeId.setText(salaryAdvance.getEid());
            txtAdvancePrice.setText(String.valueOf(salaryAdvance.getAdvance()));
        }catch (NullPointerException e){

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salaryAdvanceDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SALARYADVANCE);

        tblSalaryAdvance.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("said"));
        tblSalaryAdvance.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("eid"));
        tblSalaryAdvance.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        tblSalaryAdvance.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("currentDate"));
        tblSalaryAdvance.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("advance"));

        loadDataToSalaryAdvanceTbl();
    }

    private void loadDataToSalaryAdvanceTbl() {
        try {
            ArrayList<SalaryAdvance> saList = salaryAdvanceDAO.getAll();
            tblSalaryAdvance.setItems(FXCollections.observableArrayList(saList));
        } catch (Exception e) {
            Logger.getLogger(SalaryAdvanceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void clearTextFields() {
        txtEmployeeId.clear();
        txtAdvancePrice.clear();
    }
}
