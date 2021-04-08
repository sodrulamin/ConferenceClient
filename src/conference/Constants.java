package conference;

public class Constants {
    public static final String TITLE = "Reve Conference";
    /////Message types
    public static final int CONFERENCE_SCHEDULE_MEETING = 0;
    public static final int CONFERENCE_START_MEETING = 1;
    public static final int CONFERENCE_JOIN_MEETING = 2;
    public static final int CONFERENCE_PING_MESSAGE = 3;
    public static final int CONFERENCE_LOGIN_MESSAGE = 4;
    public static final int CONFERENCE_DELETE = 5;
    public static final int CONFERENCE_STOP_MEETING = 6;
    public static final int CONFERENCE_LEAVE_MEETING = 7;
    public static final int CONFERENCE_PARTICIPANT_LIST = 8;

    ////Attributes
    public static final String TYPE = "type";
    public static final String REQUEST_TYPE = "rType";
    public static final String DESCRIPTION = "desc";
    public static final String MEETING_NAME = "mName";
    public static final String MEETING_DURATION = "mDur";
    public static final String USER_NAME = "username";
    public static final String DISPLAY_NAME = "dName";
    public static final String MEETING_LOBBY_ENABLED = "mlEnabled";
    public static final String MEETING_PASSWORD = "mPass";
    public static final String MEETING_START_WITH_VIDEO_MUTED = "swvOff";
    public static final String MEETING_START_WITH_AUDIO_MUTED = "swaOff";
    public static final String MEETING_START_WITH_SCREEN_SHARING = "swssOn";
    public static final String MEETING_START_TIME = "mStartTime";
    public static final String MEETING_CREATE_TIME = "mCreateTime";
    public static final String STATUS = "sts";
    public static final String MEETING_ID = "mID";
    public static final String MEETING_DUMMY_ID = "mdID";
    public static final String TO_USER = "to";
    public static final String FROM_USER = "from";
    public static final String REALM = "realm";
    public static final String QOP = "qop";
    public static final String NONCE = "nonce";
    public static final String ALGORITHM = "algorithm";
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    public static final String AUTHORIZATION = "Authorization";
    public static final String URI = "uri";
    public static final String RESPONSE = "response";
    public static final String CONFERENCE_LIST = "confList";
    public static final String PARTICIPANT_ID = "partId";
    public static final String MEDIA_IP = "medIP";
    public static final String MEDIA_PORT = "medPort";
    public static final String PARTICIPANT_LIST = "partList";
    public static final String EVENT_TYPE = "eType";
    public static final String PARTICIPANT_COUNT = "pCount";
    public static final String OWNER = "owner";

    ////Event description
    public static final String DESCRIPTION_SCHEDULE_MEETING = "Schedule Meeting";
    public static final String DESCRIPTION_PING = "Ping Message";
    public static final String DESCRIPTION_PONG = "Pong Message";
    public static final String DESCRIPTION_LOGIN = "Login";
    public static final String DESCRIPTION_DELETE = "Delete Conference";
    public static final String DESCRIPTION_START_MEETING = "Start Meeting";
    public static final String DESCRIPTION_STOP_MEETING = "Stop Meeting";
    public static final String DESCRIPTION_JOIN_MEETING = "Join Meeting";
    public static final String DESCRIPTION_LEAVE_MEETING = "Leave Meeting";
}
