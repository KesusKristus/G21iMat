package iMat;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    private Product product;
    private MainController parentController;
    private int amount = 0;

    @FXML ImageView cardImage;
    @FXML Label cardName;
    @FXML Label cardPrice;



    public enum cardType{
        category,
        cart
    }






    public ProductCard(Product product, MainController parentController, cardType type){ //kanske ändra från mainController. Samma klass för productCard och cartCard

        FXMLLoader fxmlLoader;
        if(type == cardType.category) {
            fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(getClass().getResource("product_card_cart.fxml"));
        }
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try{
                fxmlLoader.load();
            } catch (IOException exception){
                throw new RuntimeException(exception);
            }

            cardName.setText(product.getName());
            cardPrice.setText("" + product.getPrice());
            cardImage.setImage(parentController.getDataHandler().getFXImage(product, 130, 130));

            this.product = product;
            this.parentController = parentController;

    }











}
