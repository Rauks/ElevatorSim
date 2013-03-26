/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aihm.model;

/**
 *
 * @author Karl
 */
public class Floor {
    private int value;
    
    public Floor(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Floor other = (Floor) obj;
        if (this.value != other.value) {
            return false;
        }
        return true;
    }
}
