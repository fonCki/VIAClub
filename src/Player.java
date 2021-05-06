public class Player {
    private String name, lastName;
    private int age, number;
    private String position;

    public Player(String name, String lastName, int age, int number, String position) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.number = number;
        this.position = position;
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

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player----------------------------\n" +
                "name: " + name + "\n" +
                "lastName: " + lastName + "\n" +
                "age: " + age + "\n" +
                "number: " + number +
                "position: " + position + "\n" +
                "----------------------------------";
    }

    public boolean equals(Object obj){
        if (!(obj instanceof Player)) {
            return false;
        } else {
            return ((Player) obj).name == this.name &&
                    ((Player) obj).lastName == this.lastName &&
                    ((Player) obj).age == this.age &&
                    ((Player) obj).number == number &&
                    ((Player) obj).position == position;
        }
    }
}
