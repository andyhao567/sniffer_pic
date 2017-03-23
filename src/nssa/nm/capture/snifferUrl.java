package nssa.nm.capture;

import java.awt.datatransfer.StringSelection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

    public class snifferUrl {
    	
    	public static BlockingQueue<String> requesturl = new LinkedBlockingQueue<String>();
	    public static Lock urlLock = new ReentrantLock();
	    public static Logger mLogger = Logger.getAnonymousLogger();
	
	
        public static void main(String args[]){
        	ClassicPcap cPcap = new ClassicPcap(requesturl, urlLock, mLogger);
        	cPcap.start();
        	
        }
}
