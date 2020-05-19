package iMat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private IMatDataHandler dataHandler;
    private CategoryScreen categoryScreen;

    @FXML AnchorPane startScreen;
    @FXML ScrollPane shoppingCartPane;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public IMatDataHandler getDataHandler() {
        return dataHandler;
    }
}
