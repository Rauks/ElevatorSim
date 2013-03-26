/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm.model;

import aihm.LiftFrame;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karl
 */
public class Lift {
    public enum Moves{UP, DOWN, STANDBY}
    
    private int nbFloors = 0;
    private Queue<Floor> floorRequests;
    private Floor currentFloor;
    
    public Lift(int nbFloors){
        this.nbFloors = nbFloors;
        this.floorRequests = new LinkedList<>();
        this.currentFloor = new Floor(0);
    }
    
    private void checkFloorIndex(int index) throws LiftException{
        if(index < 0){
            throw new LiftException("Floor out of bound");
        }
        if(index > this.nbFloors - 1){
            throw new LiftException("Floor out of bound");
        }
    }
    
    public void requestFloor(int index) throws LiftException{
        Logger.getLogger(LiftFrame.class.getName()).log(Level.INFO, "Request floor : {0}", index);
        this.checkFloorIndex(index);
        this.floorRequests.add(new Floor(index));
    }
    
    public boolean isRequested(int index) throws LiftException{
        this.checkFloorIndex(index);
        return this.floorRequests.contains(new Floor(index));
    }
    
    public boolean hasRequests(){
        return !this.floorRequests.isEmpty();
    }

    public int getNextFloor() throws LiftException{
        if(!this.hasRequests()){
            throw new LiftException("No floor request");
        }
        //Simple version, dequeue
        return this.floorRequests.peek().getValue();
    }
    
    public Moves getRequestedMove(){
        if(!this.hasRequests()){
            return Moves.STANDBY;
        }
        int currentIndex = this.getCurrentFloor();
        int nextIndex = 0;
        try {
            nextIndex = this.getNextFloor();
        } catch (LiftException ex) {
            Logger.getLogger(Lift.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(currentIndex == nextIndex){
            return Moves.STANDBY;
        }
        else if(currentIndex > nextIndex){
            return Moves.DOWN;
        }
        else{
            return Moves.UP;
        }
    }
    
    public void gotoNextFloor() throws LiftException{
        if(!this.hasRequests()){
            throw new LiftException("No floor request");
        }
        int nextIndex = this.floorRequests.poll().getValue();
        this.setCurrentFloor(nextIndex);
    }
    
    public void setCurrentFloor(int index) throws LiftException{
        this.checkFloorIndex(index);
        this.floorRequests.remove(new Floor(index));
        this.currentFloor = new Floor(index);
    }
    
    public int getCurrentFloor(){
        return this.currentFloor.getValue();
    }
}
