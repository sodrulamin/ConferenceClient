package windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.TimeHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class DateTimePicker {
    private static final String TITLE = "Pick Date and Time";
    private ArrayList<ChangeListener> changeListeners = new ArrayList<>();
    private Stage mainStage;

    public DateTimePicker(){
        mainStage = new Stage();
    }
    public void show() throws IOException {

        mainStage.setTitle(TITLE);

        mainStage.setWidth(300);
        mainStage.setHeight(200);
        mainStage.setResizable(false);

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        Button okButton = new Button("OK");

        Calendar calendar = Calendar.getInstance();
        int hourOfTheDay = calendar.get(Calendar.HOUR);

        ChoiceBox<String> hourChoiceBox = new ChoiceBox();
        for(int i=1;i<=12;i++)
            hourChoiceBox.getItems().add(String.format("%02d", i));
        if(hourOfTheDay == 0)
            hourChoiceBox.getSelectionModel().selectLast();
        else
            hourChoiceBox.getSelectionModel().select(hourOfTheDay - 1);

        ChoiceBox<String> minuteChoiceBox = new ChoiceBox();
        for(int i=0;i<60;i++)
            minuteChoiceBox.getItems().add(String.format("%02d", i));
        minuteChoiceBox.getSelectionModel().select(calendar.get(Calendar.MINUTE));

        ChoiceBox<String> ampmChoiceBox = new ChoiceBox();
        ampmChoiceBox.getItems().addAll("AM", "PM");
        if(calendar.get(Calendar.AM_PM) == Calendar.AM)
            ampmChoiceBox.getSelectionModel().selectFirst();
        else
            ampmChoiceBox.getSelectionModel().selectLast();

        Label dateLable = new Label("Date:");

        Label timeLable = new Label("Time:");

        okButton.setOnAction(e -> {
            StringBuilder timeBuilder = new StringBuilder();
            LocalDate date = datePicker.getValue();

            timeBuilder.append(TimeHelper.getInstance().getTimeInFormat(date,
                    TimeHelper.DEFAULT_DATE_FORMAT));
            timeBuilder.append(" ")
                    .append(hourChoiceBox.getSelectionModel().getSelectedItem())
                    .append(":")
                    .append(minuteChoiceBox.getSelectionModel().getSelectedItem()).append(" ")
                    .append(ampmChoiceBox.getSelectionModel().getSelectedItem());



            for(ChangeListener listener: changeListeners)
                listener.changed(timeBuilder.toString());
            mainStage.close();
        });

        HBox datehBox = new HBox();
        datehBox.setSpacing(15);
        datehBox.getChildren().addAll(dateLable, datePicker);

        HBox timehBox = new HBox();
        timehBox.getChildren().addAll(hourChoiceBox, minuteChoiceBox, ampmChoiceBox);
        HBox timeWithLabelhBox = new HBox();
        timeWithLabelhBox.setSpacing(15);
        timeWithLabelhBox.getChildren().addAll(timeLable, timehBox);


        HBox okhBox = new HBox();
        okhBox.setMinWidth(mainStage.getMinWidth());
        okhBox.setAlignment(Pos.CENTER);
        okhBox.getChildren().addAll(okButton);

        VBox vbox1 = new VBox();
        vbox1.setFillWidth(true);
        vbox1.setSpacing(5);
        vbox1.setPadding(new Insets(10, 0, 0, 10));
        vbox1.getChildren().addAll(datehBox, timeWithLabelhBox);

        VBox vbox2 = new VBox();
        vbox2.setPadding(new Insets(10, 0, 0, 10));
        vbox2.getChildren().addAll(okhBox);

        VBox vbox = new VBox();
        vbox.setMinHeight(mainStage.getMinHeight());
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(vbox1, vbox2);

        Scene scene = new Scene(vbox);


        mainStage.setScene(scene);
        mainStage.show();
    }
    public void close(){
        mainStage.close();
    }
    public void hide(){
        mainStage.hide();
    }
    public void setRootStage(Stage rootStage){
        mainStage.initOwner(rootStage);
        mainStage.initModality(Modality.WINDOW_MODAL);
    }
    public void setChangeListener(ChangeListener listener){
        changeListeners.add(listener);
    }

    public interface ChangeListener {
        void changed(String newTime);
    }
}
