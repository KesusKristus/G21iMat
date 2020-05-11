package iMat;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductCard extends AnchorPane {
    private Product product;
    private MainController parentController;

    @FXML ImageView cardImage;
    @FXML Label cardName;
    @FXML Label cardPrice;






    public ProductCard(Product product, MainController parentController){ //kanske ändra från mainController. Samma klass för productCard och cartCard
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.parentController = parentController;

    }











}
