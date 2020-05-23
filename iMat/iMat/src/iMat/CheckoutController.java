package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import se.chalmers.cse.dat216.project.*;

import java.io.IOException;

import java.util.Date;
import java.text.DecimalFormat;

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
    TextField postortText;
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

    //\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    //ERRORMEDDELANDEN
    @FXML
    Label checkoutErrorLabel;

    //TODO SKRIV UT OLIKA ERRORMEDDELANDEN OCH STOPPA ANVÄNDAREN FRÅN ATT STEGA VIDARE IFALL INFORMATIONEN INTE RÄCKER/ÄR OKEJ


    IMatDataHandler idh = IMatDataHandler.getInstance();

    CreditCard card = idh.getCreditCard();

    Customer customer = idh.getCustomer();

    MainController mainController;

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    enum payMethod {
        CARD,
        SWISH,
        INVOICE
    }

    payMethod paymethod = payMethod.CARD;

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

    private int currentStep = 1;

    private int currentOrder = idh.getOrders().size();

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

    public void nästaStegTillFrakt() {

        //Felkontroller
        boolean någotÄrFel = false;
        switch (paymethod) {
            case CARD:

                if (kontonummerText.getText().length() != 16) {
                    någotÄrFel = true;

                    checkoutErrorLabel.setText("FEL KORTNUMMER");

                    break;

                }

                if (((giltighetMMText.getText().length() != 2) && (giltighetMMText.getText().length() != 1)) || (Integer.parseInt(giltighetMMText.getText()) > 12 || Integer.parseInt(giltighetMMText.getText()) < 0)) {

                    någotÄrFel = true;

                    checkoutErrorLabel.setText("FEL GILTIGHETSTID MÅNAD");

                    break;
                }

                if ((giltighetÅÅText.getText().length() != 2) || (Integer.parseInt(giltighetÅÅText.getText()) > 99 || Integer.parseInt(giltighetÅÅText.getText()) < 0)) {

                    någotÄrFel = true;

                    checkoutErrorLabel.setText("FEL GILTIGHETSTID ÅR");

                    break;
                }

                if (cvcText.getText().length() != 3) {
                    någotÄrFel = true;

                    checkoutErrorLabel.setText("FEL CVC-KOD");

                    break;
                }

                break;

            case SWISH:

                if ((mobilnummerText.getText().length() != 10) || (mobilnummerText.getCharacters().charAt(0) != '0' && mobilnummerText.getCharacters().charAt(1) != '7')) {
                    någotÄrFel = true;

                    checkoutErrorLabel.setText("OGILTIGT MOBILNUMMER");

                    break;
                }
                break;

            case INVOICE:

                //Ingen kontroll behövs här

                break;
        }

        if (!någotÄrFel) {
            checkoutErrorLabel.setText("");

            if (currentStep == 1)
                currentStep = 2;
            updateCheckout();
        }
    }

    public void förraStegTillBetalning() {
        if (currentStep == 2)
            currentStep = 1;

        updateCheckout();
    }

    // CONFIRM
    public void nästaStegTillKvitto() {

        //Felkontroller
        boolean någotÄrFel = false;
        if (postnummerText.getText().length() != 5){
            någotÄrFel = true;

            checkoutErrorLabel.setText("OGILTIGT POSTNUMMER");
        } else if (leveransTidCombo.getSelectionModel().isEmpty()){
            någotÄrFel = true;

            checkoutErrorLabel.setText("VÄLJ LEVERANSTID");
        }
        if ((leveransMånadText.getText().length() != 2) || (Integer.parseInt(leveransMånadText.getText()) > 12) || (Integer.parseInt(leveransMånadText.getText()) < 0)){
            någotÄrFel = true;

            checkoutErrorLabel.setText("OGILTIGT LEVERANSDATUM: MÅNAD");
        } else {

            //Kolla så att dagen stämmer med månaden
            switch(Integer.parseInt(leveransMånadText.getText())){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if ((Integer.parseInt(leveransDagText.getText()) > 31) || (Integer.parseInt(leveransDagText.getText()) < 0)){
                        någotÄrFel = true;
                        checkoutErrorLabel.setText("OGILTIG LEVERANSDATUM: DAG");
                    }
                    break;
                case 2:
                    if ((Integer.parseInt(leveransDagText.getText()) > 29) || (Integer.parseInt(leveransDagText.getText()) < 0)){
                        någotÄrFel = true;
                        checkoutErrorLabel.setText("OGILTIG LEVERANSDATUM: DAG");
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    if ((Integer.parseInt(leveransDagText.getText()) > 30) || (Integer.parseInt(leveransDagText.getText()) < 0)){
                        någotÄrFel = true;
                        checkoutErrorLabel.setText("OGILTIG LEVERANSDATUM: DAG");
                    }
                    break;
            }
        }


        if (!någotÄrFel) {
            checkoutErrorLabel.setText("");

            loadKvitto();

            //Change to kvitto pane
            if (currentStep == 2)
                currentStep = 3;
            updateCheckout();
        }
    }

    void loadKvitto(){
        //Add order to list of orders
        Order newOrder = new Order();
        newOrder.setDate(new Date());
        newOrder.setItems(idh.getShoppingCart().getItems());
        currentOrder++;
        newOrder.setOrderNumber(currentOrder);
        idh.getOrders().add(newOrder);

        //Set kvitto total price and amount
        kvittoAntalVarorLabel.setText("" + idh.getShoppingCart().getItems().size() + " st");
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
        mainController.clearShoppingCart();
        mainController.updateShoppingCart();
        mainController.updateShoppingCartButton();
    }


    public void updateCheckoutInfo() {
        kontonummerText.setText(card.getCardNumber());
        giltighetMMText.setText(Integer.toString(card.getValidMonth()));
        giltighetÅÅText.setText(Integer.toString(card.getValidYear()));
        cvcText.setText(Integer.toString(card.getVerificationCode()));

        mobilnummerText.setText(customer.getMobilePhoneNumber());

        leveransAdressText.setText(customer.getAddress());
        postortText.setText(customer.getPostAddress());
        postnummerText.setText(customer.getPostCode());
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
