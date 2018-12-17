package lk.ijse.payrollsystem.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Logger logger = Logger.getLogger("");
        FileHandler fileHandler = new FileHandler("logger/error.log",true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);

        Parent root = FXMLLoader.load(getClass().getResource("../view/HomeForm.fxml"));
        primaryStage.setTitle("Pay-Roll System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
