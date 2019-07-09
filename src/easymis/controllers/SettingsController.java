package easymis.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import easymis.models.entity.Settings;
import easymis.models.entity.TransactionStatus;
import easymis.models.entity.enumeration.EventType;
import easymis.models.repository.SettingsRepository;
import easymis.models.utils.SettingsConstants;
import easymis.utils.AlertHelper;
import easymis.utils.NumberFilter;
import easymis.utils.StringUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import org.controlsfx.control.textfield.CustomPasswordField;

/**
 * FXML Controller class
 *
 * @author RashidKP
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTabPane tabPane;
    @FXML
    private Tab panelTabSettings;
    @FXML
    private Tab panelTabPin;
    @FXML
    private JFXTextField wedding;
    @FXML
    private JFXTextField mehandi;
    @FXML
    private JFXTextField reception5pm;
    @FXML
    private JFXTextField reception3pm;
    @FXML
    private JFXTextField ishaHallDay;
    @FXML
    private JFXTextField ishaHallEve;
    @FXML
    private JFXTextField normalAC;
    @FXML
    private JFXTextField additionalAC;
    @FXML
    private JFXTextField nicaHall;
    @FXML
    private JFXTextField dailyWages;
    @FXML
    private JFXTextField security;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton editButton;
    @FXML
    private CustomPasswordField newPin;
    @FXML
    private CustomPasswordField confirmationPin;
    @FXML
    private JFXButton changePinButton;
    @FXML
    private JFXButton clearButton;
    @FXML
    private Label pinNotMatchLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setNumberFormatter();
        initializePanelTabs();
        loadSettingsData();
    }

    @FXML
    private void onSaveCostClick(ActionEvent event) {
        List<Settings> settings = getSettingsData();
        if (!settings.isEmpty()) {
            TransactionStatus status = SettingsRepository.getUniqueInstance().update(settings);
            AlertHelper.showMessage(status);
            if (status.isSuccess()) {
                setFieldsEditable(false);
                saveButton.setDisable(true);
            }
        }
    }

    @FXML
    private void onEditCostClick(ActionEvent event) {
        setFieldsEditable(true);
        saveButton.setDisable(false);
    }

    @FXML
    private void onChangePinClick(ActionEvent event) {
        if (StringUtils.isNotNullCheckSpace(newPin.getText()) && StringUtils.isNotNullCheckSpace(confirmationPin.getText())) {
            if (newPin.getText().equals(confirmationPin.getText())) {
                Settings setting = new Settings();
                setting.setAttribute(SettingsConstants.PIN);
                setting.setAttributeValue(newPin.getText());
                TransactionStatus status = SettingsRepository.getUniqueInstance().update(Collections.singletonList(setting));
                AlertHelper.showMessage(status);
                if (status.isSuccess()) {
                    pinNotMatchLabel.setVisible(false);
                    newPin.setEditable(false);
                    confirmationPin.setEditable(false);
                }
            } else {
                pinNotMatchLabel.setVisible(true);
            }
        }
    }

    @FXML
    private void onPinClearClick(ActionEvent event) {
        newPin.setText(null);
        confirmationPin.setText(null);
    }

    private void loadSettingsData() {
        List<Settings> settings = SettingsRepository.getUniqueInstance().getAllSettings();
        if(settings != null && !settings.isEmpty()){
            populateSettings(settings);
            setFieldsEditable(false);
        }
    }

    private void initializePanelTabs() {
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newTab) {
                if (null != newTab.getId()) {
                    if (newTab.getId().equals("panelTabSettings")) {
                        loadSettingsData();
                    } else if (newTab.getId().equals("panelTabPin")) {
                        newPin.setText(null);
                        confirmationPin.setText(null);
                        newPin.setEditable(true);
                        confirmationPin.setEditable(true);
                    }
                }
            }
        }
        );
    }

    private List<Settings> getSettingsData() {
        List<Settings> settingsList = new ArrayList<>();
        if (StringUtils.isNotNullCheckSpace(wedding.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.WEDDING.toString());
            setting.setAttributeValue(wedding.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(mehandi.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.MEHANDI.toString());
            setting.setAttributeValue(mehandi.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(reception3pm.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.RECEPTION_3_PM.toString());
            setting.setAttributeValue(reception3pm.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(reception5pm.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.RECEPTION_5_PM.toString());
            setting.setAttributeValue(reception5pm.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(ishaHallDay.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.ISHA_HALL_AC_DAY.toString());
            setting.setAttributeValue(ishaHallDay.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(ishaHallEve.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.ISHA_HALL_AC_EVE.toString());
            setting.setAttributeValue(ishaHallEve.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(normalAC.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.NORMAL_AC.toString());
            setting.setAttributeValue(normalAC.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(additionalAC.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.ADDITIONAL_AC.toString());
            setting.setAttributeValue(additionalAC.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(nicaHall.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(EventType.NICA_LONGUE_AC.toString());
            setting.setAttributeValue(nicaHall.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(dailyWages.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(SettingsConstants.DAILY_WAGES);
            setting.setAttributeValue(dailyWages.getText());
            settingsList.add(setting);
        }
        if (StringUtils.isNotNullCheckSpace(security.getText())) {
            Settings setting = new Settings();
            setting.setAttribute(SettingsConstants.SECURITY);
            setting.setAttributeValue(security.getText());
            settingsList.add(setting);
        }
        return settingsList;
    }

    private void setNumberFormatter() {
        wedding.setTextFormatter(new NumberFilter().filter());
        mehandi.setTextFormatter(new NumberFilter().filter());
        reception5pm.setTextFormatter(new NumberFilter().filter());
        reception3pm.setTextFormatter(new NumberFilter().filter());
        ishaHallDay.setTextFormatter(new NumberFilter().filter());
        ishaHallEve.setTextFormatter(new NumberFilter().filter());
        normalAC.setTextFormatter(new NumberFilter().filter());
        additionalAC.setTextFormatter(new NumberFilter().filter());
        nicaHall.setTextFormatter(new NumberFilter().filter());
        dailyWages.setTextFormatter(new NumberFilter().filter());
        security.setTextFormatter(new NumberFilter().filter());
        newPin.setTextFormatter(new NumberFilter().filter());
        confirmationPin.setTextFormatter(new NumberFilter().filter());
    }

    private void setFieldsEditable(boolean flag) {
        wedding.setEditable(flag);
        mehandi.setEditable(flag);
        reception5pm.setEditable(flag);
        reception3pm.setEditable(flag);
        ishaHallDay.setEditable(flag);
        ishaHallEve.setEditable(flag);
        normalAC.setEditable(flag);
        additionalAC.setEditable(flag);
        nicaHall.setEditable(flag);
        dailyWages.setEditable(flag);
        security.setEditable(flag);
    }

    private void populateSettings(List<Settings> settings) {
        for(Settings setting: settings){
            if(setting.getAttribute().equals(EventType.WEDDING.toString())){
                wedding.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(EventType.MEHANDI.toString())){
                mehandi.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(EventType.RECEPTION_5_PM.toString())){
                reception5pm.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(EventType.RECEPTION_3_PM.toString())){
                reception3pm.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(EventType.ISHA_HALL_AC_DAY.toString())){
                ishaHallDay.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(EventType.ISHA_HALL_AC_EVE.toString())){
                ishaHallEve.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(EventType.NICA_LONGUE_AC.toString())){
                nicaHall.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(EventType.NORMAL_AC.toString())){
                normalAC.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(EventType.ADDITIONAL_AC.toString())){
                additionalAC.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(SettingsConstants.DAILY_WAGES)){
                dailyWages.setText(setting.getAttributeValue());
            } else if(setting.getAttribute().equals(SettingsConstants.SECURITY)){
                security.setText(setting.getAttributeValue());
            } 
        }
    }
}
