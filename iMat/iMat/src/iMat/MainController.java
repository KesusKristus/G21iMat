package iMat;

import iMat.Categories.CategoriesController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainController implements Initializable {


    @FXML
    AnchorPane startScreen;
    @FXML
    ScrollPane shoppingCartPane;

    @FXML
    ScrollPane productScrollPane;
    @FXML
    FlowPane productFlowPane;
    @FXML
    Pane productPane;
    @FXML
    Pane middlePane;
    @FXML
    Label categoryTitle;
    @FXML
    FlowPane shoppingCartFlowPane;

    @FXML
    TextField searchField;

    @FXML
    AnchorPane homePane;
    @FXML
    AnchorPane helpPane;
    @FXML
    Pane iMatPane;

    @FXML
    Label numberOfGoods;
    @FXML
    Label totalPrice;

    @FXML
    Label numberOfGoodsGREY;
    @FXML
    Label totalPriceGREY;

    @FXML
    Pane checkoutButtonPane;
    @FXML
    Pane checkoutGreyoutPane;

    @FXML
    FlowPane previouslyBoughtFlowPane;
    @FXML
    ScrollPane previouslyBoughtScrollPane;
    @FXML
    Label previouslyBoughtLabel;

    @FXML
    Pane dryckPane;
    @FXML
    Pane konto_pane;

    @FXML
    AnchorPane accountScreen = new AccountScreen(this);

    private CheckoutController checkoutController = new CheckoutController(this);
    @FXML
    AnchorPane checkoutScreen = checkoutController;

    private HistoryController historyController = new HistoryController(this);
    @FXML
    AnchorPane historyScreen = historyController;

    private CategoriesController categoriesController = new CategoriesController();

    private IMatDataHandler idh = IMatDataHandler.getInstance();

    private ShoppingCart shoppingCart = idh.getShoppingCart();

    ShoppingCartListener shoppingCartListener = new ShoppingCartListener() {
        @Override
        public void shoppingCartChanged(CartEvent cartEvent) {
            updateShoppingCart();
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Reset shoppingCart
        idh.getShoppingCart().clear();

        //Set up a listener for the shoppingCart
        shoppingCart.addShoppingCartListener(shoppingCartListener);

        //Set up a bunch of javafx stuff
        setupFX();

        //Show previously bought products
        updatePreviouslyBought();

        //fillListList();
        //sortListList();
    }

    private void setupFX() {
        middlePane.getChildren().add(accountScreen);
        accountScreen.toBack();
        middlePane.getChildren().add(checkoutScreen);
        checkoutScreen.toBack();
        middlePane.getChildren().add(historyScreen);
        historyScreen.toBack();

        startScreen.toFront();

        shoppingCartFlowPane.setVgap(5);
        previouslyBoughtFlowPane.setHgap(10);
        previouslyBoughtFlowPane.setVgap(10);
        productFlowPane.setHgap(10);
        productFlowPane.setVgap(10);
    }

    private void populateProducts(String title, List<ShoppingItem> sIList) {

        //Show to the product pane
        productPane.toFront();
        categoryTitle.setText(title);

        //Scroll to top
        productScrollPane.setVvalue(0);

        productFlowPane.getChildren().clear();

        for (ShoppingItem sI : sIList) {
            productFlowPane.getChildren().add(ProductCard.createProductCardCategory(sI));
        }
    }

    private void updateShoppingCart() {
        shoppingCartFlowPane.getChildren().clear();

        List<ShoppingItem> reversedCart = shoppingCart.getItems();
        Collections.reverse(reversedCart);

        for (ShoppingItem sI : reversedCart) {
            shoppingCartFlowPane.getChildren().add(ProductCard.createProductCardCart(sI));
        }
    }

    private void updatePreviouslyBought() {

        //Remove empty orders - there should be none, but if there are
        idh.getOrders().removeIf(o -> o.getItems().size() == 0);

        previouslyBoughtFlowPane.getChildren().clear();

        //Make a list with the newest order first
        List<Order> sortedOrderList = historyController.sortListNewestFirst();

        previouslyBoughtLabel.setText("Senast köpta varor");

        if (idh.getOrders().size() != 0) {
            Order lastOrder = sortedOrderList.get(0);

            for (ShoppingItem sI : lastOrder.getItems()) {
                previouslyBoughtFlowPane.getChildren().add(ProductCard.createProductCardCategory(sI));
            }
        } else
            previouslyBoughtLabel.setText("När ett köp utförts visas varorna här");

        previouslyBoughtScrollPane.setVvalue(0);

    }



    //Should always be called when leaving the homepage/startpage
    void showHomeButton() {
        homePane.toFront();
    }

    public void openHistoryPage() {
        historyController.populateDateList();
        productPane.toFront();
        historyScreen.toFront();

        //Show the home button
        showHomeButton();
    }

    public void openHomePage(){
        //Hide the home button
        iMatPane.toFront();

        startScreen.toFront();

        //updateShoppingCartButton();
    }


    @FXML
    void onClickHEM() {
        openHomePage();
    }


    //Perform search if the search text field is in focus and enter is pressed or if the search button is presesd
    @FXML
    void onEnter(ActionEvent ae) {
        //performSearch();
    }
    @FXML
    void onClickSök() {
        //performSearch();
    }

    @FXML
    void onClickKASSA() {

        checkoutController.setupCheckout();

        productPane.toBack();
        checkoutScreen.toFront();

        //greyoutCheckoutButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickKONTO() {
        productPane.toBack();
        accountScreen.toFront();

        //updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickHJÄLP() {
        productPane.toBack();
        helpPane.toFront();

        //updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickDRYCK() {
        /*populateCategoryScreen2(listList.get(0), "Dryck");

        updateShoppingCartButton();*/

        populateProducts("Dryck", categoriesController.getCategoryList(CategoriesController.Categories.DRYCK));

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickFRUKTBÄR() {
        /*populateCategoryScreen2(listList.get(1), "Frukt & Bär");

        updateShoppingCartButton();*/

        populateProducts("Frukt & Bär", categoriesController.getCategoryList(CategoriesController.Categories.FRUKT));

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickGRÖNSAKER() {
        /*populateCategoryScreen2(listList.get(2), "Grönsaker");

        updateShoppingCartButton();*/

        populateProducts("Grönsaker",categoriesController.getCategoryList(CategoriesController.Categories.GRÖNT));

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickKÖTTFISK() {
        /*populateCategoryScreen2(listList.get(3), "Kött & Fisk");

        updateShoppingCartButton();*/

        populateProducts("Kött & Fisk",categoriesController.getCategoryList(CategoriesController.Categories.KÖTT));

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickMEJERI() {
        /*populateCategoryScreen2(listList.get(4), "Mejeri");

        updateShoppingCartButton();*/

        populateProducts("Mejeri",categoriesController.getCategoryList(CategoriesController.Categories.MEJERI));

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickPOTATISRIS() {
        /*populateCategoryScreen2(listList.get(5), "Potatis & Ris");

        updateShoppingCartButton();*/

        populateProducts("Potatis & Ris",categoriesController.getCategoryList(CategoriesController.Categories.POTATIS));

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickSKAFFERI() {
        /*populateCategoryScreen2(listList.get(6), "Skafferi");

        updateShoppingCartButton();*/

        populateProducts("Skafferi",categoriesController.getCategoryList(CategoriesController.Categories.SKAFFERI));

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickSÖTSAKERSNACKS() {
        /*populateCategoryScreen2(listList.get(7), "Sötsaker & Snacks");

        updateShoppingCartButton();*/

        populateProducts("Sötsaker & Snacks",categoriesController.getCategoryList(CategoriesController.Categories.SÖTT));

        //Visa hemknappen
        showHomeButton();

    }

    //private java.util.List<ShoppingItem> shoppingCartList = new ArrayList<ShoppingItem>();
    //private ShoppingCart shoppingCart = idh.getShoppingCart();
    //private java.util.List<ProductCard> cardList = new ArrayList<ProductCard>();
    //private java.util.List<java.util.List<ProductCard>> listList = new ArrayList<java.util.List<ProductCard>>();

    /*public List<ProductCard> getCardList() {
        return cardList;
    }

    public List<List<ProductCard>> getListList() {
        return listList;
    }*/

    /*



    void performSearch() {

        String searchString = searchField.getText();

        if (searchString.length() != 0) {
            //populateSearchScreen(searchString);
        }

        searchField.setText("");
        homePane.requestFocus();
    }



    public void greyoutCheckoutButton() {

        double goodsSum = 0;
        double priceSum = 0;
        for (ProductCard p : cardList) {
            goodsSum += p.getItem().getAmount();
            priceSum += p.getItem().getTotal();
        }

        numberOfGoodsGREY.setText("" + (int) goodsSum + " st");
        totalPriceGREY.setText("" + Math.round(priceSum * 100D) / 100D + " kr");

        checkoutGreyoutPane.toFront();
    }

    void showCheckoutButton() {
        checkoutButtonPane.toFront();
    }

    //Återvänd till startsidan



    public void populateSearchScreen(String searchString) {
        productPane.toFront();
        productFlowPane.setHgap(10);
        productFlowPane.setVgap(10);

        productScrollPane.setVvalue(0);

        //update title
        categoryTitle.setText("Sökresultat för '" + searchString + "'");

        productFlowPane.getChildren().clear();

        List<Product> products = idh.findProducts(searchString);

        for (Product p : products) {
            productFlowPane.getChildren().add(new ProductCard(p, ProductCard.cardType.category, this));
        }

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    public void populateCategoryScreen2(List<ProductCard> cards, String title) {
        productPane.toFront();
        productFlowPane.setHgap(10);
        productFlowPane.setVgap(10);
        productScrollPane.setVvalue(0);
        categoryTitle.setText(title);
        productFlowPane.getChildren().clear();
        for (ProductCard p : cards) {
            productFlowPane.getChildren().add(p);
        }
    }

    public void productAdded(ProductCard card) {
        boolean exists = false;
        for (ProductCard p : cardList) {
            if (card.getItem().getProduct().getProductId() == p.getItem().getProduct().getProductId()) {
                exists = true;
            }
        }
        if (!exists) {
            cardList.add(card);
            shoppingCart.addItem(card.getItem());
        }
    }

    public void productDeleted(ProductCard card) {
        ProductCard removedCard = null;
        for (ProductCard p : cardList) {
            if (card.equals(p))
                removedCard = p;
        }
        if (removedCard != null) {
            cardList.remove(removedCard);
            shoppingCart.removeItem(removedCard.getItem());
        }
    }

    public void clearShoppingCart() {
        cardList.clear();
    }

    public void updateShoppingCart() {
        shoppingCartFlowPane.getChildren().clear();
        shoppingCartFlowPane.setVgap(5);
        for (ProductCard card : cardList) {
            shoppingCartFlowPane.getChildren().add(new ProductCard(card, this));
        }
        updateShoppingCartButton();
        updatePreviouslyBought();
    }

    public void updateShoppingCartButton() {

        double goodsSum = 0;
        double priceSum = 0;
        for (ProductCard p : cardList) {
            goodsSum += p.getItem().getAmount();
            priceSum += p.getItem().getTotal();
        }
        numberOfGoods.setText("" + (int) goodsSum + " st");
        totalPrice.setText("" + Math.round(priceSum * 100D) / 100D + " kr");

        if (goodsSum == 0) {
            greyoutCheckoutButton();
        } else
            showCheckoutButton();
    }


    public void fillListList() {
        for (int i = 0; i < 8; i++) {
            listList.add(new ArrayList<ProductCard>());
        }
        for (Product p : cController.dryckList) {
            listList.get(0).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for (Product p : cController.fruktBärList) {
            listList.get(1).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for (Product p : cController.grönsakerList) {
            listList.get(2).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for (Product p : cController.köttFiskList) {
            listList.get(3).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for (Product p : cController.mejeriList) {
            listList.get(4).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for (Product p : cController.potatisRisList) {
            listList.get(5).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for (Product p : cController.skafferiList) {
            listList.get(6).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
        for (Product p : cController.sötsakerSnacksList) {
            listList.get(7).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
    }

    public void sortListList() {
        for (int i = 0; i < 8; i++) {
            java.util.List<String> nameOrder = new ArrayList<String>();
            java.util.List<ProductCard> newProductList = new ArrayList<ProductCard>();

            for (ProductCard p : listList.get(i)) {
                nameOrder.add(p.getItem().getProduct().getName());
            }

            java.util.Collections.sort(nameOrder);

            for (String s : nameOrder) {
                for (ProductCard p : listList.get(i)) {
                    if (s.equals(p.getItem().getProduct().getName())) {
                        newProductList.add(p);
                    }
                }

            }
            listList.set(i, newProductList);
        }
    }

    public void clearFillSortListList() {
        listList.clear();
        fillListList();
        sortListList();
    }

    public void updatePreviouslyBought() {

        //Remove empty orders - there should be none, but if there are
        idh.getOrders().removeIf(o -> o.getItems().size() == 0);

        previouslyBoughtFlowPane.getChildren().clear();

        previouslyBoughtFlowPane.setHgap(10);
        previouslyBoughtFlowPane.setVgap(10);

        //Make a list with the newest order first
        List<Order> sortedOrderList = historyController.sortListNewestFirst();

        previouslyBoughtLabel.setText("Senast köpta varor");

        if (idh.getOrders().size() != 0) {
            Order lastOrder = sortedOrderList.get(0);

            for (ShoppingItem sI : lastOrder.getItems()) {
                previouslyBoughtFlowPane.getChildren().add(new ProductCard(sI, this));
            }
        } else
            previouslyBoughtLabel.setText("När ett köp utförts visas varorna här");

        previouslyBoughtScrollPane.setVvalue(0);
    }*/


    public IMatDataHandler getDataHandler() {
        return idh;
    }
}
