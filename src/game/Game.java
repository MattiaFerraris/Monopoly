package game;

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

public class Game {

    public static void main(String[] args) {
        ScannerUtilities scannerUtilities = new ScannerUtilities();
        Monopoly monopoly = null;
        int choice;
        int selectedGame;

        /* CARICAMENTO DA FILE */
        do{
            choice = scannerUtilities.readInt("1 Nuova partita \n2 Carica partita salvata\n:");
            switch (choice){
                case 1:
                    monopoly = new Monopoly(generatePlayers(scannerUtilities));
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
                        selectedGame = scannerUtilities.readInt("Inserisci il numero della partita da caricare: ");
                        if(selectedGame < 1 || selectedGame > savedGames.length+1)
                            System.out.println("Scelta non valida");
                        else{
                            if(selectedGame != savedGames.length+1){
                                monopoly = loadGame("saved_games" + File.separator + savedGames[selectedGame - 1]);
                                break;
                            }
                            monopoly = new Monopoly(generatePlayers(scannerUtilities));
                        }
                    }while(selectedGame < 1 || selectedGame > savedGames.length+1);
                    break;
                default:
                    System.out.println("Scelta non valida");
                    break;
            }
        }while(choice < 1 || choice > 2);

        if(monopoly == null)
            monopoly = new Monopoly(generatePlayers(scannerUtilities));

        /* GIOCO */
        System.out.println("\n\n---BENVENUTI IN MONOPOLY!---\n");
        monopoly.showTable();
        while (!monopoly.isGameOver()) {
            ArrayList<Player> lostPlayers = monopoly.getLostPlayers();

            if(!lostPlayers.isEmpty())
                for(Player player : lostPlayers)
                    System.out.println(player.getColoredName() + " ha perso!");


            Player currentPlayer = monopoly.getCurrentPlayer();

            System.out.println("\nTurno di " + currentPlayer.getColoredName());
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


    public static Player newPlayer(ScannerUtilities scannerUtilities) {
        String name = "";
        String symbol = "";
        while (name.isEmpty())
            name = scannerUtilities.readString("Inserisci il nome: ").trim();
        while (symbol.isEmpty())
            symbol = scannerUtilities.readString("Inserisci il simbolo: ").trim();
        return new Player(name, symbol.substring(0, 1), 0);
    }

    public static ArrayList<Player> generatePlayers(ScannerUtilities scannerUtilities) {
        System.out.println("Inserire il nome e il simbolo\n");
        ArrayList<Player>  players = new ArrayList<>(Monopoly.NUMBER_OF_PLAYERS);
        Colors[] defaultColors = {Colors.RED, Colors.BLUE, Colors.GREEN, Colors.YELLOW};
        for (int i = 0; i < Monopoly.NUMBER_OF_PLAYERS; ) {
            Player playerToAdd = newPlayer(scannerUtilities);
            if(players.contains(playerToAdd))
                System.out.println("Nome o simbolo già utilizzato");
            else {
                playerToAdd.setColor(defaultColors[i]);
                players.add(playerToAdd);
                i++;
            }
        }
        return players;
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