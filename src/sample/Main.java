package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.PokerCalc.GetPersentage;
import sample.PokerCalc.Hand;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Poker calc");
        primaryStage.setScene(new Scene(root, 675, 275));
        primaryStage.show();

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        //GetPersentage getPersentage = new GetPersentage();
       // getPersentage.addStatistics(new Hand("Qc Qh"),new Hand("Ad Ks"),5000);
        launch(args);
    }

}
