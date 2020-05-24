package iMat;

import com.sun.prism.Image;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;

public class HistoryController extends AnchorPane {

    @FXML
    AnchorPane historyPane;

    @FXML
    FlowPane dateFlowPane;
    @FXML
    FlowPane productFlowPane;

    public HistoryController(/*MainController mainController*/) {

        //this.mainController = mainController;

        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("history_screen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        populateDateList();

    }

    void populateDateList(){
        dateFlowPane.getChildren().clear();

        if (IMatDataHandler.getInstance().getOrders() != null) {
            for (Order o : IMatDataHandler.getInstance().getOrders()) {
                dateFlowPane.getChildren().add(new HistoryCardController(o));
            }
        }
    }





}
