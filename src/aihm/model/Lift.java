/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Karl
 */
public class Lift {
    private int nbFloors = 0;
    private Set<Floor> floorRequests;
    
    
    public Lift(int nbFloors){
        this.nbFloors = nbFloors;
        this.floorRequests = new HashSet<>();
    }
    
    public void requestFloor(int index) throws LiftException{
        if(index < 0){
            throw new LiftException("Floor out of bound");
        }
        if(index > this.nbFloors - 1){
            throw new LiftException("Floor out of bound");
        }
        this.floorRequests.add(new Floor(index));
    }
    
    public boolean isRequested(int index){
        return this.floorRequests.contains(new Floor(index));
    }
    
    public void onFloor(int index){
        this.floorRequests.remove(new Floor(index));
    }
}
