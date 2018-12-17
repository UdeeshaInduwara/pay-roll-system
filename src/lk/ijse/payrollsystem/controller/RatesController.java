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
import lk.ijse.payrollsystem.dao.custom.RatesDAO;
import lk.ijse.payrollsystem.entity.Rates;
import lk.ijse.payrollsystem.validation.Validator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RatesController implements Initializable {
    private RatesDAO ratesDAO;

    @FXML
    private JFXTextField txtType;

    @FXML
    private JFXTextField txtRate;

    @FXML
    private TableView<Rates> tblRates;

    @FXML
    void addRates(ActionEvent event) {
        boolean isOk=false;
        if (Validator.characterValidation(txtType.getText())){
            if (Validator.doubleValueValidation(txtRate.getText())){
                isOk=true;
            }else {
                txtRate.requestFocus();
            }
        }else {
            txtType.requestFocus();
        }
        if (isOk){
            String type = txtType.getText();
            double rate = Double.parseDouble(txtRate.getText());
            try {
                boolean isSaved = ratesDAO.save(new Rates(type, rate));
                if (isSaved){
                    loadDataToRateTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Added Successfully", ButtonType.OK);
                    a.show();
                }else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Adding Failed", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(RatesController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void cancelAdding(ActionEvent event) {
        clearTextFields();
    }

    @FXML
    void removeRates(ActionEvent event) {
        Rates rates = tblRates.getSelectionModel().getSelectedItem();
        try {
            boolean isDeleted = ratesDAO.delete(rates.getTypes());
            if (isDeleted){
                loadDataToRateTbl();
                clearTextFields();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully", ButtonType.OK);
                a.show();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Deleting Failed", ButtonType.OK);
                a.show();
            }
        }catch (NullPointerException e){
            tblRates.requestFocus();
        } catch (Exception e) {
            Logger.getLogger(RatesController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void updateRates(ActionEvent event) {
        boolean isOk=false;
        if (Validator.characterValidation(txtType.getText())){
            if (Validator.doubleValueValidation(txtRate.getText())){
                isOk=true;
            }else {
                txtRate.requestFocus();
            }
        }else {
            txtType.requestFocus();
        }
        if (isOk){
            String type = txtType.getText();
            double rate = Double.parseDouble(txtRate.getText());
            try {
                boolean isUpdated = ratesDAO.update(new Rates(type, rate));
                if (isUpdated){
                    loadDataToRateTbl();
                    clearTextFields();
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Updated Successfully", ButtonType.OK);
                    a.show();
                }else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Updating Failed", ButtonType.OK);
                    a.show();
                }
            } catch (Exception e) {
                Logger.getLogger(RatesController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void selectRatesTbl(MouseEvent event) {
        try {
            Rates rates = tblRates.getSelectionModel().getSelectedItem();
            txtType.setText(rates.getTypes());
            txtRate.setText(String.valueOf(rates.getPercentage()));
        }catch (NullPointerException e){

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ratesDAO=DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RATES);

        tblRates.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("types"));
        tblRates.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("percentage"));

        loadDataToRateTbl();
    }

    private void loadDataToRateTbl() {
        try {
            ArrayList<Rates> rateList = ratesDAO.getAll();
            tblRates.setItems(FXCollections.observableArrayList(rateList));
        } catch (Exception e) {
            Logger.getLogger(RatesController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void clearTextFields() {
        txtType.clear();
        txtRate.clear();
    }
}
