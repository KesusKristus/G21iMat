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

    private CategoriesController cController = new CategoriesController();

    @FXML
    FlowPane productPane;
    @FXML
    AnchorPane categoryProductPane;
    @FXML
    Label categoryTitle;
    private MainController parentController;
    private java.util.List<ProductCategory> subCategories;
    private java.util.List<Product> products;

    IMatDataHandler idh = IMatDataHandler.getInstance();

    //CategoryScreen cs = new CategoryScreen(Arrays.asList(ProductCategory.values()), new MainController());

    @FXML
    Pane dryckPane;


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

        categoryProductPane.toFront();

        categoryTitle.setText(title);
        //this.products = products;
        productPane.getChildren().clear();
        for(Product p : products){
            productPane.getChildren().add(new ProductCard(p, ProductCard.cardType.category));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public IMatDataHandler getDataHandler() {
        return dataHandler;
    }
}
