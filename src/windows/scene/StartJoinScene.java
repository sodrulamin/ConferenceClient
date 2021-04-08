package windows.scene;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sample.XmlConstants;

public class StartJoinScene {
    private static int SPACING = 20;
    private final boolean isJoin;
    public boolean running;
    public boolean finished;
    public StartJoinScene(boolean isJoin){
        this.isJoin = isJoin;
        running = false;
        finished = false;
    }

    public Label inputDeviceLabel;
    public Label outputDeviceLabel;
    public Label recordTypeLabel;
    public Label meetingIdLabel;
    public Label meetingPasswordLabel;
    public Label noiseCancelLabel;
    public Label delayLabel;
    public Label displayNameLabel;
    public Label gainLabel;


    public ChoiceBox<String> inputDeviceBox;
    public ChoiceBox<String> outputDeviceBox;
    public ChoiceBox<String> recordTypeBox;

    public TextField meetingIdField;
    public TextField meetingPasswordField;
    public TextField displayNameField;
    public TextField delayField;

    public RadioButton hardwareRadioButton;
    public RadioButton webrtcRadioButton;
    public RadioButton speexRadioButton;
    public RadioButton noneRadioButton;

    public Slider gainSlider;

    public ListView<String> participantView;

    public TextArea descriptionArea;

    public Button submitButton;

    ToggleGroup echoCancelModeGroup = new ToggleGroup();

