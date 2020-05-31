package iMat;

import iMat.Categories.CategoriesController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.util.*;

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

    @FXML
    AnchorPane buttonPane;
    @FXML
    AnchorPane buttonPaneGREY;

    IMatDataHandler idh = IMatDataHandler.getInstance();
    ShoppingCart cart = idh.getShoppingCart();

    MainController mainController;

    CategoriesController categoriesController;

    List<ShoppingItem> currentProductList = new ArrayList<>();

    public HistoryController(MainController mainController, CategoriesController categoriesController) {

        this.mainController = mainController;
        this.categoriesController = categoriesController;

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
                if (o.getItems().size() != 0) {
                    dateFlowPane.getChildren().add(new HistoryCardController(this, o));
                }

            }
        }

        buttonPaneGREY.toFront();
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

        currentProductList = o.getItems();

        historikLabel.setText("Varor köpta: " + dateS);

        productFlowPane.getChildren().clear();
        productFlowPane.setVgap(5);
        productFlowPane.setHgap(5);
        productScrollPane.setVvalue(0);

        for (ShoppingItem sI : currentProductList) {
            productFlowPane.getChildren().add(ProductCard.createProductCardHistory(categoriesController.findShoppingItem(sI.getProduct().getProductId()), sI.getAmount()));
        }

        buttonPane.toFront();
    }

    private void addOrderToCart() {

        for (ShoppingItem sI : currentProductList){
            if (!cart.getItems().contains(sI)) {
                cart.addItem(sI);
            }

            sI.setAmount(sI.getAmount());

            cart.fireShoppingCartChanged(sI, false);
        }

    }


    @FXML
    public void onClickADDALL() {
        addOrderToCart();
    }


}
