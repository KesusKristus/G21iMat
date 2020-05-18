package iMat;

import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CategoryScreen extends AnchorPane {

    private MainController parentController;
    private List<ProductCategory> subCategories;


    public CategoryScreen(List<ProductCategory> subCategories, MainController parentController){ //kanske ändra från mainController. Samma klass för productCard och cartCard
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("category_screen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }

        this.subCategories = subCategories;
        this.parentController = parentController;

    }
}
