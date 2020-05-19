package iMat.Categories;

import iMat.MainController;
import iMat.ProductCard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CategoriesController {

    IMatDataHandler idh = IMatDataHandler.getInstance();

    public java.util.List<Product> dryckList = Stream.concat(idh.getProducts(ProductCategory.COLD_DRINKS).stream(), idh.getProducts(ProductCategory.HOT_DRINKS).stream()).collect(Collectors.toList());

    public java.util.List<Product> fruktBärList = Stream.of(idh.getProducts(ProductCategory.MELONS), idh.getProducts(ProductCategory.EXOTIC_FRUIT),
            idh.getProducts(ProductCategory.CITRUS_FRUIT), idh.getProducts(ProductCategory.BERRY)).flatMap(Collection::stream).collect(Collectors.toList());

    public java.util.List<Product> grönsakerList = Stream.of(idh.getProducts(ProductCategory.VEGETABLE_FRUIT), idh.getProducts(ProductCategory.ROOT_VEGETABLE),
            idh.getProducts(ProductCategory.POD), idh.getProducts(ProductCategory.CABBAGE)).flatMap(Collection::stream).collect(Collectors.toList());

    public java.util.List<Product> köttFiskList = Stream.concat(idh.getProducts(ProductCategory.MEAT).stream(), idh.getProducts(ProductCategory.FISH).stream()).collect(Collectors.toList());

    public java.util.List<Product> mejeriList = idh.getProducts(ProductCategory.DAIRIES);

    public java.util.List<Product> potatisRisList = idh.getProducts(ProductCategory.POTATO_RICE);

    public java.util.List<Product> skafferiList = Stream.of(idh.getProducts(ProductCategory.FLOUR_SUGAR_SALT), idh.getProducts(ProductCategory.HERB),
            idh.getProducts(ProductCategory.PASTA), idh.getProducts(ProductCategory.BREAD)).flatMap(Collection::stream).collect(Collectors.toList());

    public java.util.List<Product> sötsakerSnacksList = Stream.concat(idh.getProducts(ProductCategory.SWEET).stream(), idh.getProducts(ProductCategory.NUTS_AND_SEEDS).stream()).collect(Collectors.toList());

    @FXML
    FlowPane productPane;
    @FXML
    FlowPane homepagePane;
    @FXML
    Label categoryTitle;
    @FXML
    AnchorPane categoryProductPane;
    private List<ProductCategory> subCategories;
    private List<Product> products;

    //CategoryScreen cs = new CategoryScreen(Arrays.asList(ProductCategory.values()), new MainController());

}
