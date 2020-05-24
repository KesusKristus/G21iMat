package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.util.Date;

public class HistoryCardController extends AnchorPane {

    Date date;

    Order order;

    @FXML
    Label cardDateLabel;

    HistoryController controller;

    public HistoryCardController(HistoryController historyController, Order order) {

        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("history_list_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        controller = historyController;

        this.order = order;
        date = order.getDate();

        cardDateLabel.setText(date.toString().substring(0, 16));
    }


    @FXML
    void onClick() {
        System.out.println("CLICKED POP LIST PLS");
        controller.populateProductList(order);
    }


}
