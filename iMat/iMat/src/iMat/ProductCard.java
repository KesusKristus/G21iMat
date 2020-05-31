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
    @FXML
    Label historyAmount;

    private ShoppingItem shoppingItem = new ShoppingItem(null, 0);

    private final IMatDataHandler idh = IMatDataHandler.getInstance();
    private final ShoppingCart cart = idh.getShoppingCart();
    private final Product prod;
    private final Image productImage;

    //For normal cards and the shopping cart
    private ProductCard(ShoppingItem item, boolean isCategory) {

        shoppingItem = item;

        prod = item.getProduct();

        FXMLLoader fxmlLoader;

        if (isCategory) {
            fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
            productImage = idh.getFXImage(prod, 100, 80);
        } else {
            fxmlLoader = new FXMLLoader(getClass().getResource("product_card_cart.fxml"));
            productImage = idh.getFXImage(prod, 90, 70);
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

    //For the history and previously bought cards
    private ProductCard(ShoppingItem item, double amount) {

        shoppingItem = item;

        prod = item.getProduct();

        productImage = idh.getFXImage(prod, 100, 70);

        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(getClass().getResource("product_card_history.fxml"));

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

        historyAmount.setText("Senast köpt antal: " + (int)amount + " st");
    }

    public static ProductCard createProductCardCategory(ShoppingItem item) {
        //Use this when creating ProductCards for other places than the Shopping cart
        return new ProductCard(item, true);
    }

    public static ProductCard createProductCardCart(ShoppingItem item) {
        //Use this when creating ProductCards for the Shopping cart
        return new ProductCard(item, false);
    }

    public static ProductCard createProductCardHistory(ShoppingItem item, double amount) {
        //Use this when creating ProductCards for the history and previously bought
        return new ProductCard(item, amount);
    }

    private void updateCard() {
        cardAmount.setText(Integer.toString((int) shoppingItem.getAmount()));
    }

    @FXML
    public void plusButtonPressed() {

        if (!cart.getItems().contains(shoppingItem)) {
            cart.addItem(shoppingItem);
        }

        shoppingItem.setAmount(shoppingItem.getAmount() + 1);

        cart.fireShoppingCartChanged(shoppingItem, false);

        updateCard();
    }

    @FXML
    public void minusButtonPressed() {

        if (cart.getItems().contains(shoppingItem)) {

            if (shoppingItem.getAmount() > 1) {
                shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            } else if (shoppingItem.getAmount() == 1) {
                cart.removeItem(shoppingItem);
                shoppingItem.setAmount(0);
            }

            cart.fireShoppingCartChanged(shoppingItem, false);

            updateCard();
        }
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
