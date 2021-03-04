import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiplicationTableUtility {

    public static final long EXTRA_TIME = 10000;
    static final String TITLE_TXT = "TABLICZKA MNOŻENIA";
    static final String TASK_TXT = "zadanie";
    static final String START_TXT = "\n   Wciśnij START żeby zacząć";
    static final String BRAVO_TXT = "Brawo ";
    static final String END_TXT = "\n\n  ...::KONIEC::...\n";
    static final String SCORE_TXT = " Twoja ocena to ";
    static final String WRONG_ANSWER1_TXT = "Czy na pewno ";
    static final String WRONG_ANSWER2_TXT = " ?!  popraw się, stać Cię na więcej; + 10 sekund do wyniku\n";
    static final String START_LOG_TXT = "\n\n\n..::START::..\n\nPodaj rozwiązanie i wciśnij ENTER\n";
    static final String LOAD_PLAYER_MASSAGE = "Wybierz gracza z listy";
    static final String LOAD_PLAYER_TITLE = "Kto gra?";
    static final ImageIcon ICON = new ImageIcon("src/main/resources/icon.jpeg");
    static final String playerFilePath = "src/main/resources/players.data";
    static final String START_BUTTON = "START";
    static final int NUMBER_OF_TASK = 20;
    static final Object[] NEW_PLAYER_AND_LOAD_PLAYER = new Object[]{"Nowy Gracz", "Wczytaj Gracza"};
    static final Object[] LOAD_PLAYER = new Object[]{"Nowy Gracz"};
    static final String NEW_PLAYER_MASSAGE = "Podaj swoje imię";
    static final String NEW_PLAYER_TITLE = "Nowy gracz";
    static final String NEW_PLAYER_DEFAULT_NAME = "nowy gracz";
    static final String GET_PLAYER_MASSAGE = "Kto gra?";
    static final String GET_PLAYER_TITLE = "Kto gra?";

    private List<Player> players;

    public MultiplicationTableUtility() {
        this.players = new ArrayList<>();
        File f = new File(playerFilePath);
        if (f.exists()) getPLayersList();
    }


    public Player getPlayer(JFrame frame) {
        Player player = null;
        Object[] choices;
        File f = new File(playerFilePath);
        if (f.exists()) choices = NEW_PLAYER_AND_LOAD_PLAYER;
        else choices = LOAD_PLAYER;
        int choice = JOptionPane.showOptionDialog(JOptionPane.getDesktopPaneForComponent(frame),
                GET_PLAYER_MASSAGE, GET_PLAYER_TITLE,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                ICON, choices, choices[0]);
        if (choice == JOptionPane.YES_NO_OPTION) player = getNewPlayerData();
        else if (choice == JOptionPane.CLOSED_OPTION) System.exit(0);
        else player = loadPlayerData(frame);
        return player;
    }

    private Player loadPlayerData(JFrame frame) {
        getPLayersList();
        Object[] choices = players.toArray();
        Object player = JOptionPane.showInputDialog(frame, LOAD_PLAYER_MASSAGE,
                LOAD_PLAYER_TITLE, JOptionPane.INFORMATION_MESSAGE,
                ICON, choices, choices[0]);
        System.out.println(player);
        return (Player) player;
    }

    public Player getNewPlayerData() {
        Player player = null;
        String newPlayerName = (String) JOptionPane.showInputDialog(JOptionPane.getRootFrame(),
                NEW_PLAYER_MASSAGE, NEW_PLAYER_TITLE,
                JOptionPane.INFORMATION_MESSAGE, ICON,
                null, NEW_PLAYER_DEFAULT_NAME);
        if (newPlayerName != null) {
            player = new Player(newPlayerName);
            addNewPlayer(player);
        }
        return player;
    }

    private void addNewPlayer(Player newPlayer) {
        players.add(newPlayer);
        savePlayersList(this.players);
    }

    void getPLayersList() {
        try {
            FileInputStream fileIn = new FileInputStream(playerFilePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            players = (List<Player>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    void savePlayersList(List<Player> players) {

        try {
            FileOutputStream fileOut = new FileOutputStream(MTU.playerFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(players);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void savePlayersData(Player player) {
        int index = players.indexOf(player);
        players.set(index, player);
        savePlayersList(players);
    }

    static Task getRandomTask(int taskNumber) {
        Random random = new Random();
        Task task = null;
        int x = random.nextInt(8) + 2;
        int y = random.nextInt(8) + 2;
        if (taskNumber % 2 == 0)
            task = new MultiplicationTask(x, y);
        else
            task = new DivisionTask(x, y);
        return task;
    }
}