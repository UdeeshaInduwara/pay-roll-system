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
import lk.ijse.payrollsystem.dao.custom.DesignationDAO;
import lk.ijse.payrollsystem.dao.custom.EmployeeDAO;
import lk.ijse.payrollsystem.entity.Designation;
import lk.ijse.payrollsystem.entity.Employee;
import lk.ijse.payrollsystem.generator.IDGenerator;
import lk.ijse.payrollsystem.validation.Validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeController implements Initializable {
    private DesignationDAO designationDAO;
    private EmployeeDAO employeeDAO;

    @FXML
    private JFXTextField txtEmployeeID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXComboBox<String> cmbDesignation;

    @FXML
    private TableView<Employee> tblEmployeeManagement;

    @FXML
    private TextField txtSearchId;

    @FXML
    void searchUsingID(KeyEvent event) {
        try {
            Employee employeeList = employeeDAO.search(txtSearchId.getText());
            tblEmployeeManagement.setItems(FXCollections.observableArrayList(employeeList));
        } catch (Exception e) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void addEmployee(ActionEvent event) {
        boolean isOk=false;
        if (Validator.characterValidation(txtName.getText())){
            if (Validator.nicValidation(txtNic.getText())){
                try {
                    if (Validator.designationIdValidation(cmbDesignation.getSelectionModel().getSelectedItem())){
                        isOk=true;
                    }else {
                        cmbDesignation.requestFocus();
                    }
                }catch (NullPointerException e){
                    cmbDesignation.requestFocus();
                }
            }else {
                txtNic.requestFocus();
            }
        }else {
            txtName.requestFocus();
        }
        if (isOk){
            String eid = txtEmployeeID.getText();
            String name = txtName.getText();
            String nic = txtNic.getText();
            String did = cmbDesignation.getSelectionModel().getSelectedItem();
            try {
                boolean isSaved=employeeDAO.save(new Employee(eid,name,nic,did));
                if (isSaved){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Adding Failed", ButtonType.OK);
                    a.show();
                }else {
                    loadDataToEmployeeTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void cancelAdding(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void removeEmployee(ActionEvent event) {
        Employee employee = tblEmployeeManagement.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = employeeDAO.delete(employee.getEid());
            if (isDeleted){
                loadDataToEmployeeTbl();
                clearTextFields();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblEmployeeManagement.requestFocus();
        } catch (Exception e) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void updateEmployee(ActionEvent event) {
        boolean isOk=false;
        if (Validator.characterValidation(txtName.getText())){
            if (Validator.nicValidation(txtNic.getText())){
                try {
                    if (Validator.designationIdValidation(cmbDesignation.getSelectionModel().getSelectedItem())){
                        isOk=true;
                    }else {
                        cmbDesignation.requestFocus();
                    }
                }catch (NullPointerException e){
                    cmbDesignation.requestFocus();
                }
            }else {
                txtNic.requestFocus();
            }
        }else {
            txtName.requestFocus();
        }
        if (isOk){
            String eid = txtEmployeeID.getText();
            String name = txtName.getText();
            String nic = txtNic.getText();
            String did = cmbDesignation.getSelectionModel().getSelectedItem();
            try {
                boolean isUpdated=employeeDAO.update(new Employee(eid,name,nic,did));
                if (isUpdated){
                    Alert a = new Alert(Alert.AlertType.ERROR, "Updating Failed", ButtonType.OK);
                    a.show();
                }else {
                    loadDataToEmployeeTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void selectEmployeeTbl(MouseEvent event) {
        try {
            Employee employee = tblEmployeeManagement.getSelectionModel().getSelectedItem();
            txtEmployeeID.setText(employee.getEid());
            txtName.setText(employee.getEmpName());
            txtNic.setText(employee.getNic());
            cmbDesignation.getSelectionModel().select(employee.getDid());
        }catch (NullPointerException e){

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        designationDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.DESIGNATION);
        employeeDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

        try {
            ArrayList<Designation> designationList = designationDAO.getAll();
            ArrayList<String> idList=new ArrayList<>();
            for (Designation data : designationList) {
                idList.add(data.getDid());
            }
            cmbDesignation.setItems(FXCollections.observableArrayList(idList));
        } catch (Exception e) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, e);
        }

        tblEmployeeManagement.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("eid"));
        tblEmployeeManagement.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("empName"));
        tblEmployeeManagement.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("nic"));
        tblEmployeeManagement.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("did"));

        loadDataToEmployeeTbl();
        setEmployeeID();
    }

    private void setEmployeeID() {
        try {
            String newID = IDGenerator.getNewID("employee", "eid", "e");
            txtEmployeeID.setText(newID);
        } catch (Exception e) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadDataToEmployeeTbl() {
        try {
            ArrayList<Employee> employeeList = employeeDAO.getAll();
            tblEmployeeManagement.setItems(FXCollections.observableArrayList(employeeList));
        } catch (Exception e) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void clearTextFields() {
        txtEmployeeID.clear();
        txtName.clear();
        txtNic.clear();
        cmbDesignation.getSelectionModel().clearSelection();
        setEmployeeID();
    }
}
