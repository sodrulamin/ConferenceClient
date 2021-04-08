package windows.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import windows.HomePage;
//import windows.MeetingList;

public class HomeScene {
    private static int SPACING = 80;
    private static int SQUARE_BUTTON_LENGTH = 150;
    public Button buttonSchedule;
    public Button buttonStart;
    public Button buttonJoin;
    public Button buttonSettings;
    private HomePage homePage;

    public Scene createScene(HomePage page){
        homePage = page;
        buttonSchedule = new Button("Schedule Meeting");
        buttonSchedule.setMinWidth(SQUARE_BUTTON_LENGTH);
        buttonSchedule.setMinHeight(SQUARE_BUTTON_LENGTH);
        buttonSchedule.setOnAction(e -> {
            homePage.showScheduleMeetingPage();
        });

        buttonStart = new Button("Start Meeting");
        buttonStart.setMinWidth(SQUARE_BUTTON_LENGTH);
        buttonStart.setMinHeight(SQUARE_BUTTON_LENGTH);
        buttonStart.setOnAction(e -> {
//            MeetingList.getInstance().show();
        });

        buttonJoin = new Button("Join Meeting");
        buttonJoin.setMinWidth(SQUARE_BUTTON_LENGTH);
        buttonJoin.setMinHeight(SQUARE_BUTTON_LENGTH);
        buttonJoin.setOnAction(e -> {
            homePage.showJoinMeetingPage();
        });

        buttonSettings = new Button("Settings");
        buttonSettings.setMinWidth(SQUARE_BUTTON_LENGTH);
        buttonSettings.setMinHeight(SQUARE_BUTTON_LENGTH);
        buttonSettings.setOnAction(e -> {
            homePage.showSettingPage();
        });

        HBox firstRow = new HBox();
        HBox secondRow = new HBox();
        firstRow.setAlignment(Pos.CENTER);
        secondRow.setAlignment(Pos.CENTER);
        firstRow.setSpacing(SPACING);
        secondRow.setSpacing(SPACING);

        firstRow.getChildren().addAll(buttonSchedule, buttonStart);
        secondRow.getChildren().addAll(buttonJoin, buttonSettings);

        VBox vbox = new VBox();
        vbox.setId("vbox");
        vbox.setSpacing(SPACING);
        vbox.setAlignment(Pos.CENTER);
        vbox.setFillWidth(true);
        vbox.getChildren().addAll(firstRow, secondRow);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        Scene scene = new Scene(vbox);
        scene.getStylesheets().add("styles/BigButtonStyles.css");

        return scene;
    }
    public void enableGuestView(boolean enabled){
        buttonSchedule.setDisable(enabled);
        buttonStart.setDisable(enabled);
    }

}
