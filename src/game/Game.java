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
import java.util.Random;

/*
FUNZIONALITA AGGIUNTE:
- Salvataggio e caricamento di una partita
- Compravendita di proprietà (solo se il giocatore si trova sulla proprietà di un altro giocatore
- JavaFX per la gestione dell'interfaccia grafica
 */

public class Game extends Application {

    //PER DEMO
    static int[] positionsToMove;
    static int positionPointer = 0;

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
        //PER DEMO
        if (args.length > 0)
            if (args[0].equals("demo"))
                positionsToMove = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
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

            //PER DEMO
            int prevPosition = currentPlayer.getPosition();
            int[] diceNumbers;
            Random random = new Random();
            if(positionsToMove != null){
                int position = positionsToMove[positionPointer++];
                monopoly.move(currentPlayer, position);
                int firstDice;
                do{
                    firstDice = random.nextInt(1, position>6?6:position);
                }while (position-firstDice>6);
                diceNumbers = new int[]{firstDice, position-firstDice};
            }
            else
                diceNumbers = monopoly.movePlayer(currentPlayer);
            //FINE PARTE DEMO
            Platform.runLater(() -> tc.showDice(diceNumbers[0], diceNumbers[1]));
            monopoly.showTable();
            tc.showTable();
            Box box = monopoly.getBox(currentPlayer);

            if (box instanceof Event) {
                Box tempBox = monopoly.useCard(currentPlayer, ((Event) box).getCard());
                if (tempBox != null)
                    box = tempBox;
            }
            if (box instanceof Property property) {
                if (property.getOwner() == null) { //Acquisto della proprietà
                    if (TableController.alertChoice("VUOI COMPRARE " + property.getName().toUpperCase() + " AL PREZZO DI " + property.getPrice() + "?", currentPlayer)) {
                        if (!monopoly.buyProperty(currentPlayer, property)){
                            monopoly.payPropertyFee(currentPlayer, property);
                            TableController.showAlert("Non hai abbastanza soldi");
                        } else
                            TableController.showAlert(currentPlayer.getName() + " ha acquistato " + property.getName() + "!");
                    } else
                        monopoly.payPropertyFee(currentPlayer, property);

                } else if (!property.getOwner().equals(currentPlayer)) { //Pagamento tassa al proprietario
                    if(TableController.alertChoice("VUOI PROVARE A COMPRARE " + property.getName().toUpperCase() + " DA " + property.getOwner().getName().toUpperCase() + " al prezzo di " + property.getPrice() + "?", currentPlayer))
                        if(TableController.alertChoice(property.getOwner().getName().toUpperCase() + " ACCETTA LA TUA OFFERTA?", property.getOwner()))
                            monopoly.buyPropertyFromPlayer(currentPlayer, property.getOwner(), property);
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
            tc.showTable();
            monopoly.nextTurn();
            Player nextPlayer = monopoly.getCurrentPlayer();
            Platform.runLater(() -> tc.showTurn("Turno di " + nextPlayer.getName()));
            Platform.runLater(() -> tc.updateBalances());
        } else
            TableController.showAlert(monopoly.getWinner().getName() + " HA VINTO!");
    }

    /* DEBUG GAME */
    public static void turn(Monopoly monopoly, TableController tc, int positionsToMove) {
        Player currentPlayer = monopoly.getCurrentPlayer();

        if (!monopoly.isGameOver()) {
            ArrayList<Player> lostPlayers = monopoly.getLostPlayers();

            if (!lostPlayers.isEmpty())
                for (Player player : lostPlayers)
                    TableController.showAlert(player.getName() + " HA PERSO!");

            Platform.runLater(() -> tc.updateBalances());

            int prevPosition = currentPlayer.getPosition();
            monopoly.move(currentPlayer, positionsToMove);
            tc.showTable();
            Box box = monopoly.getBox(currentPlayer);

            if (box instanceof Event) {
                Box tempBox = monopoly.useCard(currentPlayer, ((Event)box).getCard());
                if (tempBox != null)
                    box = tempBox;
            }
            if (box instanceof Property property) {
                if (property.getOwner() == null) { //Acquisto della proprietà
                    if (TableController.alertChoice("VUOI COMPRARE " + property.getName().toUpperCase() + " AL PREZZO DI " + property.getPrice() + "?", currentPlayer)) {
                        if (!monopoly.buyProperty(currentPlayer, property)){
                            monopoly.payPropertyFee(currentPlayer, property);
                            TableController.showAlert("Non hai abbastanza soldi");
                        } else
                            TableController.showAlert(currentPlayer.getName() + " ha acquistato " + property.getName() + "!");
                    } else
                        monopoly.payPropertyFee(currentPlayer, property);

                } else if (!property.getOwner().equals(currentPlayer)) { //Pagamento tassa al proprietario
                    if(TableController.alertChoice("VUOI PROVARE A COMPRARE " + property.getName().toUpperCase() + " DA " + property.getOwner().getName().toUpperCase() + " AL PREZZO DI " + property.getPrice() + "?", currentPlayer))
                        if(TableController.alertChoice(property.getOwner().getName().toUpperCase() + " ACCETTA LA TUA OFFERTA?", property.getOwner()))
                            monopoly.buyPropertyFromPlayer(currentPlayer, property.getOwner(), property);
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
            tc.showTable();
            monopoly.nextTurn();
            Player nextPlayer = monopoly.getCurrentPlayer();
            Platform.runLater(() -> tc.showTurn("Turno di " + nextPlayer.getName()));
            Platform.runLater(() -> tc.updateBalances());
        } else
            TableController.showAlert(monopoly.getWinner().getName() + " HA VINTO!");
    }
}