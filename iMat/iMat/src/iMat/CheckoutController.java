package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.text.DecimalFormat;
import java.util.List;

public class CheckoutController extends AnchorPane {

    //Kassans fxml
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

    /*@FXML
    Pane nästaSteg1Pane; //betalningsuppgifter*/
    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    //FRAKTUPPGIFTER ///////////////////////////////////
    @FXML
    TextField leveransAdressText;
    @FXML
    TextField postortText;
    @FXML
    TextField postnummerText;
    @FXML
    TextField leveransDagText;
    @FXML
    TextField leveransMånadText;
    @FXML
    ComboBox leveransTidCombo;

    /*@FXML
    Pane föregåendeSteg2Pane;

    @FXML
    Pane bekräftaKöpPane;*/

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
    @FXML
    Label orderInfoLabel;

    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    //ERRORMEDDELANDEN
    @FXML
    Label checkoutErrorLabel;


    private final IMatDataHandler idh = IMatDataHandler.getInstance();

    private final CreditCard card = idh.getCreditCard();

    private final Customer customer = idh.getCustomer();

    private final MainController mainController;

    private static final DecimalFormat df2 = new DecimalFormat("#.##");

    //ENUM för hantering av vilken betalningsmetod som är aktiv
    private enum payMethod {
        CARD,
        SWISH,
        INVOICE
    }

    private payMethod paymethod = payMethod.CARD;

    //Variabel för att spara adressen (bara för kassan, egentligen onödig)
    private String adress = "";

    //Vilket steg användaren är i i kassan
    private int currentStep = 1;

    //Nummer för pågående beställningen för att kunna sätta "order number"
    private int currentOrder = idh.getOrders().size();

    public CheckoutController(MainController mainController) {

        this.mainController = mainController;

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

        updateCheckoutInfo();


    }


    private void updateCheckout() {

        updateCheckoutInfo();

        switch (currentStep) {
            case (1):

                betalning1kassaPane.setStyle("-fx-background-color: #B7E9E9");
                frakt2kassaPane.setStyle("-fx-background-color: #EBEBEB");
                kvitto3kassaPane.setStyle("-fx-background-color: #EBEBEB");

                betalningsUppgifterPane.toFront();


                break;
            case (2):

                betalning1kassaPane.setStyle("-fx-background-color: #EBEBEB");
                frakt2kassaPane.setStyle("-fx-background-color: #B7E9E9");
                kvitto3kassaPane.setStyle("-fx-background-color: #EBEBEB");

                fraktUppgifterPane.toFront();


                break;
            case (3):

                betalning1kassaPane.setStyle("-fx-background-color: #EBEBEB");
                frakt2kassaPane.setStyle("-fx-background-color: #EBEBEB");
                kvitto3kassaPane.setStyle("-fx-background-color: #B7E9E9");

                kvittoUppgifterPane.toFront();

                break;
            default:
                break;
        }

    }

    public void setupCheckout() {

        currentStep = 1;

        bankkortInfoPane.toFront();
        bankkortPane.setStyle("-fx-background-color: #99FBB4");
        swishPane.setStyle("-fx-background-color: #EBEBEB");
        fakturaPane.setStyle("-fx-background-color: #EBEBEB");

        updateCheckout();
    }

    private void nästaStegTillFrakt() {

        //Felkontroller
        boolean somethingIsWrong = false;
        switch (paymethod) {
            case CARD:

                if (kontonummerText.getText().length() != 16) {
                    somethingIsWrong = true;

                    checkoutErrorLabel.setText("OGILTIGT KORTNUMMER");

                    break;

                }

                if (((giltighetMMText.getText().length() != 2) && (giltighetMMText.getText().length() != 1)) || (Integer.parseInt(giltighetMMText.getText()) > 12 || Integer.parseInt(giltighetMMText.getText()) < 0)) {

                    somethingIsWrong = true;

                    checkoutErrorLabel.setText("OGILTIG GILTIGHETSTID: MÅNAD");

                    break;
                }

                if ((giltighetÅÅText.getText().length() != 2) || (Integer.parseInt(giltighetÅÅText.getText()) > 99 || Integer.parseInt(giltighetÅÅText.getText()) < 0)) {

                    somethingIsWrong = true;

                    checkoutErrorLabel.setText("OGILTIG GILTIGHETSTID: ÅR");

                    break;
                }

                if (cvcText.getText().length() != 3) {
                    somethingIsWrong = true;

                    checkoutErrorLabel.setText("OGILTIG CVC-KOD");

                    break;
                }

                break;

            case SWISH:

                if ((mobilnummerText.getText().length() != 10) || (mobilnummerText.getCharacters().charAt(0) != '0' && mobilnummerText.getCharacters().charAt(1) != '7')) {
                    somethingIsWrong = true;

                    checkoutErrorLabel.setText("OGILTIGT MOBILNUMMER");

                    break;
                }
                break;

            case INVOICE:

                //Ingen kontroll behövs här

                break;
        }

        if (!somethingIsWrong) {
            checkoutErrorLabel.setText("");

            if (currentStep == 1)
                currentStep = 2;
            updateCheckout();
        }
    }

