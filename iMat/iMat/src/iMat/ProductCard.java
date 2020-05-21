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


    @FXML ImageView cardImage;
    @FXML Label cardName;
    @FXML Label cardPrice;
    @FXML Pane plusButton;
    @FXML Pane minusButton;
    @FXML Label amount;



    enum cardType{
        category,
        cart
    }

    @FXML public void plusButtonPressed(){
        item.setAmount(item.getAmount() + 1);
        parentController.productAdded(this.item);
        parentController.updateShoppingCart();

    }

    @FXML public void minusButtonPressed(){
        item.setAmount(item.getAmount() - 1);
        if(item.getAmount() < 1 ){                      //Amount är en double, detta måste lösas för varor som kan vara ex 0,5kg om det finns.
            parentController.productDeleted(item);
        }
        parentController.updateShoppingCart();
    }

    private void updateAmount(){
        amount.setText("" + item.getAmount());
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


            if (type == cardType.category){
                cardImage.setImage(IMatDataHandler.getInstance().getFXImage(product, 100, 80));
            } else {
                cardImage.setImage(IMatDataHandler.getInstance().getFXImage(product, 90, 90));
            }



            this.item.setProduct(product);
            this.parentController = parentController;
            //this.amount.setText("" + this.item.getAmount());


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
        //this.amount.setText("" + item.getAmount());
    }











}
