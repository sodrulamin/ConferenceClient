package sample;

import conference.Conference;
import configuration.Configurations;
import configuration.IpPort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
//import signaling.SignalingMessageProcessor;
//import websocket.WebsocketMessageReceiver;
import windows.HomePage;

import java.io.File;

public class Main extends Application {
    public static String homeDirectory;
    public static String logDirectory;
    private static Logger logger = Logger.getLogger(Main.class);
//    public static WebsocketMessageReceiver signalingMessageReceiver;
//    public static SignalingMessageProcessor signalingMessageProcessor;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            HomePage.getInstance().show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            JSONObject json = new JSONObject();

            homeDirectory = getUserAppDirectory();
            logDirectory = homeDirectory + "\\" + "logs";
            File file = new File(logDirectory);
            if(!file.exists())
                file.mkdirs();
            System.setProperty("AudioTest.root", homeDirectory);

            PropertyConfigurator.configure("log4j.properties");
            logger.debug("Starting Audio test...........");

            Conference.loadAllMeeting();
            initialize();

            launch(args);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void initialize(){
        IpPort signalingAddress = Configurations.getInstance().getSignalingServerAddress();
        String userName = Configurations.getInstance().getAccountUid();
        String password = Configurations.getInstance().getAccountPassword();

        boolean authUser = (userName != null && userName.length() > 0 && password != null && password.length() > 0);


        Platform.runLater(() -> {
            HomePage.getInstance().enableGuestView(!authUser);
        });

        System.out.println("Is AuthUser: " + authUser + " signalingAddress: " + signalingAddress);

        /*if(signalingAddress != null && signalingAddress.isValid()) {
            signalingMessageReceiver = new WebsocketMessageReceiver(signalingAddress);
            if(authUser) {
                signalingMessageReceiver.setPingUsername(userName);
                signalingMessageReceiver.setPingEnabled(true);
                signalingMessageReceiver.setLoginEnabled(true);
            }
            else{
                userName = "dummy-" + System.currentTimeMillis();
                signalingMessageReceiver.setPingUsername(userName);
                signalingMessageReceiver.setPingEnabled(true);
                signalingMessageReceiver.setLoginEnabled(false);
            }
            signalingMessageReceiver.start();

            signalingMessageProcessor = new SignalingMessageProcessor();
            signalingMessageProcessor.start();
        }*/
    }
    public static void configurationChanged() {
        /*if(signalingMessageReceiver != null){
            signalingMessageReceiver.shutDown();
            signalingMessageReceiver = null;
        }
        if(signalingMessageProcessor != null){
            signalingMessageProcessor.shutDown();
            signalingMessageProcessor = null;
        }*/

        initialize();
    }

    private static String getUserAppDirectory(){
        String homeDirectory = System.getProperty("user.home");
        homeDirectory = homeDirectory + "\\" + ".AudioTest";
        return homeDirectory;
    }

    @Override
    public void stop(){
        try {
            logger.debug("App is closing");

            /*if(signalingMessageReceiver != null)
                signalingMessageReceiver.shutDown();
            if(signalingMessageProcessor != null)
                signalingMessageProcessor.shutDown();*/
            logger.debug("App closed successfully...");
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
    }
}
