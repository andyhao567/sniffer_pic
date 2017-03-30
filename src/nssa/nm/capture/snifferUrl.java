package nssa.nm.capture;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

    public class snifferUrl {
    	
    	public static BlockingQueue<String> requesturl = new LinkedBlockingQueue<String>();
	    public static Lock urlLock = new ReentrantLock();
	    public static Logger mLogger = Logger.getAnonymousLogger();
	    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	
        public static void main(String args[]){
        	
        	long delay = 1*60*1000;
        	long internal_time = 3*60*1000;
        	
        	ClassicPcap cPcap = new ClassicPcap(requesturl, urlLock, mLogger);
        	cPcap.start();
        	
        	executor.scheduleWithFixedDelay(new parsePic(mLogger), delay, internal_time, TimeUnit.MILLISECONDS); 
        	
        }
}
