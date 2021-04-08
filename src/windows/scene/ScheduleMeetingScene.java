package windows.scene;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import util.Functions;
import util.TimeHelper;
import windows.ScheduleMeetingPage;

import java.util.Calendar;

public class ScheduleMeetingScene {
    public Button buttonSchedule;
    public Button buttonPickTime;
    public TextField meetingNameField;
    public TextField meetingPasswordField;
    public TextField startTimeField;
    public CheckBox enableLobbyCheck;
    public CheckBox startWithVideoOffCheck;
    public CheckBox startWithAudioOffCheck;
    public CheckBox startWithScreenShareCheck;
    public ChoiceBox<String> hourChoice;
    public ChoiceBox<String> minuteChoice;

    public Scene createScene(ScheduleMeetingPage scheduleMeetingPage){
        VBox vbox = new VBox();

        HBox parameterBox = new HBox();
        Region topOpenRegion = new Region();
        topOpenRegion.setMinHeight(40);
        Region openRegion = new Region();
        VBox.setVgrow(openRegion, Priority.ALWAYS);
        HBox buttonBox = new HBox();

        VBox firstLabelBox = new VBox();
        VBox firstFieldBox = new VBox();
        HBox.setHgrow(firstFieldBox, Priority.ALWAYS);
        VBox firstCheckBox = new VBox();

        Label meetingNameLabel = new Label("Meeting Name: ");
        Label meetingPasswordLabel = new Label("Meeting Password: ");
        Label meetingDurationLabel = new Label("Meeting Duration(HH:mm): ");
        Label meetingStartTimeLabel = new Label("Meeting Start Time: ");
        firstLabelBox.setSpacing(20);
        firstLabelBox.getChildren().addAll(meetingNameLabel, meetingPasswordLabel, meetingDurationLabel, meetingStartTimeLabel);

        meetingNameField = new TextField();
        meetingPasswordField = new TextField();
        HBox durationBox = new HBox();
        hourChoice = new ChoiceBox();
        Label colonLabel = new Label(":");
        minuteChoice = new ChoiceBox();
        durationBox.getChildren().addAll(hourChoice, colonLabel, minuteChoice);
        HBox startTimeBox = new HBox();
        startTimeField = new TextField();
        buttonPickTime = new Button("change");
        startTimeBox.getChildren().addAll(startTimeField, buttonPickTime);
        HBox.setHgrow(meetingNameField, Priority.ALWAYS);
        HBox.setHgrow(meetingPasswordField, Priority.ALWAYS);
        HBox.setHgrow(startTimeField, Priority.ALWAYS);
        firstFieldBox.setSpacing(10);
        firstFieldBox.getChildren().addAll(meetingNameField, meetingPasswordField, durationBox, startTimeBox);

        Region parameterPaddingRegion = new Region();
        HBox.setHgrow(parameterPaddingRegion, Priority.ALWAYS);

        enableLobbyCheck = new CheckBox("Enable Lobby");
        enableLobbyCheck.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        startWithVideoOffCheck = new CheckBox("Start With Video OFF");
        startWithVideoOffCheck.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        startWithAudioOffCheck = new CheckBox("Start With Audio OFF");
        startWithAudioOffCheck.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        startWithScreenShareCheck = new CheckBox("Start With Screen Share");
        startWithScreenShareCheck.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        firstCheckBox.setSpacing(20);
        firstCheckBox.setAlignment(Pos.BASELINE_RIGHT);
        firstCheckBox.getChildren().addAll(enableLobbyCheck, startWithVideoOffCheck, startWithAudioOffCheck, startWithScreenShareCheck);

        Region parameterPaddingRightRegion = new Region();
        HBox.setHgrow(parameterPaddingRightRegion, Priority.ALWAYS);

        parameterBox.getChildren().addAll(firstLabelBox, firstFieldBox, parameterPaddingRegion, firstCheckBox, parameterPaddingRightRegion);

        buttonSchedule = new Button("Schedule");
        buttonSchedule.setMinSize(125, 50);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(buttonSchedule);
        vbox.getChildren().addAll(topOpenRegion, parameterBox, openRegion, buttonBox);
        vbox.setPadding(new Insets(10, 10, 40, 10));
        Scene scene = new Scene(vbox);

        initializeParameters(scheduleMeetingPage);

        return scene;
    }
    public void initializeParameters(ScheduleMeetingPage scheduleMeetingPage){
        for(int i=0;i<=45;i+=15)
            minuteChoice.getItems().add(String.format("%02d", i));
        for(int i=0;i<=6;i++)
            hourChoice.getItems().add(String.format("%02d", i));
        hourChoice.getSelectionModel().selectFirst();
        minuteChoice.getSelectionModel().select(2);
        buttonSchedule.setDisable(false);
        String time = TimeHelper.getInstance().getTimeInFormat(Calendar.getInstance(), TimeHelper.DEFAULT_TIME_FORMAT);

        meetingNameField.setText("Business Meeting");
        meetingPasswordField.setText("1234");
        startTimeField.setText(time);

        buttonSchedule.setOnAction(e -> {
            scheduleMeetingPage.scheduleMeeting();
        });

        buttonPickTime.setOnAction(e -> {
            scheduleMeetingPage.changeStartTime();
        });

        //TODO set minute and hour choice box value change listener
        //1. if hour is 0 then minute 0-29 should be disabled
        //2. else if hour is 6 then minute 0-59 should be disabled
    }

}