    private void förraStegTillBetalning() {
        if (currentStep == 2)
            currentStep = 1;

        checkoutErrorLabel.setText("");

        updateCheckout();
    }

    // CONFIRM
    private void nästaStegTillKvitto() {

        adress = leveransAdressText.getText();

        //Felkontroller
        boolean somethingIsWrong = felkontrollTillKvittoSteg();

        if (!somethingIsWrong) {
            checkoutErrorLabel.setText("");

            loadKvitto();

            mainController.greyoutCheckoutButton();

            //Change to kvitto pane
            if (currentStep == 2)
                currentStep = 3;
            updateCheckout();
        }
    }

    private boolean felkontrollTillKvittoSteg(){

        if (leveransAdressText.getText().length() == 0){

            checkoutErrorLabel.setText("ANGE ADRESS");
            return true;

        }
        if (postortText.getText().length() == 0){

            checkoutErrorLabel.setText("ANGE POSTORT");
            return true;

        }
        if (postnummerText.getText().length() == 0){

            checkoutErrorLabel.setText("ANGE POSTNUMMER");
            return true;

        }
        if (postnummerText.getText().length() != 5) {

            checkoutErrorLabel.setText("OGILTIGT POSTNUMMER");
            return true;

        }

        if (leveransDagText.getText().length() == 0){

            checkoutErrorLabel.setText("VÄLJ LEVERANSDAG");
            return true;
        }

        if (leveransMånadText.getText().length() == 0){

            checkoutErrorLabel.setText("VÄLJ LEVERANSMÅNAD");
            return true;
        }

        if ((Integer.parseInt(leveransMånadText.getText()) > 12) || (Integer.parseInt(leveransMånadText.getText()) < 0)) {
            checkoutErrorLabel.setText("OGILTIGT LEVERANSDATUM: MÅNAD");

            return true;
        } else {
            //Kolla så att dagen stämmer med månaden
            switch (Integer.parseInt(leveransMånadText.getText())) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if ((Integer.parseInt(leveransDagText.getText()) > 31) || (Integer.parseInt(leveransDagText.getText()) < 0)) {
                        checkoutErrorLabel.setText("OGILTIG LEVERANSDATUM: DAG");
                        return true;
                    }
                    break;
                case 2:
                    if ((Integer.parseInt(leveransDagText.getText()) > 29) || (Integer.parseInt(leveransDagText.getText()) < 0)) {

                        checkoutErrorLabel.setText("OGILTIG LEVERANSDATUM: DAG");
                        return true;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if ((Integer.parseInt(leveransDagText.getText()) > 30) || (Integer.parseInt(leveransDagText.getText()) < 0)) {

                        checkoutErrorLabel.setText("OGILTIG LEVERANSDATUM: DAG");
                        return true;
                    }
                    break;
            }
        }

        if (leveransTidCombo.getSelectionModel().isEmpty()) {

            checkoutErrorLabel.setText("VÄLJ LEVERANSTID");
            return true;
        }

        return false;
    }

    private String getMonthString() {

        switch (Integer.parseInt(leveransMånadText.getText())) {
            case 1:
                return "januari";
            case 2:
                return "februari";
            case 3:
                return "mars";
            case 4:
                return "april";
            case 5:
                return "maj";
            case 6:
                return "juni";
            case 7:
                return "juli";
            case 8:
                return "augusti";
            case 9:
                return "september";
            case 10:
                return "oktober";
            case 11:
                return "november";
            case 12:
                return "december";
            default:
                return "ERROR";
        }


    }

