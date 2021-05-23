import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Player implements Serializable {
    public SystemStatus systemStatus;
    private int playerId;
    private String name, lastName;
    private LocalDate dateOfBirth;
    private int number;
    private HashSet<String> position;
    private String status;
    private int timesNoStop;

    public Player(int playerId){
        this.playerId = playerId;
        systemStatus = new SystemStatus();
    }

    public Player(int playerId, String name, String lastName, LocalDate dateOfBirth, int number, HashSet<String> position, String status) {
        this.systemStatus = new SystemStatus(status);
        this.playerId = playerId;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.number = number;
        this.position = position;
        this.status = status;
        this.timesNoStop = 0;
    }


    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPosition() {
        String stringReturn = "";
        if (this.position.size() == 0) {// Never could happend, but is a cotnrol
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

    public void setPosition(HashSet<String> position) {
        this.position = position;
    }

    public void setPosition(String position) {
        if (this.position == null) {
            this.position = new HashSet<String>(4);
        }
        this.position.add(position);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimesNoStop() {
        return timesNoStop;
    }

    public void resetTimeNoStop() {
        this.timesNoStop = 0;
    }
    public void addTimesNoStop() {
        this.timesNoStop++;
    }


    @Override
    public String toString() {
        return "Player System Status ("+ this.systemStatus.getStatus()+")---\n" +
                "Player ID: " + playerId + "\n" +
                "name: " + name + "\n" +
                "lastName: " + lastName + "\n" +
                "date of birth: " + dateOfBirth + "\n" +
                "number: " + number + "\n" +
                "position: " + position + "\n" +
                "status: " + status + "\n" +
                "----------------------------------\n";
    }

    public boolean equals(Object obj){
        if (!(obj instanceof Player)) {
            return false;
        } else {
            return  ((Player) obj).systemStatus.equals(systemStatus) &&
                    ((Player) obj).playerId == playerId &&
                    ((Player) obj).name.equals(name) &&
                    ((Player) obj).lastName.equals(lastName) &&
                    ((Player) obj).dateOfBirth.equals(dateOfBirth) &&
                    ((Player) obj).number == number &&
                    ((Player) obj).position == position &&
                    ((Player) obj).status.equals(status);
        }
    }
}
