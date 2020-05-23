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


    IMatDataHandler idh = IMatDataHandler.getInstance();

    CreditCard card = idh.getCreditCard();

    Customer customer = idh.getCustomer();

    MainController mainController;

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

        switch (currentStep){
            case(1):

                betalning1kassaPane.setStyle("-fx-background-color: #B7E9E9");
                frakt2kassaPane.setStyle("-fx-background-color: #EBEBEB");
                kvitto3kassaPane.setStyle("-fx-background-color: #EBEBEB");

                betalningsUppgifterPane.toFront();




                break;
            case(2):

                betalning1kassaPane.setStyle("-fx-background-color: #EBEBEB");
                frakt2kassaPane.setStyle("-fx-background-color: #B7E9E9");
                kvitto3kassaPane.setStyle("-fx-background-color: #EBEBEB");

                fraktUppgifterPane.toFront();




                break;
            case(3):

                betalning1kassaPane.setStyle("-fx-background-color: #EBEBEB");
                frakt2kassaPane.setStyle("-fx-background-color: #EBEBEB");
                kvitto3kassaPane.setStyle("-fx-background-color: #B7E9E9");

                kvittoUppgifterPane.toFront();

                break;
            default: break;
        }

    }

    public void setupCheckout(){

        currentStep = 1;

        bankkortInfoPane.toFront();
        bankkortPane.setStyle("-fx-background-color: #99FBB4");
        swishPane.setStyle("-fx-background-color: #EBEBEB");
        fakturaPane.setStyle("-fx-background-color: #EBEBEB");

        updateCheckout();
    }

    public void nästaStegTillFrakt(){
        if (currentStep == 1)
            currentStep = 2;

        updateCheckout();
    }

    public void förraStegTillBetalning(){
        if (currentStep == 2)
            currentStep = 1;

        updateCheckout();
    }

    // CONFIRM
    public void nästaStegTillKvitto(){

        //Add order to list of orders
        Order newOrder = new Order();
        newOrder.setDate(new Date());
        newOrder.setItems(idh.getShoppingCart().getItems());
        currentOrder++;
        newOrder.setOrderNumber(currentOrder);
        idh.getOrders().add(newOrder);

        //Set kvitto total price and amount
        kvittoAntalVarorLabel.setText("" + idh.getShoppingCart().getItems().size());

        //TODO BEGRÄNSA TILL TVÅ DECIMALER
        kvittoPrisLabel.setText("" + idh.getShoppingCart().getTotal());

        //Add bought items to kvitto flowpane list
        kvittoFlowPane.getChildren().clear();
        int index = 1;
        ReceiptCard receiptCard;
        AnchorPane receiptPane;
        for (ShoppingItem sI : newOrder.getItems()){

            receiptCard = new ReceiptCard(sI);
            receiptPane = receiptCard;
            if (index % 2 == 1){
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



        //Change to kvitto pane
        if(currentStep == 2)
            currentStep = 3;

        updateCheckout();
    }

    public void updateCheckoutInfo(){
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
    void onClickFÖRRASTEGTILLBETALNING(){
        förraStegTillBetalning();
    }

    @FXML
    void onClickNÄSTASTEGTILLKVITTO(){
        nästaStegTillKvitto();
    }

    @FXML
    void onClickBANKKORT(){
        bankkortInfoPane.toFront();
        bankkortPane.setStyle("-fx-background-color: #99FBB4");
        swishPane.setStyle("-fx-background-color: #EBEBEB");
        fakturaPane.setStyle("-fx-background-color: #EBEBEB");
    }

    @FXML
    void onClickSWISH(){
        swishInfoPane.toFront();
        bankkortPane.setStyle("-fx-background-color: #EBEBEB");
        swishPane.setStyle("-fx-background-color: #99FBB4");
        fakturaPane.setStyle("-fx-background-color: #EBEBEB");
    }

    @FXML
    void onClickFAKTURA(){
        fakturaInfoPane.toFront();
        bankkortPane.setStyle("-fx-background-color: #EBEBEB");
        swishPane.setStyle("-fx-background-color: #EBEBEB");
        fakturaPane.setStyle("-fx-background-color: #99FBB4");
    }



}
