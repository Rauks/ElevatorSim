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
        Floor request = new Floor(index);
        if(!this.floorRequests.contains(request)){
            this.floorRequests.add(new Floor(index));
        }
    }
    
    public boolean isRequested(int index) throws LiftException{
        this.checkFloorIndex(index);
        return this.floorRequests.contains(new Floor(index));
    }
    
    public boolean hasRequests(){
        return !this.floorRequests.isEmpty();
    }

    public int getNextFloor(){
        if(this.hasRequests()){
            return this.floorRequests.peek().getValue();
        }
        return this.currentFloor.getValue();
    }
    
    public Moves getRequestedMove(){
        if(!this.hasRequests()){
            return Moves.STANDBY;
        }
        int currentIndex = this.getCurrentFloor();
        int nextIndex = this.getNextFloor();
        if(currentIndex == nextIndex){
            return Moves.STANDBY;
        }
        else if(currentIndex < nextIndex){
            return Moves.UP;
        }
        else{
            return Moves.DOWN;
        }
    }
    
    public void setCurrentFloor(int index) throws LiftException{
        this.checkFloorIndex(index);
        this.floorRequests.remove(new Floor(index));
        this.currentFloor = new Floor(index);
    }
    
    public int getCurrentFloor(){
        return this.currentFloor.getValue();
    }
    
    public int getNbFloors(){
        return this.nbFloors;
    }
}