    private void loadKvitto() {

        //Set order-text on receipt page
        orderInfoLabel.setText("Din order förväntas anlända mellan \n" + leveransTidCombo.getValue().toString() + " den " +
                leveransDagText.getText() + " " + getMonthString() + "\nhos " + adress + ".");

        //Add order to list of orders
        Order newOrder = new Order();
        newOrder.setDate(new Date());


        List<ShoppingItem> shoppingItems = new ArrayList<ShoppingItem>();

        for (ShoppingItem si : idh.getShoppingCart().getItems()) {
            shoppingItems.add(si);
        }

        newOrder.setItems(shoppingItems);

        currentOrder++;
        newOrder.setOrderNumber(currentOrder);
        idh.getOrders().add(newOrder);

        //Set kvitto total price and amount
        int antalvaror = 0;

        for (int i = 0; i < idh.getShoppingCart().getItems().size(); i++) {
            antalvaror += idh.getShoppingCart().getItems().get(i).getAmount();
        }

        kvittoAntalVarorLabel.setText("" + antalvaror + " st");
        kvittoPrisLabel.setText("" + df2.format(idh.getShoppingCart().getTotal()) + " kr");

        //Add bought items to kvitto flowpane list
        kvittoFlowPane.getChildren().clear();
        int index = 1;
        ReceiptCard receiptCard;
        AnchorPane receiptPane;
        for (ShoppingItem sI : newOrder.getItems()) {

            receiptCard = new ReceiptCard(sI);
            receiptPane = receiptCard;
            if (index % 2 == 1) {
                receiptPane.setStyle("-fx-background-color: #FFFFFF");
            } else {
                receiptPane.setStyle("-fx-background-color: #F9F9F9");
            }
            index++;

            kvittoFlowPane.getChildren().add(receiptCard);
        }

        //Clear current shoppingCart
        idh.getShoppingCart().clear();

        mainController.clearFillSortListList();

        mainController.clearShoppingCart();
        mainController.updateShoppingCart();
        mainController.updateShoppingCartButton();
    }

    private void updateCheckoutInfo() {
        kontonummerText.setText(card.getCardNumber());
        giltighetMMText.setText(Integer.toString(card.getValidMonth()));
        giltighetÅÅText.setText(Integer.toString(card.getValidYear()));
        cvcText.setText(Integer.toString(card.getVerificationCode()));

        mobilnummerText.setText(customer.getMobilePhoneNumber());

        leveransAdressText.setText(customer.getAddress());
        postortText.setText(customer.getPostAddress());
        postnummerText.setText(customer.getPostCode());
    }

    private void fortsätthandla() {
        if (currentStep == 3) {
            mainController.onClickHEM();
        }
    }

    @FXML
    void onClickNÄSTASTEGTILLFRAKT() {
        nästaStegTillFrakt();
    }

    @FXML
    void onClickFÖRRASTEGTILLBETALNING() {
        förraStegTillBetalning();
    }

    @FXML
    void onClickNÄSTASTEGTILLKVITTO() {
        nästaStegTillKvitto();
    }

    @FXML
    void onClickFORTSÄTTHANDLA() {
        fortsätthandla();
    }

    @FXML
    void onClickBANKKORT() {

        paymethod = payMethod.CARD;

        bankkortInfoPane.toFront();
        bankkortPane.setStyle("-fx-background-color: #99FBB4");
        swishPane.setStyle("-fx-background-color: #EBEBEB");
        fakturaPane.setStyle("-fx-background-color: #EBEBEB");
    }

    @FXML
    void onClickSWISH() {

        paymethod = payMethod.SWISH;

        swishInfoPane.toFront();
        bankkortPane.setStyle("-fx-background-color: #EBEBEB");
        swishPane.setStyle("-fx-background-color: #99FBB4");
        fakturaPane.setStyle("-fx-background-color: #EBEBEB");
    }

    @FXML
    void onClickFAKTURA() {

        paymethod = payMethod.INVOICE;

        fakturaInfoPane.toFront();
        bankkortPane.setStyle("-fx-background-color: #EBEBEB");
        swishPane.setStyle("-fx-background-color: #EBEBEB");
        fakturaPane.setStyle("-fx-background-color: #99FBB4");
    }


}