    public Scene createScene(){

        byte [] deviceList = new byte[2048];
        int deviceLen = 0;
        String [] arr;

        inputDeviceLabel = new Label("Input Device: ");
        outputDeviceLabel = new Label("Output Device: ");
        recordTypeLabel = new Label("Record Type: ");
        meetingIdLabel = new Label("Meeting ID: ");
        meetingPasswordLabel = new Label("Meeting Password: ");
        noiseCancelLabel = new Label("Noise & Echo Cancel: ");
        delayLabel = new Label("Delay: ");
        displayNameLabel = new Label("Display Name: ");
        gainLabel = new Label("Gain: ");

        inputDeviceBox = new ChoiceBox();
        /*deviceLen = AudioRecord.INSTANCE.getDeviceList(Pointer.NULL, Constants.DEVICE_TYPE_AUDIO_INPUT, deviceList);
        arr =  new String(deviceList, 0, deviceLen).split("#");
        for(int i=0;i<arr.length;i++)
            inputDeviceBox.getItems().add(i, arr[i]);
        inputDeviceBox.getSelectionModel().selectFirst();*/

        outputDeviceBox = new ChoiceBox();
        /*deviceLen = AudioRecord.INSTANCE.getDeviceList(Pointer.NULL, Constants.DEVICE_TYPE_AUDIO_OUTPUT, deviceList);
        arr =  new String(deviceList, 0, deviceLen).split("#");
        for(int i=0;i<arr.length;i++)
            outputDeviceBox.getItems().add(i, arr[i]);
        outputDeviceBox.getSelectionModel().selectFirst();*/

        recordTypeBox = new ChoiceBox();
        recordTypeBox.getItems().add(0, XmlConstants.RECORDING_MODE_C_STR);
        recordTypeBox.getItems().add(1, XmlConstants.RECORDING_MODE_JAVA_STR);

        meetingIdField = new TextField();
        meetingPasswordField = new TextField();
        displayNameField = new TextField();
//        displayNameField.setText(Configurations.getInstance().getAccountDisplayName());
        delayField = new TextField();
        delayField.setMaxWidth(70);
        delayField.setText(480 + "");

        hardwareRadioButton = new RadioButton(XmlConstants.AUDIO_PROCESSING_BUTTON_HARDWARE);
        webrtcRadioButton = new RadioButton(XmlConstants.AUDIO_PROCESSING_BUTTON_WEBRTC);
        speexRadioButton = new RadioButton(XmlConstants.AUDIO_PROCESSING_BUTTON_SPEEX);
        noneRadioButton = new RadioButton(XmlConstants.AUDIO_PROCESSING_BUTTON_NONE);
        hardwareRadioButton.setToggleGroup(echoCancelModeGroup);
        webrtcRadioButton.setToggleGroup(echoCancelModeGroup);
        speexRadioButton.setToggleGroup(echoCancelModeGroup);
        noneRadioButton.setToggleGroup(echoCancelModeGroup);
        echoCancelModeGroup.selectToggle(hardwareRadioButton);

        gainSlider = new Slider();
        gainSlider.setMin(1.0);
        gainSlider.setMax(4.0);
        gainSlider.setMajorTickUnit(1.0);
        gainSlider.setMinorTickCount(1);
        gainSlider.setOrientation(Orientation.VERTICAL);
        gainSlider.setPrefSize(35, 100);
        gainSlider.setShowTickLabels(true);
        gainSlider.setValue(1.0);
        gainSlider.setBlockIncrement(1.0);
        gainSlider.setCache(true);

        participantView = new ListView();
        participantView.setPrefSize(440, 270);
        participantView.setDisable(true);

        descriptionArea = new TextArea();
        descriptionArea.setPrefSize(440, 270);
        descriptionArea.setEditable(false);
        descriptionArea.setDisable(true);

        if(isJoin)
            submitButton = new Button("Join");
        else
            submitButton = new Button("Start");
        submitButton.setDefaultButton(true);
        submitButton.setPrefSize(150, 60);

        HBox first = new HBox();
        HBox second = new HBox();
        HBox third = new HBox();
        first.setSpacing(5);
        second.setSpacing(5);
        third.setSpacing(5);

        VBox labelVbox = new VBox();
        VBox fieldVbox = new VBox();
        HBox.setHgrow(fieldVbox, Priority.ALWAYS);

        labelVbox.setSpacing(20);
        fieldVbox.setSpacing(10);

        labelVbox.getChildren().addAll(inputDeviceLabel, meetingIdLabel);
        fieldVbox.getChildren().addAll(inputDeviceBox, meetingIdField);
        HBox.setHgrow(meetingIdField, Priority.ALWAYS);
        HBox.setHgrow(inputDeviceBox, Priority.ALWAYS);
        HBox.setHgrow(first, Priority.ALWAYS);
        first.getChildren().addAll(labelVbox, fieldVbox);

        labelVbox = new VBox();
        fieldVbox = new VBox();
        HBox.setHgrow(fieldVbox, Priority.ALWAYS);

        labelVbox.setSpacing(20);
        fieldVbox.setSpacing(10);

        labelVbox.getChildren().addAll(outputDeviceLabel, meetingPasswordLabel);
        fieldVbox.getChildren().addAll(outputDeviceBox, meetingPasswordField);
        HBox.setHgrow(outputDeviceBox, Priority.ALWAYS);
        HBox.setHgrow(meetingPasswordField, Priority.ALWAYS);
        HBox.setHgrow(second, Priority.ALWAYS);
        second.getChildren().addAll(labelVbox, fieldVbox);

        labelVbox = new VBox();
        fieldVbox = new VBox();
        HBox.setHgrow(fieldVbox, Priority.ALWAYS);

        labelVbox.setSpacing(20);
        fieldVbox.setSpacing(10);

        labelVbox.getChildren().addAll(recordTypeLabel, displayNameLabel, delayLabel);
        fieldVbox.getChildren().addAll(recordTypeBox, displayNameField, delayField);
        HBox.setHgrow(recordTypeBox, Priority.ALWAYS);
        HBox.setHgrow(displayNameField, Priority.ALWAYS);
        HBox.setHgrow(delayField, Priority.ALWAYS);
        HBox.setHgrow(third, Priority.ALWAYS);
        third.getChildren().addAll(labelVbox, fieldVbox);

        HBox meetingInfoHBox = new HBox();

        meetingInfoHBox.setSpacing(20);
        HBox.setHgrow(meetingInfoHBox, Priority.ALWAYS);
        meetingInfoHBox.getChildren().addAll(first, second);

        HBox noiseCancelHbox = new HBox();
        noiseCancelHbox.setSpacing(20);
        noiseCancelHbox.getChildren().addAll(noiseCancelLabel, hardwareRadioButton, webrtcRadioButton, speexRadioButton,
                noneRadioButton);

        VBox parameterLeftVbox = new VBox();
        parameterLeftVbox.setSpacing(10);
        HBox.setHgrow(parameterLeftVbox, Priority.ALWAYS);
        parameterLeftVbox.getChildren().addAll(meetingInfoHBox, noiseCancelHbox);

        VBox parameterRightVbox = new VBox();
        parameterRightVbox.getChildren().addAll(gainSlider, gainLabel);

        HBox parameterHBox = new HBox();
        parameterHBox.setSpacing(20);
        parameterHBox.getChildren().addAll(parameterLeftVbox, third, parameterRightVbox);

        HBox descriptionHbox = new HBox();
        descriptionHbox.setSpacing(20);
        descriptionHbox.setAlignment(Pos.CENTER);
        HBox.setHgrow(participantView, Priority.ALWAYS);
        HBox.setHgrow(descriptionArea, Priority.ALWAYS);
        VBox.setVgrow(participantView, Priority.ALWAYS);
        VBox.setVgrow(descriptionArea, Priority.ALWAYS);
        descriptionHbox.getChildren().addAll(participantView, descriptionArea);
        VBox.setVgrow(descriptionHbox, Priority.ALWAYS);

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().addAll(submitButton);


        VBox vbox = new VBox();
        vbox.getChildren().addAll(parameterHBox, descriptionHbox, buttonHBox);
        vbox.setSpacing(SPACING);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        initEventHandlers();

        Scene scene = new Scene(vbox);

        return scene;
    }
    public void initEventHandlers(){
//        AudioRecord.INSTANCE.initiate(Pointer.NULL, true);
        gainSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            long roundValue = Math.round((double)newValue);
            gainSlider.setValue((double) roundValue);
//            AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_GAIN_FACTOR, (int)roundValue);
        });
    }

    public void setAudioProcessingMode(){
        boolean javaRecord = recordTypeBox.getSelectionModel().getSelectedIndex() == 1;
//        Configurations.getInstance().setJavaRecording(javaRecord);
        ToggleButton selectedEchoModeButton = (ToggleButton) echoCancelModeGroup.getSelectedToggle();
        String buttonName = selectedEchoModeButton.getText();
        /*if(buttonName.equalsIgnoreCase(XmlConstants.AUDIO_PROCESSING_BUTTON_HARDWARE)){
            AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_AUDIO_PROCESSING_MODE,
                    Constants.AUDIO_PROCESSING_HARDWARE);
        }
        else if(buttonName.equalsIgnoreCase(XmlConstants.AUDIO_PROCESSING_BUTTON_WEBRTC)){
            String delayFieldString = delayField.getText();
            if(delayFieldString != null){
                AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_ECHO_CANCEL_DELAY,
                        Integer.parseInt(delayFieldString.trim()));
            }
            AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_AUDIO_PROCESSING_MODE,
                    Constants.AUDIO_PROCESSING_WEBRTC);
        }
        else if(buttonName.equalsIgnoreCase(XmlConstants.AUDIO_PROCESSING_BUTTON_SPEEX)){
            String delayFieldString = delayField.getText();
            if(delayFieldString != null){
                AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_ECHO_CANCEL_DELAY,
                        Integer.parseInt(delayFieldString.trim()));
            }
            AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_AUDIO_PROCESSING_MODE,
                    Constants.AUDIO_PROCESSING_SPEEX);
        }
        else{
            AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_AUDIO_PROCESSING_MODE,
                    Constants.AUDIO_PROCESSING_NONE);
        }
        AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_GAIN_FACTOR, (int)gainSlider.getValue());
        AudioRecord.INSTANCE.setParam(Pointer.NULL, Constants.PARAM_SAMPLE_PER_SEC,
                (int) JavaAudioFormat.getAudioFormat().getSampleRate());*/
    }
    public boolean isNickNameOkay(){
        String nickName = displayNameField.getText();
        if (nickName == null || nickName.length() == 0) {
            return false;
        }else{
//            Configurations.getInstance().setAccountDisplayName(nickName);
        }

        return true;
    }
    public void onSubmitButtonPressed(){
        running = !running;
        if(isJoin){ /////this page representing as join page
            submitButton.setText(running ? "Leave" : "Join");
        }
        else{/// this page representing as start meeting page
            submitButton.setText(running ? "Leave" : "Start");
        }
    }
}
