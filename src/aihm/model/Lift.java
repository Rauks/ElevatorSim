/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm.model;

import aihm.LiftFrame;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karl
 */
public class Lift {
    private int nbFloors = 0;
    private Set<Floor> floorRequests;
    private Floor currentFloor;
    
    public Lift(int nbFloors){
        this.nbFloors = nbFloors;
        this.floorRequests = new HashSet<>();
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
    
    public void setCurrentFloor(int index) throws LiftException{
        this.checkFloorIndex(index);
        this.floorRequests.remove(new Floor(index));
        this.currentFloor = new Floor(index);
    }
    
    public int getCurrentFloor(){
        return this.currentFloor.getValue();
    }
}
