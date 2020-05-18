package iMat;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import se.chalmers.cse.dat216.project.ProductCategory;

public class CategoryCardItem extends AnchorPane {

    private CategoryController parentController;
    ProductCategory category;

    @FXML
    Label categoryLabel;

    public CategoryCardItem(ProductCategory category, CategoryController cController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.category = category;
        this.parentController = cController;

        categoryLabel.setText(category.name());
    }


    @FXML
    protected void onClick(Event event) {
        parentController.openCategory(category);
        this.setStyle("-fx-background-color: #B7E9E9");
    }


}
