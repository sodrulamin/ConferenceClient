package windows;

import conference.Conference;
import conference.Constants;
import configuration.Configurations;
import configuration.IpPort;
import javafx.stage.Stage;
import sample.XmlConstants;
import windows.scene.HomeScene;

public class HomePage {
    private static HomePage homePage = null;
    private Stage stage;
    private HomeScene homeScene;


    private HomePage(){
        stage = new Stage();
        stage.setTitle(Constants.TITLE);
        stage.setMinWidth(XmlConstants.MIN_WINDOW_WIDTH);
        stage.setMinHeight(XmlConstants.MIN_WINDOW_HEIGHT);
        homeScene = new HomeScene();
        stage.setScene(homeScene.createScene(this));
    }
    public void show(){
        stage.show();

        IpPort signalingAddress = Configurations.getInstance().getSignalingServerAddress();
        if(signalingAddress == null || !signalingAddress.isValid())
            showSettingPage();
    }
    public void hide(){
        stage.hide();
    }
    public void close(){
        stage.close();
    }

    public Stage getStage(){
        return stage;
    }

    public void showScheduleMeetingPage(){
        ScheduleMeetingPage schedulePage = new ScheduleMeetingPage();
        schedulePage.initOwner(stage);
        schedulePage.show();
    }

    public void showStartMeetingPage() {
        /*StartMeetingPage startMeetingPage = new StartMeetingPage();
        startMeetingPage.initOwner(stage);
        startMeetingPage.setConference(Conference.runningConference);
        startMeetingPage.show();*/
    }
    public void showJoinMeetingPage(){
        /*JoinMeetingPage joinMeetingPage = new JoinMeetingPage();
        joinMeetingPage.initOwner(stage);
        joinMeetingPage.show();*/
    }
    public void showSettingPage(){
        SettingPage settingsPage = new SettingPage();
        settingsPage.initOwner(stage);
        settingsPage.show();
    }
    public void enableGuestView(boolean enabled){
        homeScene.enableGuestView(enabled);
    }

    public synchronized static HomePage getInstance(){
        if(homePage == null)
            homePage = new HomePage();
        return homePage;
    }
}
