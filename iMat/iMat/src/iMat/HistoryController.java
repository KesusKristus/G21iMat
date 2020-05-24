package iMat;

import com.sun.prism.Image;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class HistoryController extends AnchorPane {

    @FXML
    AnchorPane historyPane;

    @FXML
    FlowPane dateFlowPane;
    @FXML
    FlowPane productFlowPane;

    IMatDataHandler idh = IMatDataHandler.getInstance();

    MainController mainController;

    public HistoryController(MainController mainController) {

        this.mainController = mainController;

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

        if (idh.getOrders() != null) {
            for (Order o : idh.getOrders()) {
                dateFlowPane.getChildren().add(new HistoryCardController(this, o));
            }
        }
    }

    public void populateProductList(Order o){
        productFlowPane.getChildren().clear();

        for (ShoppingItem sI : o.getItems()){
            productFlowPane.getChildren().add(new ProductCard(sI.getProduct(), ProductCard.cardType.category, mainController));
        }


    }





}
