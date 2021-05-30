package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;

/**
 * This class represent a Player
 * @author @alfonsoridao
 * @version 3.1
 */

public class Player implements Serializable {
    public SystemStatus systemStatus;
    private int playerID;
    private String name, lastName;
    private LocalDate dateOfBirth;
    private int number;
    private HashSet<String> position;
    private String status;
    private int timesNoStop;

    /**
     * One parameter constructor, the ID must be passed to create de player.
     * The plauerID is the position of the player in the playerList.
     * @param playerID the unique ID.
     */
    public Player(int playerID){
        this.playerID = playerID;
        systemStatus = new SystemStatus();
    }

    /**
     * Getting the Unique PlayerID.
     * @return and integer with the ID.
     */
    public int getPlayerId() {
        return playerID;
    }

    /**
     * Getting the name of the player.
     * @return a String with the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Setting the name of the player.
     * @param name the name of the player.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getting the last name of the player.
     * @return a String with the name.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setting the last name of the player.
     * @param lastName the last name of the player.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getting the birthday  of the player.
     * @return a LocalDate object with the date of birth.
     */
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    /**
     * Setting the date of birth of the player.
     * @param dateOfBirth the date of birth of the player.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Getting the position number of the player.
     * @return a int with the number.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Setting the position number of the player.
     * @param number the new position number.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Return a string with the position trained for the player.
     * The position could be between 1 and 4, ([Goalkeeper, Defender, Midfielder, Forward])
     * and the function returns the string position in a tidy way.
     * @return a String with the Position.
     */
    public String getPosition() {
        String stringReturn = "";
        if (this.position.size() == 0) { // It should not happend never.
            return stringReturn;
        } else if (this.position.size() == 1) {
          Iterator iterator = this.position.iterator();
          stringReturn += (iterator.next());
        } else {
            String tempString = "";
            for (String element: this.position){
                tempString += element + ", ";
            }
            stringReturn= tempString.substring(0, tempString.length() - 2);
        }
        return stringReturn;
    }

    /**
     * Setting the position trained of a player.
     * @param position a HashSet with positions between [1..4]
     */
    public void setPosition(HashSet<String> position) {
        this.position = position;
    }

    /**
     * Add a new position trained to the position set.
     * @param position a String with one position.
     */
    public void setPosition(String position) {
        if (this.position == null) {
            this.position = new HashSet<String>(4);
        }
        this.position.add(position);
    }


    /**
     * Getting the unique status of the player.
     *it could be ([Available, Unavailable, Suspended, Injured])
     * @return a String with the Status.
     */
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    /**Getting the time that the player has been playing without stop.
     * @return an int with the number.
     */
    public int getTimesNoStop() {
        return timesNoStop;
    }

    /**
     * Reset the times that the player have been playing without stop to cero.
     */
    public void resetTimeNoStop() {
        this.timesNoStop = 0;
    }

    /**
     * Increase in 1 the times that the player have been playing without stop.
     */
    public void addTimesNoStop() {
        this.timesNoStop++;
    }


    /**
     * Print a string in a nice way.
     * @return a String with all the object information.
     */
    public String toString() {
        return "Player System Status ("+ this.systemStatus.getStatus()+")---\n" +
                "Player ID: " + playerID + "\n" +
                "name: " + name + "\n" +
                "lastName: " + lastName + "\n" +
                "date of birth: " + dateOfBirth + "\n" +
                "number: " + number + "\n" +
                "position: " + position + "\n" +
                "status: " + status + "\n" +
                "----------------------------------\n";
    }

    /**
     * Compare if all the fields in a object are equals.
     * @param obj the object to compare.
     * @return boolean response.
     */
    public boolean equals(Object obj){
        if (!(obj instanceof Player)) {
            return false;
        } else {
            return  ((Player) obj).systemStatus.equals(systemStatus) &&
                    ((Player) obj).playerID == playerID &&
                    ((Player) obj).name.equals(name) &&
                    ((Player) obj).lastName.equals(lastName) &&
                    ((Player) obj).dateOfBirth.equals(dateOfBirth) &&
                    ((Player) obj).number == number &&
                    ((Player) obj).position == position &&
                    ((Player) obj).status.equals(status);
        }
    }
}
