package src.RateLimitingAlgo;

import java.util.LinkedList;
import java.util.Queue;

/*
Corrects the flaw involved in the fixed window rate limiting.

Steps:
Provides more precise rate limiting compared to the fixed window algorithm.
Allows for bursts within the sliding window, up to the rate limit.
Requires storing the request count for the current window and discarding old requests.
Can be more complex to implement compared to the fixed window algorithm.
Suitable when you need more accurate rate limiting.
*/
public class SlidingWindow {
    // tracks the time
    private final long windowSize;

    // maximum request limit in window size
    private final int requestLimit;

    // stores the timestamps for each request
    private final Queue<Long> requestTimestamps;

    public SlidingWindow(long windowSize, int requestLimit){
        this.windowSize=windowSize;
        this.requestLimit=requestLimit;
        this.requestTimestamps=new LinkedList<>();
    }

    public synchronized boolean allowRequest(){
        long currentTime=System.currentTimeMillis();
        while(!requestTimestamps.isEmpty() && currentTime-requestTimestamps.peek()>windowSize){
            requestTimestamps.poll();
        }
        if(requestTimestamps.size() < requestLimit){
            requestTimestamps.offer(currentTime);
            return true;
        }
        return false;
    }
}
