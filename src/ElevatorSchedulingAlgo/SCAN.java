package src.ElevatorSchedulingAlgo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SCAN{

    private Direction currentDirection;

    int currentFloor;

    int totalFloors;

    PriorityQueue<Integer> upRequestQueue;

    PriorityQueue<Integer> downRequestQueue;

    BlockingQueue<Integer> incomingRequests;

    boolean running;

    SCAN(int initialFloor, int totalFloors){
        this.currentDirection=Direction.UP;
        this.totalFloors=totalFloors;
        this.currentFloor=initialFloor;
        running=true;
        incomingRequests= new LinkedBlockingQueue<>();
        upRequestQueue=new PriorityQueue<>();
        downRequestQueue = new PriorityQueue<>((a, b) -> b - a);
    }


    public void addRequest(int floor) {
        incomingRequests.add(floor);
    }

    public int selectNextFloor(int currentFloor) {
        return 0;
    }

    public void processRequests(){
        while(running){
            // Process incoming requests
            while(!incomingRequests.isEmpty()){
                int request=incomingRequests.poll();
                if(request > currentFloor){
                    upRequestQueue.add(request);
                }else if(request < currentFloor){
                    downRequestQueue.add(request);
                }
            }

            // Process existing requests using SCAN
            if(currentDirection==Direction.UP){
                processUpRequests();
                if(upRequestQueue.isEmpty()) currentDirection=Direction.DOWN;
            }else{
                processDownRequests();
                if(downRequestQueue.isEmpty()) currentDirection=Direction.UP;
            }

            // Sleep for a short interval to simulate elevator movement
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopElevator() {
        running = false;
    }

    private enum Direction {
        UP,
        DOWN
    }

    public void processUpRequests(){
        while(!upRequestQueue.isEmpty() && upRequestQueue.peek() >= currentFloor){
            currentFloor = upRequestQueue.poll();
            System.out.println("Elevator stopped at floor: " + currentFloor);
        }
    }

    public void processDownRequests(){
        while(!downRequestQueue.isEmpty() && downRequestQueue.peek() <= currentFloor){
            currentFloor = downRequestQueue.poll();
            System.out.println("Elevator stopped at floor: " + currentFloor);
        }
    }


    public static void main(String[] args) {
        SCAN elevator = new SCAN(1, 10);

        // Start the elevator in a separate thread
        Thread elevatorThread = new Thread(elevator::processRequests);
        elevatorThread.start();

        // Simulate incoming requests
        elevator.addRequest(5);
        elevator.addRequest(3);

        // Wait for a short interval
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Simulate more incoming requests
        elevator.addRequest(7);
        elevator.addRequest(2);
        elevator.addRequest(9);

        // Wait for a few seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop the elevator
        elevator.stopElevator();
    }
}
