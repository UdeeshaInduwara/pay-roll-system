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
import lk.ijse.payrollsystem.dao.custom.AllowenceDAO;
import lk.ijse.payrollsystem.entity.Allowence;
import lk.ijse.payrollsystem.generator.IDGenerator;
import lk.ijse.payrollsystem.validation.Validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AllowenceController implements Initializable {
    private AllowenceDAO allowenceDAO;

    @FXML
    private JFXTextField txtAllowenceID;

    @FXML
    private JFXTextField txtType;

    @FXML
    private JFXTextField txtRate;

    @FXML
    private TableView<Allowence> tblAllowence;

    @FXML
    void addAllowence(ActionEvent event) {
        boolean isOk=false;
        if(Validator.characterValidation(txtType.getText())){
            if (Validator.doubleValueValidation(txtRate.getText())){
                isOk=true;
            }else {
                txtRate.requestFocus();
            }
        }else {
            txtType.requestFocus();
        }
        if (isOk){
            String id = txtAllowenceID.getText();
            String type = txtType.getText();
            double rate = Double.parseDouble(txtRate.getText());
            try {
                boolean isSaved=allowenceDAO.save(new Allowence(id,type,rate));
                if (isSaved){
                    loadDataToAllowenceTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully", ButtonType.OK);
                    a.show();
                }else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Adding Failed", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(AllowenceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void cancelAdding(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void removeAllowence(ActionEvent event) {
        Allowence allowence = tblAllowence.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = allowenceDAO.delete(allowence.getAlid());
            if (isDeleted){
                loadDataToAllowenceTbl();
                clearTextFields();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblAllowence.requestFocus();
        }
        catch (Exception e) {
            Logger.getLogger(AllowenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void updateAllowence(ActionEvent event) {
        boolean isOk=false;
        if(Validator.characterValidation(txtType.getText())){
            if (Validator.doubleValueValidation(txtRate.getText())){
                isOk=true;
            }else {
                txtRate.requestFocus();
            }
        }else {
            txtType.requestFocus();
        }
        if (isOk){
            String id = txtAllowenceID.getText();
            String type = txtType.getText();
            double rate = Double.parseDouble(txtRate.getText());
            try {
                boolean isUpdated=allowenceDAO.update(new Allowence(id,type,rate));
                if (isUpdated){
                    loadDataToAllowenceTbl();
                    setAllowenceID();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully", ButtonType.OK);
                    a.show();
                }else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Updating Failed", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(AllowenceController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void selectAllowenceTbl(MouseEvent event) {
        try {
            Allowence allowence = tblAllowence.getSelectionModel().getSelectedItem();
            txtAllowenceID.setText(allowence.getAlid());
            txtType.setText(allowence.getTypes());
            txtRate.setText(String.valueOf(allowence.getPecentage()));
        }catch (NullPointerException e){

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allowenceDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Allowence);

        tblAllowence.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("alid"));
        tblAllowence.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("types"));
        tblAllowence.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("pecentage"));

        loadDataToAllowenceTbl();
        setAllowenceID();
    }

    private void setAllowenceID() {
        try {
            String newID = IDGenerator.getNewID("allowance", "alid", "al");
            txtAllowenceID.setText(newID);
        } catch (Exception e) {
            Logger.getLogger(AllowenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadDataToAllowenceTbl() {
        try {
            ArrayList<Allowence> allowenceList = allowenceDAO.getAll();
            tblAllowence.setItems(FXCollections.observableArrayList(allowenceList));
        } catch (Exception e) {
            Logger.getLogger(AllowenceController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void clearTextFields() {
        txtType.clear();
        txtRate.clear();
        setAllowenceID();
    }
}
