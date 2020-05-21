package iMat;

import iMat.Categories.CategoriesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainController implements Initializable {

    private IMatDataHandler dataHandler;

    @FXML AnchorPane startScreen;
    @FXML ScrollPane shoppingCartPane;
    @FXML AnchorPane accountScreen = new AccountScreen();

    private CategoriesController cController = new CategoriesController();

    @FXML ScrollPane productScrollPane;
    @FXML FlowPane productFlowPane;
    @FXML Pane productPane;
    @FXML Pane middlePane;
    @FXML Label categoryTitle;
    @FXML FlowPane shoppingCartFlowPane;

    @FXML TextField searchField;

    @FXML AnchorPane homePane;
    @FXML Pane iMatPane;

    private MainController parentController;
    private java.util.List<ProductCategory> subCategories;
    private java.util.List<ShoppingItem> shoppingCartList = new ArrayList<ShoppingItem>();
    private java.util.List<ProductCard> cardList = new ArrayList<ProductCard>();
    private java.util.List<java.util.List<ProductCard>> listList = new ArrayList<java.util.List<ProductCard>>();


    IMatDataHandler idh = IMatDataHandler.getInstance();

    @FXML Pane dryckPane;
    @FXML Pane konto_pane;


    //När enter trycks från sökrutan
    @FXML
    void onEnter(ActionEvent ae){

        String searchString = searchField.getText();

        populateCategoryScreen(idh.findProducts(searchString), "Sökresultat för '" + searchString + "'");
    }

    //Återvänd till startsidan
    @FXML
    void onClickHEM() {
        iMatPane.toFront();
        startScreen.toFront();
    }

    //Ska kallas då "startsidan" lämnas
    void showHomeButton() {
        homePane.toFront();
    }



    @FXML
    void onClickKONTO() {
        productPane.toBack();
        accountScreen.toFront();

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickDRYCK(){
        //populateCategoryScreen(cController.dryckList, "Dryck");
        populateCategoryScreen2(listList.get(0), "Dryck");

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickFRUKTBÄR(){
        populateCategoryScreen(cController.fruktBärList, "Frukt & bär");

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickGRÖNSAKER(){
        populateCategoryScreen(cController.grönsakerList, "Grönsaker");

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickKÖTTFISK(){
        populateCategoryScreen(cController.köttFiskList, "Kött & fisk");

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickMEJERI(){
        populateCategoryScreen(cController.mejeriList, "Mejeri");

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickPOTATISRIS(){
        populateCategoryScreen(cController.potatisRisList, "Potatis & ris");

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickSKAFFERI(){
        populateCategoryScreen(cController.skafferiList, "Skafferi");

        //Visa hemknappen
        showHomeButton();
    }

    @FXML
    void onClickSÖTSAKERSNACKS(){
        populateCategoryScreen(cController.sötsakerSnacksList, "Sötsaker & snacks");

        //Visa hemknappen
        showHomeButton();
    }

    public void populateCategoryScreen(List<Product> products, String title){
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
            }

    }

    public void productDeleted(ProductCard card){
        for(ProductCard p : cardList){
            if(card.equals(p)){
                cardList.remove(p);
            }
        }
    }

    public void updateShoppingCart(){
        shoppingCartFlowPane.getChildren().clear();
        shoppingCartFlowPane.setVgap(5);
        for( ProductCard card : cardList){
            shoppingCartFlowPane.getChildren().add(new ProductCard(card, this));
        }
    }

    public void fillListList(){
        for(int i = 0; i < 8; i++){
            listList.add(new ArrayList<ProductCard>());
        }
        for( Product p : cController.dryckList){
            listList.get(0).add(new ProductCard(p, ProductCard.cardType.category, this));
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        middlePane.getChildren().add(accountScreen);
        accountScreen.toBack();
        fillListList();
    }

    public IMatDataHandler getDataHandler() {
        return dataHandler;
    }
}
