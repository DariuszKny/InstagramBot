package controller;

import bot.InstagramBot;
import config.WebDriverConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    private InstagramBot instagramBot;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField likesField;

    @FXML
    private TextField minField;

    @FXML
    private TextField maxField;

    @FXML
    private Button mainButton;

    @FXML
    private Button hashButton;

    @FXML
    private Button mainButtonStop;

    @FXML
    private Button hashButtonStop;

    @FXML
    private TextField hashInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instagramBot = new InstagramBot();
        hideCancelButtons();
    }


    private void mainPageTask(){
        hideStartButtons();
        System.out.println("Process Started");
        instagramBot.isCancelled = true;
        WebDriver webDriver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        if (webDriver != null && isInt(likesField.getText())) {
            instagramBot.startBot(webDriver,
                    Integer.parseInt(likesField.getText()),
                    loginField.getText(),
                    passwordField.getText(),
                    Integer.parseInt(maxField.getText()),
                    Integer.parseInt(minField.getText())
            );
            webDriver.close();
        }
        System.out.println("Process is Done");
        hideCancelButtons();
    }

    private void hashPageTask() {
        hideStartButtons();
        System.out.println("Process Started");
        instagramBot.isCancelled = true;
        WebDriver webDriver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        if (webDriver != null && isInt(likesField.getText())) {
            instagramBot.startBotByHashTag(webDriver,
                    Integer.parseInt(likesField.getText()),
                    loginField.getText(),
                    passwordField.getText(),
                    Integer.parseInt(maxField.getText()),
                    Integer.parseInt(minField.getText()),
                    hashInput.getText()
            );
            webDriver.close();
        }
        System.out.println("Process is Done");
        hideCancelButtons();
    }

    @FXML
    public void startHashPageTask() {
        new Thread(this::hashPageTask).start();
    }

    @FXML
    public void startMainPageTask() {
        new Thread(this::mainPageTask).start();
    }

    @FXML void cancelMainPageTask() {
      instagramBot.isCancelled = false;
        System.out.println("Process will be stopped soon");
    };


    private boolean isInt(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    private void hideCancelButtons() {
        mainButtonStop.setDisable(true);
        mainButtonStop.setVisible(false);
        hashButtonStop.setDisable(true);
        hashButtonStop.setVisible(false);

        mainButton.setDisable(false);
        mainButton.setVisible(true);
        hashButton.setDisable(false);
        hashButton.setVisible(true);
    }

    private void hideStartButtons() {
        mainButton.setDisable(true);
        mainButton.setVisible(false);
        hashButton.setDisable(true);
        hashButton.setVisible(false);

        mainButtonStop.setDisable(false);
        mainButtonStop.setVisible(true);
        hashButtonStop.setDisable(false);
        hashButtonStop.setVisible(true);
    }

}
