package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeHelper {
	private static TimeHelper timeHelper = null;
	public static final long SECOND = 1000;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;
	public static final String DEFAULT_TIME_FORMAT = "dd-MMM-yyyy HH:mm aa";
	public static final String DEFAULT_DATE_FORMAT = "dd-MMM-yyyy";

	private TimeHelper(){}

	public static TimeHelper getInstance(){
		if(timeHelper == null)
			timeHelper = new TimeHelper();
		return timeHelper;
	}

    public String getPrintableDateTime(Long timestamp) {
		if(timestamp <= 0) {
			return "N/A";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM, yyyy HH:mm z");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
		Date date = new Date(timestamp);

		return sdf.format(date);
    }
	public String getTimeInFormat(Calendar cal, String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(cal.getTime());
	}

	public String getTimeInFormat(LocalDate value, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return formatter.format(value);
	}

	public long getLongTimeFromStringTime(String dateString2, String format) {
		try {
			String dateString = "04-Apr-2021 08:48 AM";
			DateFormat simpleDateFormatGMT = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
			Date date = simpleDateFormatGMT.parse(dateString);
//			System.out.println(date.toString());
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
