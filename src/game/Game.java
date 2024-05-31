package game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.Player;
import table.*;
import table.Box;
import utility.ScannerUtilities;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Game extends Application {

    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Resources/game/StartScreen.fxml")));
            Parent root = loader.load();
            Scene startScreen = new Scene(root);
            stage.setScene(startScreen);
            stage.show();

            StartController controller = loader.getController();
            controller.uploadGamesToButton(controller.uploadGameMenuButton);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void playGame(Monopoly monopoly, Player[] players, TableController tc) {
        if (monopoly == null)
            monopoly = new Monopoly(new ArrayList<>(Arrays.asList(players)));

        Player currentPlayer = monopoly.getCurrentPlayer();
        Platform.runLater(() -> tc.showTurn("Turno di " + currentPlayer.getName()));
        Platform.runLater(() -> tc.updateBalances());

        monopoly.showTable();

    }

    public static void saveGame(Monopoly monopoly) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = builder.appendPattern("yyyy-MM-dd_HH-mm-ss").toFormatter();
        String fileName = "saved_games" + File.separator + "game_" + LocalDateTime.now().format(formatter) + ".obj";
        try (FileOutputStream file = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(file)) {
            out.writeObject(monopoly);
            TableController.showAlert("PARTITA SALVATA");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Non è stato possibile salvare lo stato del gioco");
        }
    }

    public static Monopoly loadGame(String fileName) {
        Monopoly monopoly = null;
        try (FileInputStream file = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(file)) {
            monopoly = Monopoly.loadState(in);
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato");
        } catch (IOException | ClassNotFoundException e) {
            TableController.showAlert("Non è stato possibile caricare lo stato del gioco");
        }
        return monopoly;
    }

    public static String[] getSavedGames() {
        File folder = new File("saved_games");
        File[] files = folder.listFiles();
        if (files == null)
            return new String[0];
        ArrayList<String> filesList = new ArrayList<>();
        for (File file : files)
            filesList.add(file.getName());
        return filesList.toArray(new String[0]);
    }

    public static void turn(Monopoly monopoly, TableController tc) {
        Player currentPlayer = monopoly.getCurrentPlayer();

        if (!monopoly.isGameOver()) {

            ArrayList<Player> lostPlayers = monopoly.getLostPlayers();

            if (!lostPlayers.isEmpty())
                for (Player player : lostPlayers)
                    TableController.showAlert(player.getName() + " ha perso!");


            String s = currentPlayer.getColoredName();
            System.out.println(s);
            Platform.runLater(() -> tc.updateBalances());

            int prevPosition = currentPlayer.getPosition();
            monopoly.movePlayer(currentPlayer);
            monopoly.showTable();
            Box box = monopoly.getBox(currentPlayer);

            if (box instanceof Probability) {
                ((Probability) box).getProbabilityCards();

            } else if (box instanceof Chance) {

                ((Chance) box).getChanceCard();
            }

            if (box instanceof Property property) {
                if (property.getOwner() == null) { //Acquisto della proprietà

                    if (TableController.alertChoice("VUOI COMPRARE " + property.getName().toUpperCase() + "?", currentPlayer)) {

                        if (!monopoly.buyProperty(currentPlayer, property))
                            monopoly.payPropertyFee(currentPlayer, property);
                    } else
                        monopoly.payPropertyFee(currentPlayer, property);

                } else if (!property.getOwner().equals(currentPlayer)) { //Pagamento tassa al proprietario
                    monopoly.payPropertyFee(currentPlayer, property);

                } else if (monopoly.hasPlayerAllSameColorProperties(currentPlayer, property)) { //Costruzione di case e hotel
                    BuildableProperty buildableProperty = (BuildableProperty) property;
                    if (buildableProperty.getHousesCount() < 4 && TableController.alertChoice("VUOI COSTRUIRE UNA CASA IN " + property.getName().toUpperCase() + "?", currentPlayer))
                        monopoly.buildHouse(currentPlayer, buildableProperty);
                    else if (buildableProperty.getHousesCount() == 4 && buildableProperty.getHotelsCount() == 0 && TableController.alertChoice("VUOI COSTRUIRE UN HOTEL?", currentPlayer))
                        monopoly.buildHotel(currentPlayer, buildableProperty);
                }
            } else
                monopoly.updateBalance(prevPosition, currentPlayer.getPosition(), currentPlayer);
        }
        monopoly.nextTurn();
        currentPlayer = monopoly.getCurrentPlayer();
        final Player finalCurrentPlayer = currentPlayer;
        Platform.runLater(() -> tc.showTurn("Turno di " + finalCurrentPlayer.getName())); //il lambda richiede variabili final in questo caso (altrimenti il compilatore segna errore se si usa 'currentiPlayer')
    }

}