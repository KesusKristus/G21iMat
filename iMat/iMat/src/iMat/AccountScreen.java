package iMat;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.w3c.dom.Text;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountScreen extends AnchorPane {

    @FXML Label firstname_label;
    @FXML Label surname_label;
    @FXML Label adress1_label;
    @FXML Label email_label;
    @FXML TextField firstname_textfield = new TextField();
    @FXML TextField surname_textfield = new TextField();
    @FXML TextField adress1_textfield = new TextField();
    @FXML TextField email_textfield = new TextField();

    @FXML Label konto_label;
    @FXML Label giltighet_label;
    @FXML Label cvc_label;
    @FXML TextField konto_textfield;
    //TODO ändra giltighet och cvc till int inputs istället för textfields
    @FXML TextField giltighet1_textfield;
    @FXML TextField giltighet2_textfield;
    @FXML TextField cvc_textfield;

    @FXML Label swish_label;
    @FXML TextField mobilnummer_textfield;

    private Customer c = IMatDataHandler.getInstance().getCustomer();
    private CreditCard cc = IMatDataHandler.getInstance().getCreditCard();

    public AccountScreen() {
        /* Ladda in account screen i mitten av main sidan */
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("account_screen.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        } catch (IOException exception){
            throw new RuntimeException(exception);
        }

        initTextFields();

    }

    private void initTextFields() {

        getAllCustomerData();

        // SET EVENT HANDLERS
        firstname_textfield.setOnKeyPressed(e -> {
            alphabeticalFieldValidation((TextField) e.getTarget());
        });

        surname_textfield.setOnKeyPressed(e -> {
            alphabeticalFieldValidation((TextField) e.getTarget());
        });

        adress1_textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField target = (TextField) e.getTarget();
                if (validate(target.getText())) c.setFirstName(firstname_textfield.getText());
                else {
                    System.out.println("inte en adress");
                }
            }

            private boolean validate(String text) {

                return true;
            }
        });
        email_textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField target = (TextField) e.getTarget();
                if (validate(target.getText())) c.setFirstName(firstname_textfield.getText());
                else {
                    System.out.println("inte mail adress");
                }
            }

            private boolean validate(String text) {
                return true;
            }
        });

        konto_textfield.setOnKeyPressed(e -> {
            numericFieldValidation((TextField) e.getTarget());
        });
        giltighet1_textfield.setOnKeyPressed(e -> {
            numericFieldValidation((TextField) e.getTarget());
        });
        giltighet2_textfield.setOnKeyPressed(e -> {
            numericFieldValidation((TextField) e.getTarget());
        });
        cvc_textfield.setOnKeyPressed(e -> {
            numericFieldValidation((TextField) e.getTarget());
        });
        mobilnummer_textfield.setOnKeyPressed(e -> {
            numericFieldValidation((TextField) e.getTarget());
        });
    }

    private void alphabeticalFieldValidation(TextField target) {
        if (target.getText().matches("[a-zA-Z]+")) return;
        target.setText(target.getText().replaceAll("[^a-zA-Z]+", ""));
        System.out.println("Kan bara vara bokstäver");

    }
    private void numericFieldValidation(TextField target) {

        if (target.getText().matches("\\d*")) return;
        target.setText(target.getText().replaceAll("[^\\d]", ""));
        System.out.println("Kan bara vara nummer");
    }

    @FXML
    public void onClickSpara() {
        setAllCustomerData();
        IMatDataHandler.getInstance().shutDown();
    }

    private void getAllCustomerData() {
        firstname_textfield.setText(c.getFirstName());
        surname_textfield.setText(c.getLastName());
        adress1_textfield.setText(c.getAddress());
        email_textfield.setText(c.getEmail());
        mobilnummer_textfield.setText(c.getPhoneNumber());
        konto_textfield.setText(cc.getCardNumber());
        giltighet1_textfield.setText(String.valueOf(cc.getValidMonth()));
        giltighet2_textfield.setText(String.valueOf(cc.getValidYear()));
        cvc_textfield.setText(String.valueOf(cc.getVerificationCode()));
    }

    private void setAllCustomerData() {
        c.setAddress(adress1_textfield.getText());
        c.setFirstName(firstname_textfield.getText());
        c.setLastName(surname_textfield.getText());
        c.setEmail(email_textfield.getText());
        c.setMobilePhoneNumber(mobilnummer_textfield.getText());

        cc.setCardNumber(konto_textfield.getText());
        cc.setHoldersName(firstname_textfield.getText() + " " + surname_textfield.getText());
        cc.setValidMonth(Integer.parseInt(giltighet1_textfield.getText()));
        cc.setValidYear(Integer.parseInt(giltighet2_textfield.getText()));
        cc.setVerificationCode(Integer.parseInt(cvc_textfield.getText()));

    }

}
