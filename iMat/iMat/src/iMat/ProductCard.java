package iMat;

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
    private ProductCard parentCard;


    @FXML ImageView cardImage;
    @FXML Label cardName;
    @FXML Label cardPrice;
    @FXML Pane plusButton;
    @FXML Pane minusButton;
    @FXML Label cardAmount;



    enum cardType{
        category,
        cart
    }

    @FXML public void plusButtonPressed(){
        item.setAmount(item.getAmount() + 1);
        parentController.productAdded(this);
        parentController.updateShoppingCart();

        updateAmount();

        if(parentCard != null){
            updateParentCard();
        }

    }

    @FXML public void minusButtonPressed(){
        boolean deleted = false;
        item.setAmount(item.getAmount() - 1);

        updateAmount(); //Kan gå under noll
        parentController.updateShoppingCart();


        if((int) item.getAmount() == 0 ){    //Amount är en double, detta måste lösas för varor som kan vara ex 0,5kg om det finns.
            parentController.productDeleted(this);
            deleted = true;
        }

        if(parentCard != null){
            updateParentCard();
        }

    }

    private void updateAmount(){
        cardAmount.setText("" + (int)item.getAmount());
    }



    private void updateParentCard(){
        parentCard.cardAmount.setText("" + (int)item.getAmount());
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
            cardPrice.setText("" + product.getPrice() + ":-");


            if (type == cardType.category){
                cardImage.setImage(IMatDataHandler.getInstance().getFXImage(product, 100, 80));
            } else {
                cardImage.setImage(IMatDataHandler.getInstance().getFXImage(product, 90, 90));
            }



            this.item.setProduct(product);
            this.parentController = parentController;
            cardAmount.setText("" + (int) this.item.getAmount());


    }

    public ProductCard(ProductCard parentCard, MainController parentController){ //denna va ShoppingItem


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card_cart.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
        this.parentCard = parentCard;
        this.item = parentCard.item; //va bara intem
        this.parentController = parentController;

        cardName.setText(item.getProduct().getName());
        cardPrice.setText( "" + item.getProduct().getPrice() + ":-" );
        cardImage.setImage(IMatDataHandler.getInstance().getFXImage(item.getProduct(), 90, 70));
        cardAmount.setText("" + (int) item.getAmount());
    }


    public ShoppingItem getItem() {
        return item;
    }



    public boolean equals(ProductCard p){
        if(this.getItem().equals(p.getItem())){
            return true;
        }
        return false;
    }
}
