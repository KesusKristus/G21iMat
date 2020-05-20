package iMat;

import iMat.Categories.CategoriesController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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


    private MainController parentController;
    private java.util.List<ProductCategory> subCategories;
    private java.util.List<ShoppingItem> shoppingCartList = new ArrayList<ShoppingItem>();

    IMatDataHandler idh = IMatDataHandler.getInstance();

    //CategoryScreen cs = new CategoryScreen(Arrays.asList(ProductCategory.values()), new MainController());

    @FXML Pane dryckPane;
    @FXML Pane konto_pane;


    @FXML
    void onClickKONTO() {
        productPane.toBack();
        accountScreen.toFront();

    }

    @FXML
    void onClickDRYCK(){
        /*POPULATECATEGORYSCREEN(dryckList, "Dryck");*/

        populateCategoryScreen(cController.dryckList, "Dryck");
    }

    @FXML
    void onClickFRUKTBÄR(){
        /*POPULATECATEGORYSCREEN(fruktBärList, "Frukt & bär");*/

        populateCategoryScreen(cController.fruktBärList, "Frukt & bär");
    }

    @FXML
    void onClickGRÖNSAKER(){
        /*POPULATECATEGORYSCREEN(grönsakerList, "Grönsaker");*/

        populateCategoryScreen(cController.grönsakerList, "Grönsaker");
    }

    @FXML
    void onClickKÖTTFISK(){
        /*POPULATECATEGORYSCREEN(dryckList, "Dryck");*/

        populateCategoryScreen(cController.köttFiskList, "Kött & fisk");
    }

    @FXML
    void onClickMEJERI(){
        /*POPULATECATEGORYSCREEN(mejeriList, "Mejeri");*/

        populateCategoryScreen(cController.mejeriList, "Mejeri");
    }

    @FXML
    void onClickPOTATISRIS(){
        /*POPULATECATEGORYSCREEN(potatisRisList, "Potatis & ris");*/

        populateCategoryScreen(cController.potatisRisList, "Potatis & ris");
    }

    @FXML
    void onClickSKAFFERI(){
        /*POPULATECATEGORYSCREEN(skafferList, "Skafferi");*/

        populateCategoryScreen(cController.skafferiList, "Skafferi");
    }

    @FXML
    void onClickSÖTSAKERSNACKS(){
        /*POPULATECATEGORYSCREEN(sötsakerSnacksList, "Sötsaker & snacks");*/

        populateCategoryScreen(cController.sötsakerSnacksList, "Sötsaker & snacks");
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

    public void productAdded(ShoppingItem item){
        boolean exists = false;
        if(shoppingCartList == null){
            shoppingCartList.add(item);
        } else {
            for (ShoppingItem i : shoppingCartList) {
                if (item.getProduct().equals(i.getProduct())) {
                    exists = true;
                }
            }
            if (!exists) {
                shoppingCartList.add(item);
            }
        }
    }

    public void updateShoppingCart(){
        shoppingCartFlowPane.getChildren().clear();
        productFlowPane.setHgap(10);
        for( ShoppingItem item : shoppingCartList){
            shoppingCartFlowPane.getChildren().add(new ProductCard(item, this));
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
