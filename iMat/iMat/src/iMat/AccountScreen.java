package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AccountScreen extends AnchorPane {
    private MainController parentController;

    @FXML Label firstname_label;
    @FXML Label surname_label;
    @FXML Label adress1_label;
    @FXML Label adress2_label;
    @FXML TextField firstname_textfield;
    @FXML TextField surname_textfield;
    @FXML TextField adress1_textfield;
    @FXML TextField adress2_textfield;

    @FXML Label konto_label;
    @FXML Label giltighet_label;
    @FXML Label cvc_label;
    @FXML TextField konto_textfield;
    @FXML TextField giltighet1_textfield;
    @FXML TextField giltighet2_textfield;
    @FXML TextField cvc_textfield;

    @FXML Label swish_label;
    @FXML TextField mobilnummer_textfield;

    public AccountScreen() {

        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("account_screen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }
}
