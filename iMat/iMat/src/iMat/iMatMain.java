package iMat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class iMatMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("iMatMain.fxml"));
        primaryStage.setTitle("iMat");
        primaryStage.setScene(new Scene(root, 960, 540));
        primaryStage.show();

        //blablablabla
    }


    public static void main(String[] args) {
        launch(args);
    }
}
