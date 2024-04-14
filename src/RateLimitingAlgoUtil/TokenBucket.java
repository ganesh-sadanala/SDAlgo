package src.RateLimitingAlgoUtil;

/*
Token Bucket Algorithm:
A token bucket is a container that holds a specific number of tokens.
Tokens are added to the bucket at a fixed rate.
Each request consumes one or more tokens from the bucket.
If the bucket is empty, requests are denied until more tokens are available.
The bucket size determines the burst capacity,
while the token refill rate determines the overall rate limit.
* */
public class TokenBucket {
    private final long maxBucketSize;
    private final long refillRate;
    private long currentBucketSize;
    private long lastRefillTimestamp;

    public TokenBucket(long maxBucketSize, long refillRate){
        this.maxBucketSize=maxBucketSize;
        this.refillRate=refillRate;
        this.currentBucketSize=maxBucketSize;
        this.lastRefillTimestamp=System.currentTimeMillis();
    }

    public synchronized boolean allowRequest(int numOfTokens){
        refill();
        if(currentBucketSize >= numOfTokens){
            currentBucketSize-=numOfTokens;
            return true;
        }
        return false;
    }

    public void refill(){
        long currentTime=System.currentTimeMillis();
        long timeElpased=currentTime-lastRefillTimestamp;
        long tokenToadd=timeElpased*refillRate/1000;
        currentBucketSize=Math.min(currentBucketSize+tokenToadd, maxBucketSize);
        lastRefillTimestamp=currentTime;
    }
}