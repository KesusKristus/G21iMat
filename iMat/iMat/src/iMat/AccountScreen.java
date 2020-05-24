package iMat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @FXML Label error_label;
    private Map<String, String> validationErrors = new HashMap<>();

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
        firstname_textfield.setOnKeyReleased(e -> {
            alphabeticalFieldValidation((TextField) e.getTarget());
        });
        firstname_textfield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    c.setFirstName(firstname_textfield.getText());
                }
            }
        });
        surname_textfield.setOnKeyReleased(e -> {
            alphabeticalFieldValidation((TextField) e.getTarget());
        });
        surname_textfield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    c.setLastName(surname_textfield.getText());
                }
            }
        });

        adress1_textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField target = (TextField) e.getTarget();
                if (validate(target.getText())) c.setFirstName(firstname_textfield.getText());
                else {
                    //TODO något att visa att det är fel
                    System.out.println("inte en adress");
                }
            }

            private boolean validate(String text) {

                return true;
            }
        });
        adress1_textfield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                        c.setAddress(adress1_textfield.getText());
                }
            }
        });

        email_textfield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            private Pattern VALID_EMAIL_ADDRESS_REGEX =
                    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldProertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    if (validate(email_textfield.getText())) {
                        System.out.println("korrekt email");
                        removeErrorClass(email_textfield);
                        validationErrors.remove("email");
                        c.setEmail(email_textfield.getText());
                    } else {
                        System.out.println("inte korrekt email");
                        validationErrors.put("email","inte korrekt email");
                        addErrorClass(email_textfield);
                    }
                }
            }

            private boolean validate(String text) {
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(text);
                return matcher.find();
            }
        });


        konto_textfield.setOnKeyReleased(e -> {
            TextField target = ((TextField) e.getTarget());
            if (target.getText().length() < 16) {
                numericFieldValidation((TextField) e.getTarget());
            }else {
                target.setText(target.getText().substring(0, 16));
                target.positionCaret(target.getText().length());
            }
        });
        konto_textfield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    if (konto_textfield.getText().length() == 16) {
                        removeErrorClass(konto_textfield);
                        validationErrors.remove("konto");
                        cc.setCardNumber(konto_textfield.getText());
                    } else {
                        System.out.println("inte korrekt kontokod mmåste vara 16 siffror");
                        validationErrors.put("konto","inte korrekt kontonummer måste vara 16 siffror");
                        addErrorClass(konto_textfield);
                    }
                }
            }
        });

        giltighet1_textfield.setOnKeyReleased(e -> {
            TextField target = ((TextField) e.getTarget());
            if (target.getText().length() < 2) {
                numericFieldValidation((TextField) e.getTarget());
            } else {
                target.setText(target.getText().substring(0, 2));
                target.positionCaret(target.getText().length());
            }
        });
        giltighet1_textfield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    Integer månad = Integer.parseInt(giltighet1_textfield.getText());
                    if (månad < 13 && månad > 0 && giltighet1_textfield.getText().length() == 2) {
                      removeErrorClass(giltighet1_textfield);
                      validationErrors.remove("månad");
                        cc.setValidMonth(Integer.parseInt(giltighet1_textfield.getText()));
                    } else {
                        System.out.println("inte korrekt månad måste vara 2 siffror");
                        validationErrors.put("månad","inte korrekt månad måste vara 2 siffror");
                        addErrorClass(giltighet1_textfield);
                    }
                }
            }
        });

        giltighet2_textfield.setOnKeyReleased(e -> {
            TextField target = ((TextField) e.getTarget());
            if (target.getText().length() < 2) {
                numericFieldValidation((TextField) e.getTarget());
            }else {
                target.setText(target.getText().substring(0, 2));
                target.positionCaret(target.getText().length());
            }
        });
        giltighet2_textfield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    if (giltighet2_textfield.getText().length() == 2) {
                        removeErrorClass(giltighet2_textfield);
                        validationErrors.remove("år");
                        cc.setValidYear(Integer.parseInt(giltighet2_textfield.getText()));
                    } else {
                        System.out.println("inte korrekt år måste vara 2 siffror");
                        validationErrors.put("år","inte korrekt år måste vara 2 siffror");
                        addErrorClass(giltighet2_textfield);
                    }
                }
            }
        });

        cvc_textfield.setOnKeyReleased(e -> {
            TextField target = ((TextField) e.getTarget());
            if (target.getText().length() < 3) {
                numericFieldValidation((TextField) e.getTarget());
            }else {
                target.setText(target.getText().substring(0, 3));
                target.positionCaret(target.getText().length());
            }
        });
        cvc_textfield.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    if (cvc_textfield.getText().length() == 3) {
                        removeErrorClass(cvc_textfield);
                        validationErrors.remove("cvc");
                        cc.setVerificationCode(Integer.parseInt(cvc_textfield.getText()));
                    } else {
                        System.out.println("inte korrekt cvc måste vara 3 siffror");
                        validationErrors.put("cvc","inte korrekt cvc måste vara 3 siffror");
                        addErrorClass(cvc_textfield);
                    }
                }
            }
        });
        mobilnummer_textfield.setOnKeyReleased(e -> {
            TextField target = ((TextField) e.getTarget());
            if (target.getText().length() < 10) {
                numericFieldValidation((TextField) e.getTarget());
            } else {
                target.setText(target.getText().substring(0, 10));
                target.positionCaret(target.getText().length());
            }
        });
    }

    private void addErrorClass(TextField tf) {
        tf.getStyleClass().add("error");
    }

    private void removeErrorClass(TextField tf) {
        tf.getStyleClass().remove("error");
    }

    private void alphabeticalFieldValidation(TextField target) {
        if (target.getText().matches("[a-zA-Z]+")) return;

        target.setText(target.getText().replaceAll("[^a-zA-Z]+", ""));
        target.positionCaret(target.getText().length());

    }
    private void numericFieldValidation(TextField target) {
        if (target.getText().matches("\\d*")) return;

        target.setText(target.getText().replaceAll("[^\\d]", ""));
        target.positionCaret(target.getText().length());

    }

    @FXML
    public void onClickSpara() {
        if (validationErrors.size() == 0) {
            error_label.setText("");
            setAllCustomerData();
            IMatDataHandler.getInstance().shutDown();
        } else {
            String text = "";
            for (Map.Entry<String, String> entry : validationErrors.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
                text = entry.getValue();
                break;
            }
            error_label.setText(text);
        }
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
