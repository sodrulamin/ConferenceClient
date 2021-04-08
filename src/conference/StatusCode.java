package conference;

public class StatusCode {
	public static final int TRYING = 100;
	public static final int RINGING = 180;
	public static final int CALL_FORWARD = 181;
	public static final int QUEUED = 182;
	public static final int SESSION_PROGRESS = 183;
	public static final int OK = 200;
	public static final int MULTIPLE_CHOICES = 300;
	public static final int MOVED_PERMANENTLY = 301;
	public static final int MOVED_REMPORARILY = 302;
	public static final int USE_PROXY = 305;
	public static final int ALTERNATIVE_SERVICE = 380;
	public static final int BAD_REQUEST = 400;
	public static final int UNAUTHORIZED = 401;
	public static final int PAYMENT_REQUIRED = 402;
	public static final int FORBIDDEN = 403;
	public static final int NOTFOUND = 404;
	public static final int METHOD_NOT_ALLOWED = 405;
	public static final int NOT_ACCEPTABLE = 406;
	public static final int PROXY_AUTHENTICATE_REQUIRED = 407;
	public static final int TIMEOUT = 408;
	public static final int GONE = 410;
	public static final int ENTITY_LARGE = 413;
	public static final int REQUEST_URI_LARGE = 414;
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;
	public static final int UNSUPPORTED_URI = 416;
	public static final int TEMPORARY_UNAVAILABLE = 480;
	public static final int TRANSACTION_DOES_NOT_EXISTS = 481;
	public static final int ADDRESS_INCOMPLETE = 484;
	public static final int BUSY_HERE = 486;
	public static final int REQUEST_TERMINATED = 487;
	public static final int NOT_ACCEPTABLE_HERE = 488;
	public static final int BAD_EVENT = 489;
	public static final int DECLINED = 603;
	public static final int ACCEPTED = 202;
	public static final int CONDITIONAL_REQUEST_FAILED = 412;
	public static final int NOT_IMPLEMENTED = 501;
	public static final int BAD_GATEWAY = 502;
	public static final int INTERNAL_SERVER_ERROR = 503;
	public static final int MEETING_EVENT = 199;

	public static boolean isErrorMessage(int type){
		switch(type){
			case NOTFOUND:
			case NOT_ACCEPTABLE:
			case DECLINED:
			case FORBIDDEN:
			case BAD_REQUEST:
				return true;
		}

		return false;
	}
}
