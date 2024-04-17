package src.DesignQuestions.MultiThreadedLogger;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
The instructions state to design and implement an API or class to write information to a log file.
The API must support writing strings and integers to the log file and be able to open, close, and write to the log file.
Write operations may be requested from multiple threads simultaneously.

To summarize the bonus points:

Write performance is important. If not desirable to block other threads while writing to file,
explain how to address this concern and write code to prevent blocking multiple threads while
one thread is writing to the log.
Explain how to accommodate using the log API class to write concurrently to the same file
from different processes in the same computer, if time allows.

https://leetcode.com/discuss/interview-question/object-oriented-design/3636436/Microsoft-OA%3A-Design-implement-API
 */
public class Logger {

    private static Logger logger;
    BlockingQueue<String> queue;

    Writer writer;
    boolean running;

    private Logger(){
        queue=new ArrayBlockingQueue<>(1024);
        running=true;
        startWriterThread();
    }

    public static Logger getLogger(){
        if(logger==null) logger=new Logger();
        return logger;
    }

    public void log(String message){
        queue.add(message);
    }

    public void log(int message){
        queue.add(String.valueOf(message));
    }

    // daemon process
    public void startWriterThread(){
        Thread writerThread=new Thread(() -> {
            try{
                writer=new BufferedWriter(new FileWriter("log.txt", true));
                while(running || !queue.isEmpty()){
                    String message=queue.take();
                    writer.write(message+System.lineSeparator());
                }
            }catch (InterruptedException | IOException e){
                e.printStackTrace();
            } finally {
                try{
                    writer.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        writerThread.start();
    }

    public void cleanUp(){
        running=false;
    }
}
