package windows.scene;

import configuration.Configurations;
import configuration.IpPort;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import windows.SettingPage;

public class SettingScene {
    private Button buttonSave;
    public TextField signalingIpField;
    public TextField signalingPortField;
    public TextField usernameField;
    public PasswordField passwordField;

    public Scene createScene(SettingPage settingPage) {
        VBox vbox = new VBox();

        HBox parameterBox = new HBox();
        Region topOpenRegion = new Region();
        topOpenRegion.setMinHeight(100);
        Region openRegion = new Region();
        HBox buttonBox = new HBox();

        VBox firstLabelBox = new VBox();
        VBox firstFieldBox = new VBox();
        VBox secondLabelBox = new VBox();
        VBox secondFieldBox = new VBox();

        Label signalingIpLabel = new Label("Signaling IP: ");
        Label usernameLabel = new Label("Username: ");
        firstLabelBox.setSpacing(20);
        firstLabelBox.getChildren().addAll(signalingIpLabel, usernameLabel);

        signalingIpField = new TextField();
        usernameField = new TextField();
        HBox.setHgrow(signalingIpField, Priority.ALWAYS);
        HBox.setHgrow(usernameField, Priority.ALWAYS);
        firstFieldBox.setSpacing(10);
        firstFieldBox.getChildren().addAll(signalingIpField, usernameField);

        Label signalingPortLabel = new Label("Signaling Port: ");
        Label passwordLabel = new Label("Password: ");
        secondLabelBox.setSpacing(20);
        secondLabelBox.getChildren().addAll(signalingPortLabel, passwordLabel);

        signalingPortField = new TextField();
        passwordField = new PasswordField();
        HBox.setHgrow(signalingPortField, Priority.ALWAYS);
        HBox.setHgrow(passwordField, Priority.ALWAYS);
        secondFieldBox.setSpacing(10);
        secondFieldBox.getChildren().addAll(signalingPortField, passwordField);

        Region paddingRegion1 = new Region();
        HBox.setHgrow(paddingRegion1, Priority.ALWAYS);
        Region paddingRegion2 = new Region();
        HBox.setHgrow(paddingRegion2, Priority.ALWAYS);
        Region paddingRegion3 = new Region();
        HBox.setHgrow(paddingRegion3, Priority.ALWAYS);
        HBox.setHgrow(firstFieldBox, Priority.ALWAYS);
        HBox.setHgrow(secondFieldBox, Priority.ALWAYS);

        parameterBox.setSpacing(10);
        parameterBox.setAlignment(Pos.CENTER);
        parameterBox.getChildren().addAll(paddingRegion1, firstLabelBox, firstFieldBox, paddingRegion2, secondLabelBox, secondFieldBox, paddingRegion3);

        VBox.setVgrow(openRegion, Priority.ALWAYS);

        buttonSave = new Button("Save");
        buttonSave.setMinSize(100, 50);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(buttonSave);


        vbox.getChildren().addAll(topOpenRegion, parameterBox, openRegion, buttonBox);
        vbox.setPadding(new Insets(10, 10, 40, 10));

        initializePreferencesAttributes();
        buttonSave.setOnAction(e -> {
            settingPage.saveInfo();
        });

        Scene scene = new Scene(vbox);
        return scene;
    }

    private void initializePreferencesAttributes(){
        IpPort signalingServerAddress = Configurations.getInstance().getSignalingServerAddress();

        if(signalingServerAddress != null) {
            if( signalingServerAddress.ip != null)
                signalingIpField.setText(signalingServerAddress.ip);
            if(signalingServerAddress.port > 0)
                signalingPortField.setText(signalingServerAddress.port + "");
        }
        usernameField.setText(Configurations.getInstance().getAccountUid());
        passwordField.setText(Configurations.getInstance().getAccountPassword());
    }




}
