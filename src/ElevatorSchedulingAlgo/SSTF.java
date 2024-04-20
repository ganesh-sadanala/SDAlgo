package src.ElevatorSchedulingAlgo;

import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

// Shortest Seek Time First algorithm
// Greedy
// May cause starvation
public class SSTF {


    // Bruteforce
    /*
    List<Integer> requestedFloors;
    public int selectNextFloor(int currentFloor) {
        if (!requestedFloors.isEmpty()) {
            int nearestFloor = requestedFloors.get(0);
            int minDistance = Math.abs(nearestFloor - currentFloor);
            for (int floor : requestedFloors) {
                int distance = Math.abs(floor - currentFloor);
                if (distance < minDistance) {
                    nearestFloor = floor;
                    minDistance = distance;
                }
            }
            return nearestFloor;
        }
        return currentFloor;
    }
     */



    // Optimization, tradeoff: extra space(everytime create new Heap)
    public void requestFloor(int floor) {
        requestedFloors.add(floor);
    }

    private int currentFloor;
    private PriorityQueue<Integer> requestedFloors;
    private BlockingQueue<Integer> incomingRequests;

    boolean running;

    public SSTF() {
        this.currentFloor = 1;
        this.requestedFloors = new PriorityQueue<>(
                (a, b) -> Math.abs(a - currentFloor) - Math.abs(b - currentFloor)
        );
        this.incomingRequests=new LinkedBlockingDeque<>();
        running=true;
    }

    void addRequest(int floor){
        incomingRequests.offer(floor);
    }

    public void processRequests() {
        while(running) {
            while (!incomingRequests.isEmpty()) {
                int request = incomingRequests.poll();
                if (!requestedFloors.contains(request)) {
                    requestedFloors.offer(request);
                }
            }

            if (!requestedFloors.isEmpty()) {
                int nextFloor = requestedFloors.remove();
                moveToFloor(nextFloor);
                currentFloor = nextFloor;

                // Update the priorities of the remaining floors in the min-heap
                PriorityQueue<Integer> updatedFloors = new PriorityQueue<>(
                        (a, b) -> Math.abs(a - currentFloor) - Math.abs(b - currentFloor)
                );
                updatedFloors.addAll(requestedFloors);
                requestedFloors = updatedFloors;
            }

            // Sleep for a short interval to simulate elevator movement
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void moveToFloor(int floor) {
        System.out.println("Elevator moving from floor " + currentFloor + " to floor " + floor);
    }

    public void stopElevator() {
        running = false;
    }

    public static void main(String[] args) {
        SSTF elevator = new SSTF();

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

