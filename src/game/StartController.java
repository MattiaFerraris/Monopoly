package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StartController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String selectedFileName;

    @FXML
    MenuButton uploadGameMenuButton;

    public void startScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/StartScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        StartController controller = loader.getController();
        controller.uploadGamesToButton(controller.uploadGameMenuButton);
    }

    public void uploadGamesToButton(MenuButton menuButton) {
        List<String> savedGamesList = new ArrayList<>(Arrays.asList(Game.getSavedGames()));
        List<MenuItem> items = new ArrayList<>();

        for (String savedGame : savedGamesList) {
            MenuItem menuItem = new MenuItem(savedGame);
            menuItem.setOnAction(event -> selectedFileName = savedGame);
            items.add(menuItem);
        }
        menuButton.getItems().addAll(items);
    }

    public void goToNameScene(ActionEvent event) throws IOException {
        NameController name = new NameController();
        name.nameScene(event);
    }

    public void caricaPartita(ActionEvent event) throws IOException {
        if (selectedFileName != null) {
            Monopoly monopoly = Game.loadGame("saved_games" + File.separator + selectedFileName);
            goToTableScene(event, monopoly);

        } else {
            System.out.println("Nessun file selezionato");
        }
    }


    public void goToTableScene(ActionEvent event, Monopoly monopoly) throws IOException {
        try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/game/TableScene.fxml")));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                TableController controller = loader.getController();
                controller.tableScene(monopoly, monopoly.getPlayers());
                stage.setScene(scene);
                stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
