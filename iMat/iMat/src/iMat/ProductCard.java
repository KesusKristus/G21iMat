package iMat;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    private ShoppingItem item = new ShoppingItem(null, 0);
    private MainController parentController;
    private int amount = 0;

    @FXML ImageView cardImage;
    @FXML Label cardName;
    @FXML Label cardPrice;
    @FXML Pane plusButton;



    enum cardType{
        category,
        cart
    }

    @FXML public void plusButtonPressed(){
        item.setAmount(item.getAmount() + 1);
        parentController.productAdded(this.item);
        parentController.updateShoppingCart();

    }

    public ProductCard(Product product, cardType type, MainController parentController) { //kanske ändra från mainController. Samma klass för productCard och cartCard

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

            cardImage.setImage(IMatDataHandler.getInstance().getFXImage(product, 160, 80));

            //cardImage.setImage(parentController.getDataHandler().getFXImage(product, 130, 130));

            this.item.setProduct(product);
            this.parentController = parentController;

    }

    public ProductCard(ShoppingItem item, MainController parentController){


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card_cart.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }

        this.item = item;
        this.parentController = parentController;

        cardName.setText(item.getProduct().getName());
        cardPrice.setText( "" + item.getProduct().getPrice() + ":-" );
        cardImage.setImage(IMatDataHandler.getInstance().getFXImage(item.getProduct(), 90, 70));
    }











}
