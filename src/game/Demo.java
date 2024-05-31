package game;

import player.Player;
import table.*;
import utility.ScannerUtilities;

import java.io.File;
import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        ScannerUtilities scannerUtilities = new ScannerUtilities();
        Monopoly monopoly = Game.loadGame("saved_games" + File.separator + "demoCharlieAllPropr.obj");
        demo(monopoly, scannerUtilities);
    }

    public static void demo(Monopoly monopoly, ScannerUtilities scannerUtilities){
        int prefixPositions[] = {3};
        int positionsPointer = 0;
        int choice;
        monopoly.showTable();
        while (!monopoly.isGameOver()) {
            ArrayList<Player> lostPlayers = monopoly.getLostPlayers();

            if(!lostPlayers.isEmpty())
                for(Player player : lostPlayers)
                    System.out.println(player.getColoredName() + " ha perso!");


            Player currentPlayer = monopoly.getCurrentPlayer();

            System.out.println("\nTurno di " + currentPlayer.getColoredName());
            choice = scannerUtilities.readInt("1 Mostra i soldi \n2 Lancia il dado \n3 Salva la partita\n:");


            /* DEBUG COMMANDS
            - 4: GET POSITION
            - 5: MOVE PLAYER
            - 6: GIVE PLAYER PROPERTY
            - 7: ADD HOUSE TO PROPERTY
             */

            switch (choice) {
                case 1:
                    monopoly.showBalance(currentPlayer);
                    break;
                case 2, 5:
                    int prevPosition = currentPlayer.getPosition();
                    if (choice == 5) // DEBUG
                        monopoly.move(currentPlayer, scannerUtilities.readInt("Positions to move: "));
                    else
                        monopoly.movePlayer(currentPlayer.getName(), prefixPositions[positionsPointer++ % prefixPositions.length]); // FOR DEMO
                    monopoly.showTable();
                    Box box = monopoly.getBox(currentPlayer);

                    if(box instanceof Probability)
                    {
                        monopoly.useProbabilityCard(currentPlayer, ((Probability) box).getProbabilityCards());
                    } else if (box instanceof  Chance) {
                        monopoly.useChanceCard(currentPlayer, ((Chance) box).getChanceCard());
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
                    Game.saveGame(monopoly);
                    break;
                /* DEBUG */
                case 4: // GET POSITION
                    System.out.println("CURRENT PLAYER POSITION: " + monopoly.getCurrentPlayer().getPosition());
                    break;
                case 6:
                    monopoly.givePlayerProperty(currentPlayer.getName(), scannerUtilities.readString("Property name: "));
                    break;
                case 7:
                    monopoly.addHouse(currentPlayer.getName(), scannerUtilities.readString("Property name: "));
                    break;
                default:
                    System.out.println("Scelta non valida");
                    break;

            }
        }
    }
}
