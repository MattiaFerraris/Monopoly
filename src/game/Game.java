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
        tc.showTable();

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
            TableController.showAlert("Non è stato possibile salvare lo stato del gioco");
        }
    }

    public static Monopoly loadGame(String fileName) {
        Monopoly monopoly = null;
        try (FileInputStream file = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(file)) {
            monopoly = Monopoly.loadState(in);
        } catch (FileNotFoundException e) {
            TableController.showAlert("File non trovato");
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
                    TableController.showAlert(player.getName() + " HA PERSO!");

            Platform.runLater(() -> tc.updateBalances());

            int prevPosition = currentPlayer.getPosition();
            monopoly.movePlayer(currentPlayer);
            monopoly.showTable();
            tc.showTable();
            Box box = monopoly.getBox(currentPlayer);

            if (box instanceof Probability) {
                monopoly.useProbabilityCard(currentPlayer, ((Probability) box).getProbabilityCards());

            } else if (box instanceof Chance) {
                monopoly.useChanceCard(currentPlayer, ((Chance) box).getChanceCard());
            }

            if (box instanceof Property property) {
                if (property.getOwner() == null) { //Acquisto della proprietà
                    if (TableController.alertChoice("VUOI COMPRARE " + property.getName().toUpperCase() + "?", currentPlayer)) {
                        if (!monopoly.buyProperty(currentPlayer, property)){
                            monopoly.payPropertyFee(currentPlayer, property);
                            TableController.showAlert("Non hai abbastanza soldi");
                        } else
                            TableController.showAlert(currentPlayer.getName() + " ha acquistato " + property.getName() + "!");
                    } else
                        monopoly.payPropertyFee(currentPlayer, property);

                } else if (!property.getOwner().equals(currentPlayer)) { //Pagamento tassa al proprietario
                    if(TableController.alertChoice("VUOI PROVARE A COMPRARE " + property.getName().toUpperCase() + " DA " + property.getOwner().getName().toUpperCase() + "?", currentPlayer))
                        if(TableController.alertChoice(property.getOwner().getName().toUpperCase() + " ACCETTA LA TUA OFFERTA?", property.getOwner()))
                            monopoly.buyProperty(currentPlayer, property);
                        else
                            monopoly.payPropertyFee(currentPlayer, property);
                    else
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
        Player nextPlayer = monopoly.getCurrentPlayer();
        Platform.runLater(() -> tc.showTurn("Turno di " + nextPlayer.getName()));
        Platform.runLater(() -> tc.updateBalances());
    }

}