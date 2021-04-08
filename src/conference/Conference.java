package conference;

import configuration.Configurations;
import configuration.IpPort;
import configuration.PreferenceManager;
import javafx.application.Platform;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.Main;
import sample.PreferenceConstants;
//import sample.nativelib.RecorderThread;
import windows.scene.StartJoinScene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class Conference {
    private static Logger logger = Logger.getLogger(Conference.class);
    public static Conference runningConference;
    private static ConcurrentHashMap<String, Conference> schedulePendingConferenceMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Conference> scheduledMeetingMap = new ConcurrentHashMap<>();
    public String id ;
    public String dummyId;
    public String pass;
    public String name;
    public String owner;
    public int duration;
    public boolean lobbyEnabled;
    public boolean startWithVideoOff;
    public boolean startWithAudioOff;
    public boolean startWithScreenSharing;
    public long creationTime;
    public long startTime;
    public StartJoinScene meetingScene;
    public ConcurrentHashMap<String, Participant> participantList = null;
    public IpPort mediaIpPort;

    public static void loadAllMeeting(){
        runningConference = null;
        String meetingListJSONString = PreferenceManager.getInstance().get(PreferenceConstants.meetingList, null);
        if(meetingListJSONString != null) {
            ArrayList<Conference> conferenceList;
            try {
                conferenceList = parseConferenceListFromString(meetingListJSONString);
                for (Conference conference : conferenceList) {
                    if(conference.id != null)
                        scheduledMeetingMap.put(conference.id, conference);
                    else{
                        logger.debug("id is null");
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static ArrayList<Conference> parseConferenceListFromString(String meetingListJSONString)
            throws ParseException {
        JSONParser parser = new JSONParser();
        ArrayList<Conference> conferenceList = new ArrayList<>();
        JSONArray meetingArray = (JSONArray) parser.parse(meetingListJSONString);
        for(Object obj: meetingArray){
            JSONObject conf = (JSONObject) obj;
            Conference conference = new Conference();
            conference.parseAllPublicInfo(conf);
            conference.parseAllLocalInfo(conf);
            conferenceList.add(conference);
        }
        return conferenceList;
    }



    public Conference(){
        lobbyEnabled = false;
        creationTime = System.currentTimeMillis();
    }

    public static String createDummyId(){
        return System.currentTimeMillis() + "";
    }


    public void parseAllPublicInfo(JSONObject jsonObject) {
        name = (String) jsonObject.get(Constants.MEETING_NAME);
        id = (String) jsonObject.get(Constants.MEETING_ID);
        if(jsonObject.containsKey(Constants.MEETING_PASSWORD))
            pass = (String) jsonObject.get(Constants.MEETING_PASSWORD);
        if(jsonObject.containsKey(Constants.MEETING_DURATION))
            duration = Math.toIntExact((Long) jsonObject.get(Constants.MEETING_DURATION));
        if(jsonObject.containsKey(Constants.MEETING_LOBBY_ENABLED))
            lobbyEnabled = (boolean) jsonObject.get(Constants.MEETING_LOBBY_ENABLED);
        if(jsonObject.containsKey(Constants.MEETING_START_WITH_AUDIO_MUTED))
            startWithAudioOff = (boolean) jsonObject.get(Constants.MEETING_START_WITH_AUDIO_MUTED);
        if(jsonObject.containsKey(Constants.MEETING_START_WITH_VIDEO_MUTED))
            startWithVideoOff = (boolean) jsonObject.get(Constants.MEETING_START_WITH_VIDEO_MUTED);
        if(jsonObject.containsKey(Constants.MEETING_START_WITH_SCREEN_SHARING))
            startWithScreenSharing = (boolean) jsonObject.get(Constants.MEETING_START_WITH_SCREEN_SHARING);
        if(jsonObject.containsKey(Constants.MEETING_START_TIME))
            startTime = (long) jsonObject.get(Constants.MEETING_START_TIME);
    }
    private void parseAllLocalInfo(JSONObject jsonObject) {
        owner = Configurations.getInstance().getAccountUid();
        if(jsonObject.containsKey(Constants.MEETING_CREATE_TIME))
            creationTime = (long) jsonObject.get(Constants.MEETING_CREATE_TIME);
    }

    public JSONObject createJsonObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.MEETING_DUMMY_ID, dummyId);
        if(pass != null)
            jsonObject.put(Constants.MEETING_PASSWORD, pass);
        jsonObject.put(Constants.MEETING_NAME, name);
        jsonObject.put(Constants.MEETING_DURATION, duration);
        if(lobbyEnabled)
            jsonObject.put(Constants.MEETING_LOBBY_ENABLED, lobbyEnabled);
        if(lobbyEnabled)
            jsonObject.put(Constants.MEETING_START_WITH_VIDEO_MUTED, startWithVideoOff);
        if(lobbyEnabled)
            jsonObject.put(Constants.MEETING_START_WITH_AUDIO_MUTED, startWithAudioOff);
        if(lobbyEnabled)
            jsonObject.put(Constants.MEETING_START_WITH_SCREEN_SHARING, startWithScreenSharing);
        jsonObject.put(Constants.MEETING_START_TIME, startTime);

        return jsonObject;
    }
    public JSONObject addAllLocalInfo(JSONObject jsonObject){
        jsonObject.put(Constants.MEETING_ID, id);
        jsonObject.put(Constants.MEETING_CREATE_TIME, creationTime);
        return jsonObject;
    }

    public static Conference getSchedulePendingConferenceByID(String id){
        return schedulePendingConferenceMap.get(id);
    }
    public static void removeSchedulePendingConference(String id){
        schedulePendingConferenceMap.remove(id);
    }
    public static void saveSchedulePendingConference(Conference conference){
        schedulePendingConferenceMap.put(conference.dummyId, conference);
    }
    public static void saveScheduledConference(Conference conference) {
        scheduledMeetingMap.put(conference.id, conference);
        PreferenceManager.getInstance().put(PreferenceConstants.meetingList,
                getMeetingListAsJSONString());
    }

    private static String getMeetingListAsJSONString() {
        JSONArray jsonArray = new JSONArray();
        for(Conference conference: scheduledMeetingMap.values()){
            JSONObject jsonObject = conference.createJsonObject();
            jsonObject = conference.addAllLocalInfo(jsonObject);
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    public static void removeScheduleConference(String id){
        if(id != null) {
            scheduledMeetingMap.remove(id);
            if(scheduledMeetingMap.size() > 0) {
                PreferenceManager.getInstance().put(PreferenceConstants.meetingList,
                        getMeetingListAsJSONString());
            }
            else{
                PreferenceManager.getInstance().remove(PreferenceConstants.meetingList);
            }
        }
    }
    public static Collection<Conference> getScheduleConferenceList(){
        return scheduledMeetingMap.values();
    }
    public static Conference getScheduleConferenceByID(String conferenceId){
        return scheduledMeetingMap.get(conferenceId);
    }
    public static void clearScheduledMap() {
        scheduledMeetingMap.clear();
        PreferenceManager.getInstance().remove(PreferenceConstants.meetingList);
    }

    public int getParticipantCount() {
        if(participantList == null)
            return 0;
        return participantList.size();
    }
    public void addParticipant(Participant participant) {
        if(participantList == null)
            participantList = new ConcurrentHashMap<>();
        if(!participantList.containsKey(participant.id)) {
            participantList.put(participant.id, participant);
            Platform.runLater(() -> {
                meetingScene.participantView.getItems().add(participant.toString());
            });
        }
    }
    public void addParticipant(String participantId, String accountDisplayName) {
        if(participantList == null)
            participantList = new ConcurrentHashMap<>();
        if(!participantList.containsKey(participantId)){
            Participant participant = new Participant(participantId, accountDisplayName);
            addParticipant(participant);

        }
    }
    public void removeParticipant(String participantID) {
        if(participantID == null)
            return;

        Participant participant = participantList.remove(participantID);
        if(participant != null){
            Platform.runLater(() -> {
                meetingScene.participantView.getItems().remove(participant.toString());
            });
        }
    }
    public void clearParticipant() {
        Platform.runLater(() -> {
            meetingScene.participantView.getItems().clear();
        });

        participantList.clear();
    }

    public void startMedia() {
        meetingScene.setAudioProcessingMode();
        /*Main.recorderThread = new RecorderThread(mediaIpPort, Configurations.getInstance().isJavaRecording());
        Main.recorderThread.start();*/
        logger.debug("Recorder thread started successfully.");
    }

    private void stopMedia() {
//        Main.recorderThread.shutDown();
    }







    public void stopped() {
        stopMedia();
        clearParticipant();
        mediaIpPort = null;
        meetingScene.finished = true;
        Platform.runLater(() -> {
            meetingScene.submitButton.setText("Exit");
        });
    }


}
