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
    public enum States{CLOSED, OPENED, OPENING, CLOSING}
            
    private int nbFloors = 0;
    private Queue<Floor> floorRequests;
    private Floor currentFloor;
    private States state;
    
    public Lift(int nbFloors){
        this.nbFloors = nbFloors;
        this.floorRequests = new LinkedList<>();
        this.currentFloor = new Floor(0);
        this.state = States.CLOSED;
    }
    
    /**
     * Check if the index is in floors bounds.
     * 
     * @param index The floor index to check.
     * @throws LiftException Throw this exception if the index of out of floors bounds.
     */
    private void checkFloorIndex(int index) throws LiftException{
        if(index < 0){
            throw new LiftException("Floor out of bound");
        }
        if(index > this.nbFloors - 1){
            throw new LiftException("Floor out of bound");
        }
    }
    
    /**
     * Add a floor index to the requests queue of the lift.
     * 
     * @param index The floor index.
     * @throws LiftException Throw this exception if the index of out of floors bounds.
     */
    public void requestFloor(int index) throws LiftException{
        Logger.getLogger(LiftFrame.class.getName()).log(Level.INFO, "Request floor : {0}", index);
        this.checkFloorIndex(index);
        Floor request = new Floor(index);
        if(!this.floorRequests.contains(request)){
            this.floorRequests.add(new Floor(index));
        }
    }
    /**
     * Check if the floor index is in requests queue of the lift.
     * 
     * @param index The floor index.
     * @return True if the floor index is in requests queue of the lift.
     * @throws LiftException Throw this exception if the index of out of floors bounds.
     */
    public boolean isFloorInRequest(int index) throws LiftException{
        this.checkFloorIndex(index);
        return this.floorRequests.contains(new Floor(index));
    }
    
    /**
     * Check if the lift has requests.
     * 
     * @return  True if the laft has requests in his queue.
     */
    private boolean hasRequests(){
        return !this.floorRequests.isEmpty();
    }

    /**
     * Return the next targeted floor in requests queue.
     * 
     * @return The floor index or -1 if no floor is requested.
     */
    private int getNextFloorTarget(){
        if(this.hasRequests()){
            return this.floorRequests.peek().getValue();
        }
        return -1;
    }
    
    /**
     * Return the next floor requested in the way to reach the targeted floor.
     * 
     * @return The floor index or -1 if no floor is requested.
     */
    public int getNextFloorStop(){
        try {
            int target = this.getNextFloorTarget();
            int current = this.getCurrentFloor();
            switch(this.getRequestedMove()){
                case STANDBY:
                    if(this.isFloorInRequest(current)){
                        return current;
                    }
                    else{
                        return -1;
                    }
                case UP:
                    for (int i = this.getCurrentFloor(); i <= target; i++) {
                        if(this.isFloorInRequest(i)){
                            return i;
                        }
                    }
                case DOWN:
                    for (int i = this.getCurrentFloor(); i >= target; i--) {
                        if(this.isFloorInRequest(i)){
                            return i;
                        }
                    }
            }
        } catch (LiftException ex) {
            Logger.getLogger(Lift.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    /**
     * Calculate the move to do in order to reach  the next floor in requests queue
     * 
     * @return The move to do.
     * @throws LiftException In case of unauthorized state change.
     */
    public Moves getRequestedMove() throws LiftException{
        if(this.state != States.CLOSED){
            throw new LiftException("Doors must be in CLOSED state to change the current floor");
        }
        if(!this.hasRequests()){
            return Moves.STANDBY;
        }
        int currentIndex = this.getCurrentFloor();
        int nextIndex = this.getNextFloorTarget();
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
    
    /**
     * Change the current floor of the lift. The lift state must be CLOSED.
     * 
     * @param index The floor index.
     * @throws LiftException Throw this exception if the index of out of floors bounds or in case of unauthorized state change.
     */
    public void setCurrentFloor(int index) throws LiftException{
        if(this.state != States.CLOSED){
            throw new LiftException("Doors must be in CLOSED state to change the current floor");
        }
        this.checkFloorIndex(index);
        this.floorRequests.remove(new Floor(index));
        this.currentFloor = new Floor(index);
    }
    
    /**
     * Get the current floor index.
     * 
     * @return The vurrent floor index. 
     */
    public int getCurrentFloor(){
        return this.currentFloor.getValue();
    }
    
    /**
     * Get the number of floors.
     * 
     * @return The number of floors.
     */
    public int getNbFloors(){
        return this.nbFloors;
    }
    
    /**
     * Change the state of the lift to OPENING. The state of the lift must be CLOSED or CLOSING.
     * 
     * @throws LiftException In case of unauthorized state change.
     */
    public void requestDoorsOpening() throws LiftException{
        if(this.state != States.CLOSED && this.state != States.CLOSING){
            throw new LiftException("Doors must be in CLOSED or CLOSING state before to request them to OPENING");
        }
        this.floorRequests.remove(new Floor(this.getCurrentFloor()));
        this.state = States.OPENING;
    }
    
    /**
     * Change the state of the lift to CLOSING. The state of the lift must be OPENED or OPENING.
     * 
     * @throws LiftException In case of unauthorized state change.
     */
    public void requestDoorsClosing() throws LiftException{
        if(this.state != States.OPENED && this.state != States.OPENING){
            throw new LiftException("Doors must be in OPENED or OPENING state before to request them to CLOSING");
        }
        this.state = States.CLOSING;
    }
    
    /**
     * Change the state of the lift to OPENED. The state of the lift must be OPENING.
     * 
     * @throws LiftException In case of unauthorized state change.
     */
    public void setDoorsOpened() throws LiftException{
        if(this.state != States.OPENING){
            throw new LiftException("Doors must be in OPENING state before to set them to OPENED");
        }
        this.state = States.OPENED;
    }
    
    /**
     * Change the state of the lift to CLOSED. The state of the lift must be CLOSING.
     * 
     * @throws LiftException In case of unauthorized state change.
     */
    public void setDoorsClosed() throws LiftException{
        if(this.state != States.CLOSING){
            throw new LiftException("Doors must be in CLOSING state before to set them to CLOSED");
        }
        this.state = States.CLOSED;
    }
    
    /**
     * Get the current lift state.
     * 
     * @return The current state.
     */
    public States getState(){
        return this.state;
    }
}
