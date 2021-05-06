
public class TestClasses {
    public static void main(String[] args) {
        Player playerOne = new Player("Hugo", "Broglio", 37, 9, "Central");
        Player playerTwo = new Player("Mariano", "Cola", 38, 10, "forward");
        Player playerThree = new Player("Jose", "Pasuccho", 45, 5,"media");
        PlayerList playerList = new PlayerList();
        playerList.add(playerOne);
        playerList.add(playerTwo);
        playerList.add(playerThree);

        for (Player player: playerList.getPlayersList()) {
            System.out.println(player);
        }


    }
}
