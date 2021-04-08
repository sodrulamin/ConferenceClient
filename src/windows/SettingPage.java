package windows;

import conference.Constants;
import configuration.Configurations;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.XmlConstants;
import windows.scene.SettingScene;

public class SettingPage {
    private Stage stage;
    private SettingScene settingScene;

    public SettingPage(){
        stage = new Stage();
        stage.setTitle(Constants.TITLE);
        stage.setMinWidth(XmlConstants.MIN_WINDOW_WIDTH);
        stage.setMinHeight(XmlConstants.MIN_WINDOW_HEIGHT);

        settingScene = new SettingScene();
        stage.setScene(settingScene.createScene(this));
    }

    public void show() {
        stage.show();
    }
    public void initOwner(Stage stage) {
        this.stage.initOwner(stage);
        this.stage.initModality(Modality.WINDOW_MODAL);
    }

    public void saveInfo() {
        String signalingIP = settingScene.signalingIpField.getText();
        String signalingPortStr = settingScene.signalingPortField.getText();

        Configurations.getInstance().setSignalingServerIp(signalingIP);
        if(signalingPortStr != null && signalingPortStr.length() > 0) {
            try {
                Configurations.getInstance().setSignalingServerPort(Integer.parseInt(signalingPortStr));
            }catch(Exception e){}
        }
        else{
            Configurations.getInstance().setSignalingServerPort(0);
        }

        Configurations.getInstance().setAccountUid(settingScene.usernameField.getText());
        Configurations.getInstance().setAccountPassword(settingScene.passwordField.getText());
        stage.close();
        Main.configurationChanged();
    }
}
