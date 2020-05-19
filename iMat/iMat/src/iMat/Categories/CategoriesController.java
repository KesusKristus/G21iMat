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


    public void populateCategoryScreen(List<Product> products, String title){
        /*categoryTitle.setText(title);
        this.products = products;
        productPane.getChildren().clear();
        for(Product p : products){
            productPane.getChildren().add(new ProductCard(p, parentController, ProductCard.cardType.category));
        }*/
    }
}
