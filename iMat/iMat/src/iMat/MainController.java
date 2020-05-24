package iMat;

import iMat.Categories.CategoriesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML AnchorPane startScreen;
    @FXML ScrollPane shoppingCartPane;
    @FXML AnchorPane accountScreen = new AccountScreen();

    CheckoutController checkoutController = new CheckoutController(this);
    @FXML AnchorPane checkoutScreen = checkoutController;//new CheckoutController();

    HistoryController historyController = new HistoryController();
    @FXML AnchorPane historyScreen = historyController;

    private CategoriesController cController = new CategoriesController();

    @FXML ScrollPane productScrollPane;
    @FXML FlowPane productFlowPane;
    @FXML Pane productPane;
    @FXML Pane middlePane;
    @FXML Label categoryTitle;
    @FXML FlowPane shoppingCartFlowPane;

    @FXML TextField searchField;

    @FXML AnchorPane homePane;
    @FXML AnchorPane helpPane;
    @FXML Pane iMatPane;

    @FXML Label numberOfGoods;
    @FXML Label totalPrice;

    @FXML Pane checkoutButtonPane;
    @FXML Pane checkoutGreyoutPane;

    IMatDataHandler idh = IMatDataHandler.getInstance();

    private MainController parentController;
    private java.util.List<ProductCategory> subCategories;
    //private java.util.List<ShoppingItem> shoppingCartList = new ArrayList<ShoppingItem>();
    private ShoppingCart shoppingCart = idh.getShoppingCart();
    private java.util.List<ProductCard> cardList = new ArrayList<ProductCard>();
    private java.util.List<java.util.List<ProductCard>> listList = new ArrayList<java.util.List<ProductCard>>();

    @FXML Pane dryckPane;
    @FXML Pane konto_pane;


    //När enter trycks från sökrutan
    @FXML
    void onEnter(ActionEvent ae){

        String searchString = searchField.getText();

        populateSearchScreen(idh.findProducts(searchString), "Sökresultat för '" + searchString + "'");
    }

    //Återvänd till startsidan
    @FXML
    void onClickHEM() {
        iMatPane.toFront();
        startScreen.toFront();

        showCheckoutButton();
    }

    //Ska kallas då "startsidan" lämnas
    void showHomeButton() {
        homePane.toFront();
    }

    void greyoutCheckoutButton(){checkoutGreyoutPane.toFront();}
    void showCheckoutButton(){checkoutButtonPane.toFront();}

    @FXML
    void onClickKASSA() {

        checkoutController.setupCheckout();

        productPane.toBack();
        checkoutScreen.toFront();

        greyoutCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickKONTO() {
        productPane.toBack();
        accountScreen.toFront();

        showCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickHJÄLP() {
        productPane.toBack();
        helpPane.toFront();

        showCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickDRYCK(){
        //populateCategoryScreen(cController.dryckList, "Dryck");
        populateCategoryScreen2(listList.get(0), "Dryck");

        showCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickFRUKTBÄR(){
        //populateCategoryScreen(cController.fruktBärList, "Frukt & bär");
        populateCategoryScreen2(listList.get(1), "Frukt & Bär");
        showCheckoutButton();
        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickGRÖNSAKER(){
        //populateCategoryScreen(cController.grönsakerList, "Grönsaker");
        populateCategoryScreen2(listList.get(2), "Grönsaker");              //hitils bara ändrat de tre första
        showCheckoutButton();
        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickKÖTTFISK(){
        //populateCategoryScreen(cController.köttFiskList, "Kött & fisk");
        populateCategoryScreen2(listList.get(3), "Kött & Fisk");
        showCheckoutButton();
        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickMEJERI(){
        //populateCategoryScreen(cController.mejeriList, "Mejeri");
        populateCategoryScreen2(listList.get(4), "Mejeri");
        showCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickPOTATISRIS(){
        //populateCategoryScreen(cController.potatisRisList, "Potatis & ris");
        populateCategoryScreen2(listList.get(5), "Potatis & Ris");
        showCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickSKAFFERI(){
        //populateCategoryScreen(cController.skafferiList, "Skafferi");
        populateCategoryScreen2(listList.get(6), "Skafferi");
        showCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickSÖTSAKERSNACKS(){
        //populateCategoryScreen(cController.sötsakerSnacksList, "Sötsaker & snacks");
        populateCategoryScreen2(listList.get(7), "Sötsaker & Snacks");
        showCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    public void populateSearchScreen(List<Product> products, String title){
        productPane.toFront();
        productFlowPane.setHgap(10);
        productFlowPane.setVgap(10);

        productScrollPane.setVvalue(0);

        categoryTitle.setText(title);
        //this.products = products;
        productFlowPane.getChildren().clear();

        for(Product p : products){
            productFlowPane.getChildren().add(new ProductCard(p, ProductCard.cardType.category, this));
        }
    }

    public void populateCategoryScreen2(List<ProductCard> cards, String title){
        productPane.toFront();
        productFlowPane.setHgap(10);
        productFlowPane.setVgap(10);
        productScrollPane.setVvalue(0);
        categoryTitle.setText(title);
        productFlowPane.getChildren().clear();
        for(ProductCard p : cards){
            productFlowPane.getChildren().add(p);
        }
    }

    public void productAdded(ProductCard card){
        boolean exists = false;
            for (ProductCard p : cardList) {
                if (card.equals(p)){
                    exists = true;
                }
            }
            if (!exists) {
                cardList.add(card);
                shoppingCart.addItem(card.getItem());
            }
    }

    public void productDeleted(ProductCard card){
        ProductCard removedCard = null;
        for(ProductCard p : cardList){
            if(card.equals(p))
                    removedCard = p;
        }
        if(removedCard != null) {
            cardList.remove(removedCard);
            shoppingCart.removeItem(removedCard.getItem());
        }
    }

    public void clearShoppingCart(){
        cardList.clear();
    }

    public void updateShoppingCart(){
        shoppingCartFlowPane.getChildren().clear();
        shoppingCartFlowPane.setVgap(5);
        for( ProductCard card : cardList){
            shoppingCartFlowPane.getChildren().add(new ProductCard(card, this));
        }
        updateShoppingCartButton();
    }

    public void updateShoppingCartButton(){
        double goodsSum = 0;
        double priceSum = 0;
        for(ProductCard p : cardList){
            goodsSum += p.getItem().getAmount();
            priceSum += p.getItem().getTotal();
        }
        numberOfGoods.setText("" + (int) goodsSum + " st");
        totalPrice.setText("" + Math.round(priceSum * 100D) / 100D  + " kr");
    }


    public void fillListList(){
        for(int i = 0; i < 8; i++){
            listList.add(new ArrayList<ProductCard>());
        }
        for( Product p : cController.dryckList){
            listList.get(0).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for( Product p : cController.fruktBärList){
            listList.get(1).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for( Product p : cController.grönsakerList){
            listList.get(2).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for( Product p : cController.köttFiskList){
            listList.get(3).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for( Product p : cController.mejeriList){
            listList.get(4).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for( Product p : cController.potatisRisList){
            listList.get(5).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for( Product p : cController.skafferiList){
            listList.get(6).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for( Product p : cController.sötsakerSnacksList){
            listList.get(7).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
    }

    public void sortListList() {
        for(int i = 0; i < 8; i++){
            java.util.List<String> nameOrder = new ArrayList<String>();
            java.util.List<ProductCard> newProductList = new ArrayList<ProductCard>();

            for(ProductCard p : listList.get(i)){
                nameOrder.add(p.getItem().getProduct().getName());
            }

            java.util.Collections.sort(nameOrder);

            for(String s : nameOrder){
                for(ProductCard p : listList.get(i)){
                    if (s.equals(p.getItem().getProduct().getName())){
                        newProductList.add(p);
                    }
                }

            }
            listList.set(i, newProductList);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        middlePane.getChildren().add(accountScreen);
        accountScreen.toBack();
        middlePane.getChildren().add(checkoutScreen);
        checkoutScreen.toBack();
        middlePane.getChildren().add(historyScreen);
        historyScreen.toFront();
        fillListList();
        sortListList();
    }

    public IMatDataHandler getDataHandler() {
        return idh;
    }
}
