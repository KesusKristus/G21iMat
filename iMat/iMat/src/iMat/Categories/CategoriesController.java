package iMat.Categories;

import javafx.fxml.FXML;
import se.chalmers.cse.dat216.project.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoriesController {

<<<<<<< HEAD
<<<<<<< HEAD
    @FXML
    FlowPane productFlowPane;
    @FXML
    Label categoryTitle;
    private MainController parentController;
    private List<ProductCategory> subCategories;
    private List<Product> products;

    IMatDataHandler idh = IMatDataHandler.getInstance();

    public List<Product> dryckList = Stream.concat(idh.getProducts(ProductCategory.COLD_DRINKS).stream(), idh.getProducts(ProductCategory.HOT_DRINKS).stream()).collect(Collectors.toList());

    public List<Product> fruktBärList = Stream.of(idh.getProducts(ProductCategory.MELONS), idh.getProducts(ProductCategory.EXOTIC_FRUIT),
=======
    IMatDataHandler idh = IMatDataHandler.getInstance();

=======
    IMatDataHandler idh = IMatDataHandler.getInstance();

>>>>>>> parent of f0628c7... tog bort categoryscreen och fixade så att kategoriknapparna går att klicka på
    List<Product> dryckList = Stream.concat(idh.getProducts(ProductCategory.COLD_DRINKS).stream(), idh.getProducts(ProductCategory.HOT_DRINKS).stream()).collect(Collectors.toList());

    List<Product> fruktBärList = Stream.of(idh.getProducts(ProductCategory.MELONS), idh.getProducts(ProductCategory.EXOTIC_FRUIT),
>>>>>>> parent of f0628c7... tog bort categoryscreen och fixade så att kategoriknapparna går att klicka på
            idh.getProducts(ProductCategory.CITRUS_FRUIT), idh.getProducts(ProductCategory.BERRY)).flatMap(Collection::stream).collect(Collectors.toList());

    public List<Product> grönsakerList = Stream.of(idh.getProducts(ProductCategory.VEGETABLE_FRUIT), idh.getProducts(ProductCategory.ROOT_VEGETABLE),
            idh.getProducts(ProductCategory.POD), idh.getProducts(ProductCategory.CABBAGE)).flatMap(Collection::stream).collect(Collectors.toList());

    public List<Product> köttFiskList = Stream.concat(idh.getProducts(ProductCategory.MEAT).stream(), idh.getProducts(ProductCategory.FISH).stream()).collect(Collectors.toList());

    public List<Product> mejeriList = idh.getProducts(ProductCategory.DAIRIES);

    public List<Product> potatisRisList = idh.getProducts(ProductCategory.POTATO_RICE);

    public List<Product> skafferiList = Stream.of(idh.getProducts(ProductCategory.FLOUR_SUGAR_SALT), idh.getProducts(ProductCategory.HERB),
            idh.getProducts(ProductCategory.PASTA), idh.getProducts(ProductCategory.BREAD)).flatMap(Collection::stream).collect(Collectors.toList());

<<<<<<< HEAD
    public List<Product> sötsakerSnacksList = Stream.concat(idh.getProducts(ProductCategory.SWEET).stream(), idh.getProducts(ProductCategory.NUTS_AND_SEEDS).stream()).collect(Collectors.toList());


    public void populateCategoryScreen(List<Product> products, String title) {
        categoryTitle.setText(title);
        productFlowPane.getChildren().clear();
        for (Product p : products) {
            productFlowPane.getChildren().add(new ProductCard(p, ProductCard.cardType.category));
        }
    }
=======
    List<Product> sötsakerSnacksList = Stream.concat(idh.getProducts(ProductCategory.SWEET).stream(), idh.getProducts(ProductCategory.NUTS_AND_SEEDS).stream()).collect(Collectors.toList());


    @FXML
    void onClickDRYCK(){
        /*POPULATECATEGORYSCREEN(dryckList, "Dryck");*/
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

<<<<<<< HEAD
>>>>>>> parent of f0628c7... tog bort categoryscreen och fixade så att kategoriknapparna går att klicka på
=======
>>>>>>> parent of f0628c7... tog bort categoryscreen och fixade så att kategoriknapparna går att klicka på
}
