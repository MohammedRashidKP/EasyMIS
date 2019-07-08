package easymis.controllers;

import easymis.models.repository.SettingsRepository;
import easymis.utils.Constants;
import easymis.utils.NumberFilter;
import easymis.utils.StringUtils;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author RashidKP
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField pinField;
    @FXML
    private Label passwordIncorrectLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pinField.setTextFormatter(new NumberFilter().filter());
    }

    @FXML
    private void onLoginClicked(ActionEvent event) {
        if (StringUtils.isNotNullCheckSpace(pinField.getText())) {
            String pin = SettingsRepository.getUniqueInstance().getPinNmber();
            if (StringUtils.isNotNullCheckSpace(pin)) {
                if (pin.equals(pinField.getText())) {
                    try {
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        launchApplication(stage);
                    } catch (Exception ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    passwordIncorrectLabel.setVisible(true);
                }
            }
        }
    }

    private void launchApplication(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(Constants.MAIN_VIEW));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setFullScreen(true);
        //stage.resizableProperty().setValue(Boolean.TRUE);
        stage.show();
    }

}
