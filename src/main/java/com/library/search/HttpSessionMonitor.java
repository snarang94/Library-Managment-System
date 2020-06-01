package com.library.search;

import java.util.LinkedList;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.library.localStorage.CoverImageLoader;

public class HttpSessionMonitor implements Runnable {
	private LinkedList<HttpSession> sessions = null;
	private DBSearchController dbSearchController = null;
	private final int SLEEP_INTERVAL_MILLISECONDS = 1*60*1000;
	private final int ACCESS_INTERVAL_MILLISECONDS = 10*60*1000;
	private static final Logger logger = LogManager.getLogger(CoverImageLoader.class);

	public HttpSessionMonitor(LinkedList<HttpSession> sessions, DBSearchController dbSearchController) {
		this.sessions = sessions;
		this.dbSearchController = dbSearchController;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(SLEEP_INTERVAL_MILLISECONDS);
				long currentTime = System.currentTimeMillis();
				int numBeforeCleanup = sessions.size();
				while(!sessions.isEmpty()) {
					HttpSession session = sessions.getFirst();
					if(currentTime - session.getLastAccessedTime() >  ACCESS_INTERVAL_MILLISECONDS) {
						sessions.poll();
						session.invalidate();
						dbSearchController.clearSearch(session);
					} else {
						String message = "Number of actively searching users is " + sessions.size() + 
								". Number of removed inactive users is " + (numBeforeCleanup - sessions.size());
						logger.log(Level.INFO, message);
						break;
					}
				}
			}
			
		} catch (InterruptedException e) {
			logger.log(Level.FATAL,"Some thread has interrupted the current HttpSessionMonitors thread.",e);
		} catch (IllegalArgumentException e) {
			logger.log(Level.FATAL,"Negative value of milliseconds sleep interval is supplied.",e);
		}
	}
}
