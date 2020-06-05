import bot.InstagramBot;
import config.WebDriverConfig;
import controller.ViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;


public class InstagramApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AppView.fxml"));
        Scene scene = null;
        try {
            Parent root = loader.load();
            ViewController controller = loader.getController();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Instragam BOT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
