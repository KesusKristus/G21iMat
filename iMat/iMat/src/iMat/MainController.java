package iMat;

import iMat.Categories.CategoriesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {


    @FXML
    AnchorPane startScreen;
    @FXML
    ScrollPane shoppingCartScrollPane;

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

    private CategoriesController categoriesController = new CategoriesController();

    private HistoryController historyController = new HistoryController(this, categoriesController);
    @FXML
    AnchorPane historyScreen = historyController;

    private IMatDataHandler idh = IMatDataHandler.getInstance();

    private ShoppingCart shoppingCart = idh.getShoppingCart();

    ShoppingCartListener shoppingCartListener = new ShoppingCartListener() {
        @Override
        public void shoppingCartChanged(CartEvent cartEvent) {
            updateShoppingCart();
            updateProducts();
            updateShoppingCartButton();
            historyController.updateProductListHistory();
        }
    };

    private List<ShoppingItem> currentProductsOnShow = new ArrayList<>();

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
        currentProductsOnShow = sIList;

        //Show the home button / update whether the checkout button is greyed out or not
        updateButtonStates();
    }

    private void updateProducts() {
        productFlowPane.getChildren().clear();

        for (ShoppingItem sI : currentProductsOnShow) {
            productFlowPane.getChildren().add(ProductCard.createProductCardCategory(sI));
        }
    }

    private void updateShoppingCart() {
        shoppingCartFlowPane.getChildren().clear();

        for (ShoppingItem sI : shoppingCart.getItems()) {
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
                previouslyBoughtFlowPane.getChildren().add(ProductCard.createProductCardHistory(categoriesController.findShoppingItem(sI.getProduct().getProductId()), sI.getAmount(), categoriesController));
            }
        } else
            previouslyBoughtLabel.setText("När ett köp utförts visas varorna här");

        previouslyBoughtScrollPane.setVvalue(0);

    }



    public void updateShoppingCartButton() {

        int nGoods = 0;

        for (ShoppingItem sI : shoppingCart.getItems()){
            nGoods += sI.getAmount();
        }

        numberOfGoods.setText("" + (int) nGoods + " st");
        numberOfGoodsGREY.setText("" + (int) nGoods + " st");
        totalPrice.setText("" + Math.round(shoppingCart.getTotal() * 100D) / 100D + " kr");
        totalPriceGREY.setText("" + Math.round(shoppingCart.getTotal() * 100D) / 100D + " kr");

        if (shoppingCart.getItems().size() == 0) {
            showCheckoutButton(false);
        } else
            showCheckoutButton(true);
    }

    void showCheckoutButton(boolean shouldItShow) {
        if (shouldItShow){
            checkoutButtonPane.toFront();
        } else {
            checkoutGreyoutPane.toFront();
        }


    }

    //Should always be called when leaving the homepage/startpage
    void updateButtonStates() {
        homePane.toFront();
        updateShoppingCartButton();
    }

    public void openHistoryPage() {
        historyController.populateDateList();
        productPane.toFront();
        historyScreen.toFront();

        //Show the home button
        updateButtonStates();
    }

    public void openHomePage(){
        //Hide the home button
        iMatPane.toFront();

        startScreen.toFront();

        updatePreviouslyBought();
        updateShoppingCartButton();
    }

    void performSearch() {

        String searchString = searchField.getText();

        if (searchString.length() != 0) {
            populateSearchScreen(searchString);
        }

        searchField.setText("");
        homePane.requestFocus();
    }

    public void populateSearchScreen(String searchString) {
        productPane.toFront();

        //update title
        categoryTitle.setText("Sökresultat för '" + searchString + "'");

        populateProducts("Sökresultat för '" + searchString + "'", categoriesController.searchShoppingItems(searchString));
    }

    @FXML
    void onClickHEM() {
        openHomePage();
    }

    //Perform search if the search text field is in focus and enter is pressed or if the search button is presesd
    @FXML
    void onEnter(ActionEvent ae) {
        performSearch();
    }
    @FXML
    void onClickSök() {
        performSearch();
    }

    @FXML
    void onClickKASSA() {

        checkoutController.setupCheckout();

        productPane.toBack();
        checkoutScreen.toFront();

        //Visa hemknappen
        updateButtonStates();

        showCheckoutButton(false);
    }

    @FXML
    void onClickKONTO() {
        productPane.toBack();
        accountScreen.toFront();

        //Show the home button / update whether the checkout button is greyed out or not
        updateButtonStates();
    }

    @FXML
    void onClickHJÄLP() {
        productPane.toBack();
        helpPane.toFront();

        //Show the home button / update whether the checkout button is greyed out or not
        updateButtonStates();
    }

    @FXML
    void onClickDRYCK() {
        populateProducts("Dryck", categoriesController.getCategoryList(CategoriesController.Categories.DRYCK));
    }

    @FXML
    void onClickFRUKTBÄR() {
        populateProducts("Frukt & Bär", categoriesController.getCategoryList(CategoriesController.Categories.FRUKT));
    }

    @FXML
    void onClickGRÖNSAKER() {
        populateProducts("Grönsaker",categoriesController.getCategoryList(CategoriesController.Categories.GRÖNT));
    }

    @FXML
    void onClickKÖTTFISK() {
        populateProducts("Kött & Fisk",categoriesController.getCategoryList(CategoriesController.Categories.KÖTT));
    }

    @FXML
    void onClickMEJERI() {
        populateProducts("Mejeri",categoriesController.getCategoryList(CategoriesController.Categories.MEJERI));
    }

    @FXML
    void onClickPOTATISRIS() {
        populateProducts("Potatis & Ris",categoriesController.getCategoryList(CategoriesController.Categories.POTATIS));
    }

    @FXML
    void onClickSKAFFERI() {
        populateProducts("Skafferi",categoriesController.getCategoryList(CategoriesController.Categories.SKAFFERI));
    }

    @FXML
    void onClickSÖTSAKERSNACKS() {
        populateProducts("Sötsaker & Snacks",categoriesController.getCategoryList(CategoriesController.Categories.SÖTT));
    }

    public CategoriesController getCategoriesController() {
        return categoriesController;
    }
}
