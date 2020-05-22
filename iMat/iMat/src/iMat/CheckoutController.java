package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

public class CheckoutController extends AnchorPane {

    //Kassans fxml
    //stegvisare
    @FXML
    Pane betalning1kassaPane;
    @FXML
    Pane frakt2kassaPane;
    @FXML
    Pane kvitto3kassaPane;
    //Panes för varje steg
    @FXML
    Pane betalningsUppgifterPane;
    @FXML
    Pane fraktUppgifterPane;
    @FXML
    Pane kvittoUppgifterPane;

    //BETALNIGSUPPGIFTER //////////////////////////////
    //Knappar för val av betalningsmetod
    @FXML
    Pane bankkortPane;
    @FXML
    Pane swishPane;
    @FXML
    Pane fakturaPane;
    //Fönster för ifyllning av info (betalningsmetod)
    @FXML
    AnchorPane bankkortInfoPane;
    @FXML
    TextField kontonummerText;
    @FXML
    TextField giltighetMMText;
    @FXML
    TextField giltighetÅÅText;
    @FXML
    TextField cvcText;

    @FXML
    AnchorPane swishInfoPane;
    @FXML
    TextField mobilnummerText;

    @FXML
    AnchorPane fakturaInfoPane;

    @FXML
    Pane nästaSteg1Pane; //betalningsuppgifter
    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    //FRAKTUPPGIFTER ///////////////////////////////////
    @FXML
    TextField leveransAdressText;
    @FXML
    TextField postordText;
    @FXML
    TextField postnummerText;
    @FXML
    TextField leveransDagText;
    @FXML
    TextField leveransMånadText;
    @FXML
    ComboBox leveransTidCombo;

    @FXML
    Pane föregåendeSteg2Pane;

    @FXML
    Pane bekräftaKöpPane;

    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    //KVITTOUPPGIFTER //////////////////////////////////

    @FXML
    ScrollPane kvittoScrollPane;
    @FXML
    FlowPane kvittoFlowPane;
    @FXML
    Label kvittoAntalVarorLabel;
    @FXML
    Label kvittoPrisLabel;


    IMatDataHandler idh = IMatDataHandler.getInstance();

    CreditCard card = idh.getCreditCard();

    Customer customer = idh.getCustomer();

    public CheckoutController() {

        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("checkout_screen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        leveransTidCombo.getItems().addAll(
                "8:00-12:00",
                "12:00-16:00",
                "16:00-20:00");


        kontonummerText.setText(card.getCardNumber());
        giltighetMMText.setText(Integer.toString(card.getValidMonth()));
        giltighetÅÅText.setText(Integer.toString(card.getValidYear()));
        cvcText.setText(Integer.toString(card.getVerificationCode()));

        mobilnummerText.setText(customer.getMobilePhoneNumber());

        leveransAdressText.setText(customer.getAddress());
        postordText.setText(customer.getPostAddress());
        postnummerText.setText(customer.getPostCode());

    }

}
