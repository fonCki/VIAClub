package model;

import java.io.Serializable;

/**
 * This class represent the Status of a Player.
 * The status could be Available, Unavailable or Deleted.
 * @author @alfonsoridao
 * @version 3.1
 */

public class SystemStatus implements Serializable {
    private boolean deleted;
    private boolean available;
    private boolean unavailable;

    /**
     * No parameter constructor. Initialize the status as Available as default.
     */
    public SystemStatus() {
        this.available = true;
        this.unavailable = !(this.available);
        this.deleted = !(this.available);
    }

    /**
     * One parameter constructor. Set the status given as a parameter
     * @param status a String with the status.
     */
    public SystemStatus(String status) {
        this.setStatus(status);
    }

    /**
     * Setting the status to available.
     */
    public void setAvailable() {
        this.available = true;
        this.unavailable = !(this.available);
        this.deleted = !(this.available);
    }

    /**
     * Setting the status to Unavailable.
     */
    public void setUnavailable() {
        this.unavailable = true;
        this.available = !(this.unavailable);
        this.deleted = !(this.unavailable);
    }

    /**
     * Setting the status to deleted.
     */
    public void setDeleted() {
        this.deleted = true;
        this.unavailable = !(this.deleted);
        this.available = !(this.deleted);
    }

    /**
     * Apply the status to the status given as an argument.
     * @param status an array with the status.
     */
    public void setStatus(String status) {
        switch (status.toLowerCase()) {
            case "unavailable" :
            case "injured" :
            case "suspended" : {
                this.setUnavailable();
                break;
            }
            case "deleted" : {
                this.setDeleted();
                break;
            }
            default: setAvailable();
        }
    }

    /**
     * return true, if the status is available
     * @return a boolean option
     */
    public boolean isAvailable() {
        return this.available;
    }

    /**
     * return true, if the status is deleted
     * @return a boolean option
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    /**
     * return true, if the status is unavailable
     * @return a boolean option
     */
    public boolean isUnavailable() {
        return this.unavailable;
    }

    /**
     * return a String with the status of the player.
     * @return a String.
     */
    public String getStatus() {
        if (this.available) return "Available";
        if (this.unavailable) return "Unavailable";
        if (this.deleted) return "Deleted";
        return "System Status Error";
    }

    /**
     * Print the String in with the status.
     * @return a String.
     */
    public String toString() {
        return getStatus();
    }

    /**
     * Compare if all the fields in a object are equals.
     * @param obj the object to compare.
     * @return boolean response.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof SystemStatus)) {
            return false;
        } else {
            return ((SystemStatus) obj).deleted == this.deleted &&
                    ((SystemStatus) obj).available == this.available &&
                    ((SystemStatus) obj).unavailable == this.unavailable;
        }
    }

}
