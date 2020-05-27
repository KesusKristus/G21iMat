package iMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.util.Date;

public class HistoryCardController extends AnchorPane {

    Date date;

    Order order;

    String sweDate;

    @FXML
    Label cardDateLabel;
    @FXML
    AnchorPane historyPane;


    HistoryController controller;

    public HistoryCardController(HistoryController historyController, Order order) {

        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("history_list_card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        controller = historyController;

        this.order = order;
        date = order.getDate();

        sweDate = getSwedishDate(date);

        cardDateLabel.setText(sweDate);
    }

    private String getSwedishDate(Date date){

        String day = null;
        String month = null;
        String dateNumber = null;
        String time = null;

        int startIndex = 0;

        int spaces = 0;

        for (int i = 0; i < date.toString().length(); i++){

            if (date.toString().charAt(i) == ' '){

                String substring = date.toString().substring(startIndex + 1, i);

                switch (spaces){
                    case 0: day = date.toString().substring(0, i);
                        break;
                    case 1: month = substring;
                        break;
                    case 2: dateNumber = substring;
                        break;
                    case 3: time = date.toString().substring(startIndex + 1, i - 3);
                        break;
                }
                startIndex = i;
                spaces++;
            }
        }

        day = translateDay(day);
        month = monthNumber(month);

        return (day + " " + dateNumber + "/" + month + " kl: " + time);
    }

    private String translateDay(String day){

        switch (day){
            case "Mon": return "mån";
            case "Tue": return "tis";
            case "Wed": return "ons";
            case "Thu": return "tors";
            case "Fri": return "fre";
            case "Sat": return "lör";
            case "Sun": return "sön";
            default: return "blame Henrik";
        }
    }

    private String monthNumber(String month){

        switch (month){
            case "Jan": return "1";
            case "Feb": return "2";
            case "Mar": return "3";
            case "Apr": return "4";
            case "May": return "5";
            case "Jun": return "6";
            case "Jul": return "7";
            case "Aug": return "8";
            case "Sep": return "9";
            case "Oct": return "10";
            case "Nov": return "11";
            case "Dec": return "12";
            default: return "blame Henrik";
        }

        /*switch (month){
            case "Jan": return "januari";
            case "Feb": return "februari";
            case "Mar": return "mars";
            case "Apr": return "april";
            case "May": return "maj";
            case "Jun": return "juni";
            case "Jul": return "juli";
            case "Aug": return "augusti";
            case "Sep": return "september";
            case "Oct": return "october";
            case "Nov": return "november";
            case "Dec": return "december";
            default: return "blame Henrik";
        }*/
    }


    @FXML
    void onClick() {

        controller.populateProductList(order, sweDate);
    }


}
