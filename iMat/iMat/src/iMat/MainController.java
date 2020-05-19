package iMat;

import iMat.Categories.CategoriesController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
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

    @FXML FlowPane productFlowPane;
    @FXML Pane productPane;
    @FXML Pane middlePane;
    @FXML Label categoryTitle;

    private MainController parentController;
    private java.util.List<ProductCategory> subCategories;
    private java.util.List<Product> products;

    IMatDataHandler idh = IMatDataHandler.getInstance();

    //CategoryScreen cs = new CategoryScreen(Arrays.asList(ProductCategory.values()), new MainController());

    @FXML Pane dryckPane;
    @FXML Pane konto_pane;


    @FXML
    void onClickKONTO() {
        System.out.println("konto click");
        accountScreen.toFront();
    }

    @FXML
    void onClickDRYCK(){
        /*POPULATECATEGORYSCREEN(dryckList, "Dryck");*/

        System.out.println("CLICK");

        populateCategoryScreen(cController.dryckList, "Dryck");
    }

    @FXML
    void onClickFRUKTBÄR(){
        /*POPULATECATEGORYSCREEN(fruktBärList, "Frukt & bär");*/
    }

    @FXML
    void onClickGRÖNSAKER(){
        /*POPULATECATEGORYSCREEN(grönsakerList, "Grönsaker");*/
    }

    @FXML
    void onClickKÖTTFISK(){
        /*POPULATECATEGORYSCREEN(dryckList, "Dryck");*/
    }

    @FXML
    void onClickMEJERI(){
        /*POPULATECATEGORYSCREEN(mejeriList, "Mejeri");*/
    }

    @FXML
    void onClickPOTATISRIS(){
        /*POPULATECATEGORYSCREEN(potatisRisList, "Potatis & ris");*/
    }

    @FXML
    void onClickSKAFFERI(){
        /*POPULATECATEGORYSCREEN(skafferList, "Skafferi");*/
    }

    @FXML
    void onClickSÖTSAKERSNACKS(){
        /*POPULATECATEGORYSCREEN(sötsakerSnacksList, "Sötsaker & snacks");*/
    }

    public void populateCategoryScreen(List<Product> products, String title){

        productPane.toFront();

        categoryTitle.setText(title);
        //this.products = products;
        productFlowPane.getChildren().clear();

        for(Product p : products){
            productFlowPane.getChildren().add(new ProductCard(p, ProductCard.cardType.category));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        middlePane.getChildren().add(accountScreen);
        accountScreen.toBack();
    }

    public IMatDataHandler getDataHandler() {
        return dataHandler;
    }
}
