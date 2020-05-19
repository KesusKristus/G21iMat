package iMat.Categories;

import iMat.MainController;
import iMat.ProductCard;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.*;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoriesController {

    @FXML
    FlowPane productPane;
    @FXML
    Label categoryTitle;
    private MainController parentController;
    private List<ProductCategory> subCategories;
    private List<Product> products;

    IMatDataHandler idh = IMatDataHandler.getInstance();

    //CategoryScreen cs = new CategoryScreen(Arrays.asList(ProductCategory.values()), new MainController());

    @FXML
    Pane dryckPane;

    List<Product> dryckList = Stream.concat(idh.getProducts(ProductCategory.COLD_DRINKS).stream(), idh.getProducts(ProductCategory.HOT_DRINKS).stream()).collect(Collectors.toList());

    List<Product> fruktBärList = Stream.of(idh.getProducts(ProductCategory.MELONS), idh.getProducts(ProductCategory.EXOTIC_FRUIT),
            idh.getProducts(ProductCategory.CITRUS_FRUIT), idh.getProducts(ProductCategory.BERRY)).flatMap(Collection::stream).collect(Collectors.toList());

    List<Product> grönsakerList = Stream.of(idh.getProducts(ProductCategory.VEGETABLE_FRUIT), idh.getProducts(ProductCategory.ROOT_VEGETABLE),
            idh.getProducts(ProductCategory.POD), idh.getProducts(ProductCategory.CABBAGE)).flatMap(Collection::stream).collect(Collectors.toList());

    List<Product> köttFiskList = Stream.concat(idh.getProducts(ProductCategory.MEAT).stream(), idh.getProducts(ProductCategory.FISH).stream()).collect(Collectors.toList());

    List<Product> mejeriList = idh.getProducts(ProductCategory.DAIRIES);

    List<Product> potatisRisList = idh.getProducts(ProductCategory.POTATO_RICE);

    List<Product> skafferiList = Stream.of(idh.getProducts(ProductCategory.FLOUR_SUGAR_SALT), idh.getProducts(ProductCategory.HERB),
            idh.getProducts(ProductCategory.PASTA), idh.getProducts(ProductCategory.BREAD)).flatMap(Collection::stream).collect(Collectors.toList());

    List<Product> sötsakerSnacksList = Stream.concat(idh.getProducts(ProductCategory.SWEET).stream(), idh.getProducts(ProductCategory.NUTS_AND_SEEDS).stream()).collect(Collectors.toList());


    @FXML
    void onClickDRYCK(){
        /*POPULATECATEGORYSCREEN(dryckList, "Dryck");*/

        System.out.println("CLICK");

        populateCategoryScreen(dryckList, "Dryck");
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
        categoryTitle.setText(title);
        this.products = products;
        productPane.getChildren().clear();
        for(Product p : products){
            productPane.getChildren().add(new ProductCard(p, parentController, ProductCard.cardType.category));
        }
    }
}
