package src.RateLimitingAlgoUtil.Questions;

import src.RateLimitingAlgoUtil.SlidingWindow;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/*
Whenever you expose a web service / api endpoint, you need to implement a rate limiter
to prevent abuse of the service (DOS attacks).

Implement a RateLimiter Class with an isAllow method. Every request comes in with a unique clientID,
deny a request if that client has made more than 100 requests in the past second.

Reference: https://leetcode.com/discuss/interview-question/system-design/124558/Implement-a-Rate-Limiter
* */
public class RateLimitingPerUser {

    // requestLimit
    public static final int MAX_REQUESTS_PER_SECOND=100;

    // window size
    public static final long WINDOW_SIZE= TimeUnit.SECONDS.toMillis(1);

    private final Map<String, SlidingWindow> clientWindows;

    public RateLimitingPerUser(){
        clientWindows=new ConcurrentHashMap<>();
    }

    public boolean isAllow(String clientId){
        SlidingWindow window = clientWindows.computeIfAbsent(clientId, key -> new SlidingWindow(WINDOW_SIZE, MAX_REQUESTS_PER_SECOND));
        return window.allowRequest();
    }
}
