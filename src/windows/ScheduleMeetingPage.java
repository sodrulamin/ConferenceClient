package windows;

import conference.Conference;
import conference.Constants;
import configuration.Configurations;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import sample.Main;
import sample.XmlConstants;
import util.TimeHelper;
import windows.scene.ScheduleMeetingScene;

import java.io.IOException;

public class ScheduleMeetingPage {
    private static Logger logger = Logger.getLogger(ScheduleMeetingPage.class);
    public Stage stage;
    public ScheduleMeetingScene scheduleMeetingScene;

    private static final int NO_ERROR = 0;
    private static final int MEETING_NAME_INVALID = 1;
    private static final int MEETING_PASSWORD_INVALID = 2;

    public ScheduleMeetingPage(){
        stage = new Stage();
        stage.setTitle(Constants.TITLE);
        stage.setMinWidth(XmlConstants.MIN_WINDOW_WIDTH);
        stage.setMinHeight(XmlConstants.MIN_WINDOW_HEIGHT);

        scheduleMeetingScene = new ScheduleMeetingScene();
        stage.setScene(scheduleMeetingScene.createScene(this));
    }
    public void show(){
        stage.show();
    }
    public void initOwner(Stage stage) {
        this.stage.initOwner(stage);
        this.stage.initModality(Modality.WINDOW_MODAL);
    }

    public void scheduleMeeting() {
        scheduleMeetingScene.buttonSchedule.setDisable(true);
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            int error = checkParametersValidity();

            switch (error) {
                case MEETING_NAME_INVALID:
                    alert.setContentText(XmlConstants.EMPTY_MEETING_NAME_ERROR_TEXT);
                    alert.setHeaderText(XmlConstants.EMPTY_NAME_ERROR_HEADER);
                    alert.showAndWait();
                    scheduleMeetingScene.meetingNameField.requestFocus();
                    break;
                case MEETING_PASSWORD_INVALID:
                    alert.setContentText(XmlConstants.EMPTY_PASSWORD_ERROR_TEXT);
                    alert.setHeaderText(XmlConstants.EMPTY_PASSWORD_ERROR_HEADER);
                    alert.showAndWait();
                    scheduleMeetingScene.meetingPasswordField.requestFocus();
                    break;

                default:
                    break;
            }
            if (error == NO_ERROR) {
                Conference conference = new Conference();
                conference.dummyId = Conference.createDummyId();
                conference.name = scheduleMeetingScene.meetingNameField.getText();
                conference.pass = scheduleMeetingScene.meetingPasswordField.getText();
                conference.duration = Integer.parseInt(scheduleMeetingScene.hourChoice.getSelectionModel().getSelectedItem()) * 60
                        + Integer.parseInt(scheduleMeetingScene.minuteChoice.getSelectionModel().getSelectedItem());
                conference.lobbyEnabled = scheduleMeetingScene.enableLobbyCheck.isSelected();
                conference.startWithVideoOff = scheduleMeetingScene.startWithVideoOffCheck.isSelected();
                conference.startWithAudioOff = scheduleMeetingScene.startWithAudioOffCheck.isSelected();
                conference.startWithScreenSharing = scheduleMeetingScene.startWithScreenShareCheck.isSelected();
                conference.startTime = TimeHelper.getInstance().getLongTimeFromStringTime(scheduleMeetingScene.startTimeField.getText(),
                        TimeHelper.DEFAULT_TIME_FORMAT);


                boolean scheduleMessageSent = false;
                try {
                    scheduleMessageSent = scheduleMeeting(conference);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    if(scheduleMessageSent) {
                        synchronized (conference) {
                            ///wait for maximum 10 second to get 200 ok of schedule message
                            conference.wait(10 * TimeHelper.SECOND);
                        }
                        stage.hide();
                    }
                } catch (InterruptedException e) {
                    logger.debug("Exception", e);
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        scheduleMeetingScene.buttonSchedule.setDisable(false);
    }

    private int checkParametersValidity() {
        if(scheduleMeetingScene.meetingNameField.getText() == null
                || scheduleMeetingScene.meetingNameField.getText().isEmpty())
            return MEETING_NAME_INVALID;
        else if(scheduleMeetingScene.meetingPasswordField.getText() == null
                || scheduleMeetingScene.meetingPasswordField.getText().isEmpty())
            return MEETING_PASSWORD_INVALID;
        return NO_ERROR;
    }
    private boolean scheduleMeeting(Conference conference) throws IOException {
        JSONObject conferenceObj = conference.createJsonObject();
        conferenceObj.put(Constants.TYPE, Constants.CONFERENCE_SCHEDULE_MEETING);
        conferenceObj.put(Constants.DESCRIPTION, Constants.DESCRIPTION_SCHEDULE_MEETING);
        conferenceObj.put(Constants.FROM_USER, Configurations.getInstance().getAccountUid());
        conferenceObj.put(Constants.TO_USER, Configurations.getInstance().getAccountUid());
        Conference.saveSchedulePendingConference(conference);
        /*if(Main.signalingMessageReceiver != null) {
            Main.signalingMessageReceiver.sendJSONMessage(conferenceObj);
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please check if you have set Signaling IP/Port properly (Setting) and you have active internet" +
                    " connection");
            alert.setHeaderText("Not Connected to Server");
            alert.showAndWait();

        }*/
        return false;
    }
    public void changeStartTime() {
        DateTimePicker datePicker = new DateTimePicker();
        datePicker.setRootStage(stage);

        datePicker.setChangeListener(new DateTimePicker.ChangeListener() {
            @Override
            public void changed(String newTime) {
                scheduleMeetingScene.startTimeField.setText(newTime);
            }
        });

        try {
            datePicker.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
