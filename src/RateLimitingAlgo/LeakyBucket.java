package src.RateLimitingAlgo;

/*
Enforces a strict rate limit without allowing bursts.
Suitable when you want to maintain a constant rate of processing requests.
Simpler to implement compared to the token bucket algorithm.
Discards requests when the bucket is full, which may lead to dropped requests during high traffic.
Provides a more rigid rate limiting experience.
*/
public class LeakyBucket {

    // total/maxsize or capacity of bucket
    private final long capacity;

    // processing rate (constant)
    private final long leakRate;

    // initially bucket is empty
    private long currentBucketSize;

    //
    private long lastLeakTimestamp;

    LeakyBucket(long capacity, long leakRate){
        this.capacity=capacity;
        this.leakRate=leakRate;
        currentBucketSize=0;
        this.lastLeakTimestamp=System.currentTimeMillis();
    }

    public synchronized boolean allowRequest(){
        leak();
        if(currentBucketSize < capacity){
            currentBucketSize++;
            return true;
        }
        return false;
    }

    private void leak(){
        long currentTime=System.currentTimeMillis();
        long elapsedTime=currentTime-lastLeakTimestamp;
        long leakedRequests=elapsedTime*leakRate/1000;
        currentBucketSize=Math.max(currentBucketSize-leakedRequests, 0);
        lastLeakTimestamp=currentTime;
    }
}
