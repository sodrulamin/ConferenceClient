package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class ParticipantViewManager {
    public ListView<String> participantView;
    int index;
    String data;

    public ParticipantViewManager(ListView<String> participantView){
        this.participantView = participantView;
    }

    public void initialize(){
        ObservableList<String> items = FXCollections.observableArrayList ();
        participantView.setItems(items);

        participantView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                index = newValue.intValue();
            }
        });
        participantView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                data = newValue;
            }
        });
        participantView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            int position = 0;
            String positionstr = "";
            @Override
            public ListCell<String> call(ListView<String> param) {
                Label label = new Label();

                ListCell<String> cell = new ListCell<String>(){

                    @Override
                    protected void updateItem(String item, boolean empty) {

                        super.updateItem(item, empty);

                        if(!empty){
                            label.setText(item);

                            /*label.setPrefHeight(20);
                            label.setFont(new Font(15.0));*/
                            this.setGraphic(label);
                        }
                    }

                };
                cell.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if(newValue && !label.getText().equals("")){
                            label.setTextFill(Color.BLACK);
                        }else{

                        }
                    }
                });
                cell.hoverProperty().addListener(new ChangeListener<Boolean>() {

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                                        Boolean newValue) {
                        if(newValue && !label.getText().equals("")){
                            position = param.getItems().indexOf(label.getText());
                            param.getFocusModel().focus(position);
                        }

                    }
                });
                // Set how to handle drag and drop elements, first delete the drag position element, and then add it in the drag and drop position
                cell.setOnDragDropped(new EventHandler<DragEvent>() {

                    @Override
                    public void handle(DragEvent event) {
                        if(position == -1){
                            position = param.getItems().size()-1;
                        }
                        String value = event.getDragboard().getString();
                        param.getItems().remove(index);
                        param.getItems().add(position,value);
//                        System.out.println(value);
                        param.getSelectionModel().select(position);
                    }
                });

                cell.setOnDragOver(new EventHandler<DragEvent>() {

                    @Override
                    public void handle(DragEvent event) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                });
                // Get the drag position in real time
                cell.setOnDragEntered(new EventHandler<DragEvent>() {

                    @Override
                    public void handle(DragEvent event) {
                        position = param.getItems().indexOf(label.getText());
                        positionstr = label.getText();
                        param.getFocusModel().focus(position);//Get focus

                        //System.out.println(position + "-" + label.getText() );
                    }
                });
                // Create a drag panel to join the selected drag element
                cell.setOnDragDetected(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                        Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(data);
                        db.setContent(content);
                    }
                });

                return cell;
            }
        });

    }
}
