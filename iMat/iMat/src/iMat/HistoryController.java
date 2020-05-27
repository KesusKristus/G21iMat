package iMat;

import com.sun.prism.Image;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HistoryController extends AnchorPane {

    @FXML
    AnchorPane historyPane;

    @FXML
    FlowPane dateFlowPane;
    @FXML
    FlowPane productFlowPane;
    @FXML
    ScrollPane productScrollPane;
    @FXML
    Label historikLabel;

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

    void populateDateList() {
        dateFlowPane.getChildren().clear();

        if (idh.getOrders().size() != 0) {

            List<Order> sortedList = sortListNewestFirst();

            for (Order o : sortedList) {
                //Only add the order to the history if there were any items bought
                if (o.getItems().size() != 0){
                    dateFlowPane.getChildren().add(new HistoryCardController(this, o));
                }

            }
        }
    }

    public List<Order> sortListNewestFirst() {

        List<Order> sortedOrderList = idh.getOrders();

        sortedOrderList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        Collections.reverse(sortedOrderList);

        return sortedOrderList;

    }

    public void populateProductList(Order o, String dateS) {

        historikLabel.setText("Varor köpta: " + dateS);

        productFlowPane.getChildren().clear();
        productFlowPane.setVgap(5);
        productFlowPane.setHgap(5);
        productScrollPane.setVvalue(0);

        for (ShoppingItem sI : o.getItems()) {
            productFlowPane.getChildren().add(new ProductCard(sI, mainController));
        }


    }


}
