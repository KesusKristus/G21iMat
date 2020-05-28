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

    @FXML
    AnchorPane startScreen;
    @FXML
    ScrollPane shoppingCartPane;
    @FXML
    AnchorPane accountScreen = new AccountScreen(this);

    CheckoutController checkoutController = new CheckoutController(this);
    @FXML
    AnchorPane checkoutScreen = checkoutController;//new CheckoutController();

    HistoryController historyController = new HistoryController(this);
    @FXML
    AnchorPane historyScreen = historyController;

    private CategoriesController cController = new CategoriesController();

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

    IMatDataHandler idh = IMatDataHandler.getInstance();

    private MainController parentController;
    private java.util.List<ProductCategory> subCategories;
    //private java.util.List<ShoppingItem> shoppingCartList = new ArrayList<ShoppingItem>();
    private ShoppingCart shoppingCart = idh.getShoppingCart();
    private java.util.List<ProductCard> cardList = new ArrayList<ProductCard>();
    private java.util.List<java.util.List<ProductCard>> listList = new ArrayList<java.util.List<ProductCard>>();

    @FXML
    Pane dryckPane;
    @FXML
    Pane konto_pane;


    public List<ProductCard> getCardList() {
        return cardList;
    }

    public List<List<ProductCard>> getListList() {
        return listList;
    }

    //När enter trycks från sökrutan
    @FXML
    void onEnter(ActionEvent ae) {

        String searchString = searchField.getText();

        populateSearchScreen(idh.findProducts(searchString), "Sökresultat för '" + searchString + "'");
    }


    //Ska kallas då "startsidan" lämnas
    void showHomeButton() {
        homePane.toFront();
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

    public void openHistoryPane() {
        historyController.populateDateList();
        productPane.toFront();
        historyScreen.toFront();

        //Visa hemknappen
        showHomeButton();
    }

    //Återvänd till startsidan
    @FXML
    void onClickHEM() {
        iMatPane.toFront();
        startScreen.toFront();

        updateShoppingCartButton();
    }

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

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickHJÄLP() {
        productPane.toBack();
        helpPane.toFront();

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickDRYCK() {
        populateCategoryScreen2(listList.get(0), "Dryck");

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickFRUKTBÄR() {
        populateCategoryScreen2(listList.get(1), "Frukt & Bär");

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickGRÖNSAKER() {
        populateCategoryScreen2(listList.get(2), "Grönsaker");              //hitils bara ändrat de tre första

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickKÖTTFISK() {
        populateCategoryScreen2(listList.get(3), "Kött & Fisk");

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickMEJERI() {
        populateCategoryScreen2(listList.get(4), "Mejeri");

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickPOTATISRIS() {
        populateCategoryScreen2(listList.get(5), "Potatis & Ris");

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickSKAFFERI() {
        populateCategoryScreen2(listList.get(6), "Skafferi");

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickSÖTSAKERSNACKS() {
        populateCategoryScreen2(listList.get(7), "Sötsaker & Snacks");

        updateShoppingCartButton();

        //Visa hemknappen
        showHomeButton();
    }

    public void populateSearchScreen(List<Product> products, String title) {
        productPane.toFront();
        productFlowPane.setHgap(10);
        productFlowPane.setVgap(10);

        productScrollPane.setVvalue(0);

        categoryTitle.setText(title);
        //this.products = products;
        productFlowPane.getChildren().clear();

        for (Product p : products) {
            productFlowPane.getChildren().add(new ProductCard(p, ProductCard.cardType.category, this));
        }
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

        //TA BORT ALLA TOMMA ORDERS SOM GÖR ALLT OCOOLT
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
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Reset shoppingCart
        idh.getShoppingCart().clear();

        middlePane.getChildren().add(accountScreen);
        accountScreen.toBack();
        middlePane.getChildren().add(checkoutScreen);
        checkoutScreen.toBack();
        middlePane.getChildren().add(historyScreen);
        historyScreen.toBack();

        //Fill tidigare köpta varor
        updatePreviouslyBought();

        fillListList();
        sortListList();
    }

    public IMatDataHandler getDataHandler() {
        return idh;
    }
}
