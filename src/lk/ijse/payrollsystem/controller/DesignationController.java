package lk.ijse.payrollsystem.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.payrollsystem.dao.DAOFactory;
import lk.ijse.payrollsystem.dao.custom.DesignationDAO;
import lk.ijse.payrollsystem.entity.Designation;
import lk.ijse.payrollsystem.generator.IDGenerator;
import lk.ijse.payrollsystem.validation.Validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DesignationController implements Initializable {
    private DesignationDAO designationDAO;

    @FXML
    private JFXTextField txtDesignationID;

    @FXML
    private JFXTextField txtLevel;

    @FXML
    private JFXTextField txtBasicSalary;

    @FXML
    private JFXTextField txtMedicleAllowence;

    @FXML
    private TableView<Designation> tblDesignation;

    @FXML
    void addDesignation(ActionEvent event) {
        boolean isOk=false;
        if (Validator.designationIdValidation(txtDesignationID.getText())){
            if (Validator.characterValidation(txtLevel.getText())){
                if (Validator.doubleValueValidation(txtBasicSalary.getText())){
                    if (Validator.doubleValueValidation(txtMedicleAllowence.getText())){
                        isOk=true;
                    }else {
                        txtMedicleAllowence.requestFocus();
                    }
                }else {
                    txtBasicSalary.requestFocus();
                }
            }else {
                txtLevel.requestFocus();
            }
        }else {
            txtDesignationID.requestFocus();
        }
        if (isOk){
            String did = txtDesignationID.getText();
            String level = txtLevel.getText();
            double bSalary = Double.parseDouble(txtBasicSalary.getText());
            double mAllowence = Double.parseDouble(txtMedicleAllowence.getText());
            try {
                boolean isSaved=designationDAO.save(new Designation(did,level,bSalary,mAllowence));
                if (isSaved){
                    loadDataToDesignationTable();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully", ButtonType.OK);
                    a.show();
                }else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Adding Failed", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(DesignationController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void cancelAdding(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void removeDesignation(ActionEvent event) {
        Designation designation = tblDesignation.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = designationDAO.delete(designation.getDid());
            if (isDeleted){
                loadDataToDesignationTable();
                clearTextFields();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblDesignation.requestFocus();
        } catch (Exception e) {
            Logger.getLogger(DesignationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void updateDesignation(ActionEvent event) {
        boolean isOk=false;
        if (Validator.designationIdValidation(txtDesignationID.getText())){
            if (Validator.characterValidation(txtLevel.getText())){
                if (Validator.doubleValueValidation(txtBasicSalary.getText())){
                    if (Validator.doubleValueValidation(txtMedicleAllowence.getText())){
                        isOk=true;
                    }else {
                        txtMedicleAllowence.requestFocus();
                    }
                }else {
                    txtBasicSalary.requestFocus();
                }
            }else {
                txtLevel.requestFocus();
            }
        }else {
            txtDesignationID.requestFocus();
        }
        if (isOk){
            String did = txtDesignationID.getText();
            String level = txtLevel.getText();
            double bSalary = Double.parseDouble(txtBasicSalary.getText());
            double mAllowence = Double.parseDouble(txtMedicleAllowence.getText());
            try {
                boolean isUpdated=designationDAO.update(new Designation(did,level,bSalary,mAllowence));
                if (isUpdated){
                    loadDataToDesignationTable();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully", ButtonType.OK);
                    a.show();
                }else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Updating Failed", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(DesignationController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void selectDesignationTbl(MouseEvent event) {
        try {
            Designation designation = tblDesignation.getSelectionModel().getSelectedItem();
            txtDesignationID.setText(designation.getDid());
            txtLevel.setText(designation.getLevels());
            txtBasicSalary.setText(String.valueOf(designation.getBasicSalary()));
            txtMedicleAllowence.setText(String.valueOf(designation.getMedicelAllowence()));
        }catch (NullPointerException e){

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        designationDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.DESIGNATION);

        tblDesignation.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("did"));
        tblDesignation.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("levels"));
        tblDesignation.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        tblDesignation.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("medicelAllowence"));

        loadDataToDesignationTable();
        setDesignationID();
    }

    private void setDesignationID() {
        try {
            String newID = IDGenerator.getNewID("designation", "did", "d");
            txtDesignationID.setText(newID);
        } catch (Exception e) {
            Logger.getLogger(DesignationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadDataToDesignationTable() {
        try {
            ArrayList<Designation> designationList = designationDAO.getAll();
            tblDesignation.setItems(FXCollections.observableArrayList(designationList));
        } catch (Exception e) {
            Logger.getLogger(DesignationController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void clearTextFields() {
        txtDesignationID.clear();
        txtMedicleAllowence.clear();
        txtBasicSalary.clear();
        txtLevel.clear();
        setDesignationID();
    }
}
