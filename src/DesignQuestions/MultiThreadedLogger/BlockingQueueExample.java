package src.DesignQuestions.MultiThreadedLogger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*

What is BlockingQueue?
BlockingQueue is an interface in Java that represents a queue that is thread-safe and supports operations
that wait for the queue to become non-empty when retrieving an element and wait for space to become available
 in the queue when storing an element. It is part of the java.util.concurrent package.

When to use BlockingQueue?
BlockingQueue is used in scenarios where you need to implement a producer-consumer pattern,
where one or more threads (producers) produce elements and one or more threads (consumers) consume those elements.
The queue acts as a buffer between the producers and consumers, allowing them to work independently
and at different speeds.

Why is BlockingQueue used in the Logger example?
In the Logger example, BlockingQueue is used to handle concurrent logging requests from multiple threads.
The log methods (producers) add log messages to the queue, while a separate writer thread (consumer)
retrieves messages from the queue and writes them to the log file. This allows the logging operations
to be non-blocking for the calling threads, as they can quickly add messages to the queue and continue
their execution without waiting for the messages to be written to the file.

BlockingQueue Methods
BlockingQueue provides several methods for adding and removing elements from the queue.
Some commonly used methods are:

put(E element): Inserts the specified element into the queue, waiting if necessary for space to become available.
take(): Retrieves and removes the head of the queue, waiting if necessary until an element becomes available.
offer(E element): Inserts the specified element into the queue if it is possible to do so immediately
                  without violating capacity restrictions.
poll(): Retrieves and removes the head of the queue, or returns null if the queue is empty.
 */
public class BlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue=new ArrayBlockingQueue<>(5);

        // Producer Thread
        Thread producerThread=new Thread(() -> {
           for(int i=1;i<=10;i++){
               try{
                   queue.put(i);
                   System.out.println("Produced: " + i);
               }catch(InterruptedException e){
                   e.printStackTrace();
               }
           }
        });

        // Consumer Thread
        Thread consumerThread=new Thread(() -> {
           while(true){
               try{
                   int item = queue.take();
                   System.out.println("Consumed: " + item);
               }catch (InterruptedException e){
                   e.printStackTrace();
               }
           }
        });
        producerThread.start();
        consumerThread.start();
    }
}

/*
Exercises
1.Modify the above example to have multiple producer threads and multiple consumer threads.
Observe how the BlockingQueue handles concurrent access.
2.Implement a simple task scheduler using BlockingQueue.
Create a Task class with a run method and a TaskScheduler class that accepts tasks and executes
them using a fixed number of worker threads.
3.Create a file processing application where multiple threads read lines from different
files and add them to a BlockingQueue. Have a separate thread process the lines from the queue
and perform some operation on each line (e.g., counting words, filtering, etc.).
* */
