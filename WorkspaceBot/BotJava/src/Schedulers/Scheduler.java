package Schedulers;

import org.telegram.telegrambots.api.objects.Update;

import CallMatches.Commands;

/**
 * This is a sample class to execute scheduler on specific date based on <code>java.util.Calendar</code>. Over here,
 * <code>executor</code> is a runnable which run on everyday basis from 8:00 AM to 5:00 PM.
 * 
 * @author Tomas Mali
 */
public class Scheduler {

	public static Commands mySianConnect = null;
	public static Update update = null;

	static String attention = "\u26a0\ufe0f";
	static String ok_check = "\u2714\ufe0f";

	public static Long mio = (long) 145645559;
	private static String icon_link = "\ud83d\udcce";
	private static String icon_clock = "\u231a\ufe0f";

	public Scheduler(Commands instance) {
		mySianConnect = instance;
	}

}