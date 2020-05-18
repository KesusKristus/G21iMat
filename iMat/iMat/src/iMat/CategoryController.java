package iMat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {


    private Map<String, CategoryCardItem> categoryCardItemMap = new HashMap<String, CategoryCardItem>();

    @FXML
    FlowPane categoriesFlowPane;
    @FXML
    AnchorPane homepagePane;
    @FXML
    AnchorPane categorypagePane;
    @FXML
    Label categoryLabel;


    public void openCategory(ProductCategory category){
        categorypagePane.toFront();
        categoryLabel.setText(category.name());
    }



    @Override
    public void initialize(URL url, ResourceBundle rb){
        for (ProductCategory category : ProductCategory.values()){
            CategoryCardItem categoryCard = new CategoryCardItem(category, this);
            categoryCardItemMap.put(category.toString(), categoryCard);
        }

        updateCategories();
    }

    private void updateCategories(){

        categoriesFlowPane.getChildren().clear();

        ProductCategory[] categoryList = ProductCategory.values();

        for (ProductCategory pC : categoryList){
            categoriesFlowPane.getChildren().add(categoryCardItemMap.get(pC.toString()));
        }

    }





}
