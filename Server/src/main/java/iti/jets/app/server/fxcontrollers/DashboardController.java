package iti.jets.app.server.fxcontrollers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import com.jfoenix.controls.JFXToggleButton; // Add this import statement

import iti.jets.app.server.Network.Connection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class DashboardController implements Initializable {

    private int toggler = 1 ;
    @FXML
    public BorderPane bp;

    @FXML
    public AnchorPane ap;

    public JFXSnackbar snackbar;

    private Connection openserver = new Connection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openserver.openConnection();
        toggleButton.setSelected(true);
        snackbar = new JFXSnackbar(bp);



    }


    @FXML
    public JFXToggleButton toggleButton;


    @FXML
    void home(MouseEvent event) {
        bp.setCenter(ap);
    }


    @FXML
    void announcements(MouseEvent event) {
        loadPage("/iti/jets/app/server/views/announcements.fxml");
    }

    @FXML
    void statistics(MouseEvent event) {
        loadPage("/iti/jets/app/server/views/statistics.fxml");
    }

    private void loadPage(String page) {
        Parent root = null;
        try {
            System.out.println("Page: " + page);
            if (page == null) {
                System.err.println("Page is null.");
                return;
            }
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bp.setCenter(root);
    }

    private void showSnackbar(String message) {
        toggler = (toggler +1)%2;
        if(toggler == 1){
            openserver.openConnection();
        }else{
            System.out.println("close");
            openserver.closeConnection();
        }
        Platform.runLater(() -> {
            JFXSnackbarLayout snackbarLayout = new JFXSnackbarLayout(message);
            snackbar.enqueue(new JFXSnackbar.SnackbarEvent(snackbarLayout));
            snackbarLayout.getStyleClass().add("jfx-snackbar");
            snackbar.setPrefWidth(180);
            snackbarLayout.setStyle("-fx-text-fill: white;");

            int displayDurationMillis = 400;

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(displayDurationMillis), event -> {
                snackbar.close();
            }));
            timeline.play();
        });
        snackbar.setVisible(true);
//            snackbarLayout.setStyle(
//                    "-fx-background-color: #6165a3;" +    // Background color
//                            "-fx-text-fill: white;" +              // Text color
//                            "-fx-font-size: 14px;" +               // Font size
//                            "-fx-border-radius: 10 10 0 0;" +      // Border radius (top-left, top-right, bottom-right, bottom-left)
//                            "-fx-font-family: 'system-ui';" +
//                            "-fx-background-radius: 10;" +         // Background radius
//                            "-fx-max-height: 8;"                   // Adjusted minimum height
//            );
//            snackbarLayout.setStyle("-fx-text-fill: red;" );
//            snackbarLayout.setStyle("-fx-background-color: red;" );
    }

    @FXML
    private void handleToggleButton() {
        if (toggleButton.isSelected()) {
            showSnackbar("Server is on");
        } else {
            showSnackbar("Server is off");
        }
    }

}