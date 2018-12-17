package lk.ijse.payrollsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeController implements Initializable {

    @FXML
    private JFXButton btnAttendence;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnSalary;

    @FXML
    private JFXButton btnSalaryAdvance;

    @FXML
    private JFXButton btnAllowence;

    @FXML
    private JFXButton btnDesignation;

    @FXML
    private JFXButton btnRates;

    @FXML
    private AnchorPane pnlParent;

    @FXML
    private AnchorPane pnlMain;


    @FXML
    void loadAttendenceForm(ActionEvent event) {
        startAttendenceForm();
    }

    @FXML
    void loadEmployeeForm(ActionEvent event) {
        AnchorPane pane= null;
        try {
            pane = FXMLLoader.load(this.getClass().getResource("/lk/ijse/payrollsystem/view/EmployeeForm.fxml"));
        } catch (IOException e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
        pnlMain.getChildren().setAll(pane);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), pnlMain);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        setButtonColor(2);
    }

    @FXML
    void loadSalaryForm(ActionEvent event) {
        AnchorPane pane= null;
        try {
            pane = FXMLLoader.load(this.getClass().getResource("/lk/ijse/payrollsystem/view/SalaryForm.fxml"));
        } catch (IOException e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
        pnlMain.getChildren().setAll(pane);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), pnlMain);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        setButtonColor(3);
    }

    @FXML
    void loadSalaryAdvanceForm(ActionEvent event) {
        AnchorPane pane= null;
        try {
            pane = FXMLLoader.load(this.getClass().getResource("/lk/ijse/payrollsystem/view/SalaryAdvanceForm.fxml"));
        } catch (IOException e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
        pnlMain.getChildren().setAll(pane);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), pnlMain);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        setButtonColor(4);
    }

    @FXML
    void loadAllowenceForm(ActionEvent event) {
        AnchorPane pane= null;
        try {
            pane = FXMLLoader.load(this.getClass().getResource("/lk/ijse/payrollsystem/view/AllowenceForm.fxml"));
        } catch (IOException e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
        pnlMain.getChildren().setAll(pane);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), pnlMain);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        setButtonColor(5);
    }

    @FXML
    void loadDesignationForm(ActionEvent event) {
        AnchorPane pane= null;
        try {
            pane = FXMLLoader.load(this.getClass().getResource("/lk/ijse/payrollsystem/view/DesignationForm.fxml"));
        } catch (IOException e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
        pnlMain.getChildren().setAll(pane);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), pnlMain);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        setButtonColor(6);
    }

    @FXML
    void loadRateForm(ActionEvent event) {
        AnchorPane pane= null;
        try {
            pane = FXMLLoader.load(this.getClass().getResource("/lk/ijse/payrollsystem/view/RatesForm.fxml"));
        } catch (IOException e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
        pnlMain.getChildren().setAll(pane);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), pnlMain);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        setButtonColor(7);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(3000), pnlParent);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        startAttendenceForm();
    }

    private void startAttendenceForm() {
        AnchorPane pane= null;
        try {
            pane = FXMLLoader.load(this.getClass().getResource("/lk/ijse/payrollsystem/view/AttendenceForm.fxml"));
        } catch (IOException e) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
        }
        pnlMain.getChildren().setAll(pane);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), pnlMain);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        setButtonColor(1);
    }

    private void setButtonColor(int num) {
        switch (num){
            case 1: btnAttendence.setStyle("-fx-background-color: #6C7A89");
                btnEmployee.setStyle("-fx-background-color: #2C3E50");
                btnSalary.setStyle("-fx-background-color: #2C3E50");
                btnSalaryAdvance.setStyle("-fx-background-color: #2C3E50");
                btnAllowence.setStyle("-fx-background-color: #2C3E50");
                btnDesignation.setStyle("-fx-background-color: #2C3E50");
                btnRates.setStyle("-fx-background-color: #2C3E50"); break;

            case 2: btnAttendence.setStyle("-fx-background-color: #2C3E50");
                btnEmployee.setStyle("-fx-background-color: #6C7A89");
                btnSalary.setStyle("-fx-background-color: #2C3E50");
                btnSalaryAdvance.setStyle("-fx-background-color: #2C3E50");
                btnAllowence.setStyle("-fx-background-color: #2C3E50");
                btnDesignation.setStyle("-fx-background-color: #2C3E50");
                btnRates.setStyle("-fx-background-color: #2C3E50"); break;

            case 3: btnAttendence.setStyle("-fx-background-color: #2C3E50");
                btnEmployee.setStyle("-fx-background-color: #2C3E50");
                btnSalary.setStyle("-fx-background-color: #6C7A89");
                btnSalaryAdvance.setStyle("-fx-background-color: #2C3E50");
                btnAllowence.setStyle("-fx-background-color: #2C3E50");
                btnDesignation.setStyle("-fx-background-color: #2C3E50");
                btnRates.setStyle("-fx-background-color: #2C3E50"); break;

            case 4: btnAttendence.setStyle("-fx-background-color: #2C3E50");
                btnEmployee.setStyle("-fx-background-color: #2C3E50");
                btnSalary.setStyle("-fx-background-color: #2C3E50");
                btnSalaryAdvance.setStyle("-fx-background-color: #6C7A89");
                btnAllowence.setStyle("-fx-background-color: #2C3E50");
                btnDesignation.setStyle("-fx-background-color: #2C3E50");
                btnRates.setStyle("-fx-background-color: #2C3E50"); break;

            case 5: btnAttendence.setStyle("-fx-background-color: #2C3E50");
                btnEmployee.setStyle("-fx-background-color: #2C3E50");
                btnSalary.setStyle("-fx-background-color: #2C3E50");
                btnSalaryAdvance.setStyle("-fx-background-color: #2C3E50");
                btnAllowence.setStyle("-fx-background-color: #6C7A89");
                btnDesignation.setStyle("-fx-background-color: #2C3E50");
                btnRates.setStyle("-fx-background-color: #2C3E50"); break;

            case 6: btnAttendence.setStyle("-fx-background-color: #2C3E50");
                btnEmployee.setStyle("-fx-background-color: #2C3E50");
                btnSalary.setStyle("-fx-background-color: #2C3E50");
                btnSalaryAdvance.setStyle("-fx-background-color: #2C3E50");
                btnAllowence.setStyle("-fx-background-color: #2C3E50");
                btnDesignation.setStyle("-fx-background-color: #6C7A89");
                btnRates.setStyle("-fx-background-color: #2C3E50"); break;

            case 7: btnAttendence.setStyle("-fx-background-color: #2C3E50");
                btnEmployee.setStyle("-fx-background-color: #2C3E50");
                btnSalary.setStyle("-fx-background-color: #2C3E50");
                btnSalaryAdvance.setStyle("-fx-background-color: #2C3E50");
                btnAllowence.setStyle("-fx-background-color: #2C3E50");
                btnDesignation.setStyle("-fx-background-color: #2C3E50");
                btnRates.setStyle("-fx-background-color: #6C7A89"); break;
        }
    }

}
