package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ReceiptCard extends AnchorPane {
    private ShoppingItem item = new ShoppingItem(null, 0);

    @FXML
    Label kvittoAmount;
    @FXML
    Label kvittoName;
    @FXML
    Label kvittoPrice;


    public ReceiptCard(ShoppingItem shoppingItem){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("receipt_card.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }

        item = shoppingItem;

        kvittoAmount.setText("" + (int)item.getAmount() + " st");
        kvittoName.setText(item.getProduct().getName());
        kvittoPrice.setText(item.getProduct().getPrice() + " kr/st");

    }


}
