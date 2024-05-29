package game;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import player.Player;
import table.*;
import utility.ScannerUtilities;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static javafx.application.Application.launch;

public class Game extends Application {

    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Resources/game/StartScreen.fxml")));
            Scene startScreen = new Scene(root);
            stage.setScene(startScreen);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    public static void playGame(Monopoly monopoly, Player[] players, TableController tc) {
        ScannerUtilities scannerUtilities = new ScannerUtilities();
        int choice;

        /* CARICAMENTO DA FILE */
        do{
            choice = scannerUtilities.readInt("1 Nuova partita \n2 Carica partita salvata\n:");
            switch (choice){
                case 1:
                    monopoly = new Monopoly(new ArrayList<Player> (Arrays.asList(players)));
                    break;
                case 2:
                    String[] savedGames = getSavedGames();
                    if (savedGames.length == 0) {
                        System.out.println("Nessuna partita salvata");
                        return;
                    }
                    do{
                        System.out.println("Partite salvate:");
                        for (int i = 0; i < savedGames.length; i++)
                            System.out.println((i + 1) + " " + savedGames[i]);
                        System.out.println((savedGames.length + 1) + " NUOVA PARTITA");
                        choice = scannerUtilities.readInt("Inserisci il numero della partita da caricare: ");
                        if(choice < 1 || choice > savedGames.length+1)
                            System.out.println("Scelta non valida");
                        else{
                            if(choice != savedGames.length+1){
                                monopoly = loadGame("saved_games" + File.separator + savedGames[choice - 1]);
                                break;
                            }
                            monopoly = new Monopoly(new ArrayList<Player> (Arrays.asList(players)));
                        }
                    }while(choice < 1 || choice > savedGames.length+1);
                    break;
                default:
                    System.out.println("Scelta non valida");
                    break;
            }
        }while(choice < 1 || choice > 2);

        if(monopoly == null)
            monopoly = new Monopoly(new ArrayList<Player> (Arrays.asList(players)));

        /* GIOCO */
        System.out.println("\n\n---BENVENUTI IN MONOPOLY!---\n");
        monopoly.showTable();
        while (!monopoly.isGameOver()) {
            Platform.runLater(() -> tc.updateBalances());
            ArrayList<Player> lostPlayers = monopoly.getLostPlayers();

            if(!lostPlayers.isEmpty())
                for(Player player : lostPlayers)
                    System.out.println(player.getColoredName() + " ha perso!");


            Player currentPlayer = monopoly.getCurrentPlayer();

            System.out.println("\nTurno di " + currentPlayer.getColoredName());

            Platform.runLater(() -> tc.mostraTurno("Turno di " + currentPlayer.getName()));

            choice = scannerUtilities.readInt("1 Mostra i soldi \n2 Lancia il dado \n3 Salva la partita\n:");

            switch (choice) {
                case 1:
                    monopoly.showBalance(currentPlayer);
                    break;
                case 2:
                    int prevPosition = currentPlayer.getPosition();
                    monopoly.movePlayer(currentPlayer);
                    monopoly.showTable();
                    Box box = monopoly.getBox(currentPlayer);

                    if(box instanceof Probability)
                    {
                        ((Probability) box).getProbabilityCards();

                    } else if (box instanceof  Chance) {

                        ((Chance) box).getChanceCard();
                        
                    }

                    if (box instanceof Property property) {
                        if (property.getOwner() == null) { //Acquisto della proprietà
                            if (scannerUtilities.yesOrNo("Vuoi comprare " + property.getColoredName() + "? (si/no): ")) {
                                if (!monopoly.buyProperty(currentPlayer, property))
                                    monopoly.payPropertyFee(currentPlayer, property);
                            } else
                                monopoly.payPropertyFee(currentPlayer, property);

                        } else if (!property.getOwner().equals(currentPlayer)) { //Pagamento tassa al proprietario
                            monopoly.payPropertyFee(currentPlayer, property);

                        } else if (monopoly.hasPlayerAllSameColorProperties(currentPlayer, property)) { //Costruzione di case e hotel
                            BuildableProperty buildableProperty = (BuildableProperty) property;
                            if (buildableProperty.getHousesCount() < 4 && scannerUtilities.yesOrNo("Vuoi costruire una casa? (si/no): "))
                                monopoly.buildHouse(currentPlayer, buildableProperty);
                            else if (buildableProperty.getHousesCount() == 4 && buildableProperty.getHotelsCount() == 0 && scannerUtilities.yesOrNo("Vuoi costruire un hotel? (si/no): "))
                                monopoly.buildHotel(currentPlayer, buildableProperty);
                        }
                    } else
                        monopoly.updateBalance(prevPosition, currentPlayer.getPosition(), currentPlayer);
                    monopoly.nextTurn();
                    break;
                case 3:
                    saveGame(monopoly);
                    break;
                default:
                    System.out.println("Scelta non valida");
                    break;

            }
        }
    }


    public static void saveGame(Monopoly monopoly) {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        DateTimeFormatter formatter = builder.appendPattern("yyyy-MM-dd_HH-mm-ss").toFormatter();
        String fileName = "saved_games" + File.separator + "game_"+ LocalDateTime.now().format(formatter) + ".obj";
        try (FileOutputStream file = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(file)) {
            out.writeObject(monopoly);
        } catch (IOException e) {
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
            System.out.println("Non è stato possibile caricare lo stato del gioco");
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
}