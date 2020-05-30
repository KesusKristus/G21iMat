package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    private ShoppingItem shoppingItem = new ShoppingItem(null, 0);
    private Product prod;
    Image productImage;
    private MainController parentController;
    private ProductCard parentCard;
    private IMatDataHandler idh = IMatDataHandler.getInstance();
    private ShoppingCart cart = idh.getShoppingCart();

    @FXML
    ImageView cardImage;
    @FXML
    Label cardName;
    @FXML
    Label cardPrice;
    /*@FXML
    Pane plusButton;
    @FXML
    Pane minusButton;*/
    @FXML
    Label cardAmount;

    private ProductCard(ShoppingItem item, boolean isCategory) {

        shoppingItem = item;

        prod = item.getProduct();

        FXMLLoader fxmlLoader;

        if (isCategory) {
            fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
            productImage = idh.getFXImage(item.getProduct(), 100, 80);
        } else {
            fxmlLoader = new FXMLLoader(getClass().getResource("product_card_cart.fxml"));
            productImage = idh.getFXImage(item.getProduct(), 90, 90);
        }

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        //Set up card
        cardImage.setImage(productImage);
        cardName.setText(prod.getName());
        cardPrice.setText(prod.getPrice() + " " + prod.getUnit());
        cardAmount.setText(Integer.toString((int) item.getAmount()));
    }

    public static ProductCard createProductCardCategory(ShoppingItem item) {
        //Use this when creating ProductCards for other places than the Shopping cart
        return new ProductCard(item, true);
    }

    public static ProductCard createProductCardCart(ShoppingItem item) {
        //Use this when creating ProductCards for the Shopping cart
        return new ProductCard(item, false);
    }

    private void updateCard(){
        cardAmount.setText(Integer.toString((int)shoppingItem.getAmount()));
    }

    @FXML
    public void plusButtonPressed() {

        if (!cart.getItems().contains(shoppingItem)) {
            cart.addItem(shoppingItem);
        }

        shoppingItem.setAmount(shoppingItem.getAmount() + 1);

        cart.fireShoppingCartChanged(shoppingItem, false);

        updateCard();

        /*if (!cart.getItems().contains(shoppingItem)){
            cart.addItem(shoppingItem);
        }*/



        /*item.setAmount(item.getAmount() + 1);
        parentController.productAdded(this);
        parentController.updateShoppingCart();

        updateAmount();

        if(parentCard != null){
            updateParentCard();
        }*/

    }

    @FXML
    public void minusButtonPressed() {

        if (cart.getItems().contains(shoppingItem)) {

            if (shoppingItem.getAmount() > 1) {
                shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            } else if (shoppingItem.getAmount() == 1){
                cart.removeItem(shoppingItem);
                shoppingItem.setAmount(0);
            }

            cart.fireShoppingCartChanged(shoppingItem, false);

            updateCard();

            /*if (shoppingItem.getAmount() == 1){
                cart.removeItem(shoppingItem);
            }*/


            /*shoppingItem.setAmount(shoppingItem.getAmount() + 1);*/
        }

        /*if((int) item.getAmount() > 0) {
            item.setAmount(item.getAmount() - 1);

            if ((int) item.getAmount() == 0) {    //Amount är en double, detta måste lösas för varor som kan vara ex 0,5kg om det finns.
                parentController.productDeleted(this);
            }

            updateAmount(); //Kan gå under noll
            parentController.updateShoppingCart();

            if (parentCard != null) {
                updateParentCard();
            }
        }*/
    }

    /*
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
            cardPrice.setText("" + product.getPrice() + " " + product.getUnit());


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
        cardPrice.setText( "" + item.getProduct().getPrice() + " " + item.getProduct().getUnit() );
        cardImage.setImage(IMatDataHandler.getInstance().getFXImage(item.getProduct(), 90, 70));
        cardAmount.setText("" + (int) item.getAmount());
    }


    //Denna är för historiken
    public ProductCard(ShoppingItem shoppingItem, MainController parentController){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
        this.item = shoppingItem; //va bara intem
        this.parentController = parentController;

        cardName.setText(item.getProduct().getName());
        cardPrice.setText( "" + item.getProduct().getPrice() + " " + item.getProduct().getUnit() );
        cardImage.setImage(idh.getFXImage(item.getProduct(), 90, 70));


        //DETTA GÖR SÅ ATT VARORNA I HISTORIKENS MÄNGD STÄMMER MED HUR MÅNGA MAN HAR I KUNDVAGNEN ATM
        item.setAmount(0);

        for (ShoppingItem sI : idh.getShoppingCart().getItems()){
            if (sI.getProduct().getProductId() == item.getProduct().getProductId()){
                item.setAmount(sI.getAmount());
                break;
            }
        }

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
    }*/
}
