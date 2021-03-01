import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.LinkedList;
import java.util.List;

public class ListViewContainer {
    private final ListView<String> listView = new ListView<>();
    private Label label;
    public ListViewContainer(double x, double y, double width, double height, String title, ObservableList<Node> children){
        label = new Label(title);
        setPosition(x,y,width,height);
        children.add(listView);
        children.add(label);
    }

    private void setPosition(double x, double y, double width, double height){
        label.relocate(x,y-25);
        listView.relocate(x,y);
        listView.setPrefWidth(width);
        listView.setPrefHeight(height);
    }

    public void updateLabel(String text) {
        label.setText(text);
    }
    public int getIndex() {
        return listView.getSelectionModel().getSelectedIndex();
    }
    public String getSelectedItem() {
        return listView.getSelectionModel().getSelectedItem();
    }
    public void addToView(String word){
        listView.getItems().add(word);
    }
    public void flush(){
        listView.getItems().removeAll(listView.getItems());
    }
    public void addAllWords(List<String> words) {
        listView.getItems().addAll(words);
    }
    public void turnOnButton(Button button){
        listView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(getIndex() == -1) {
                    button.setDisable(true);
                } else button.setDisable(false);
            }
        });
    }
    public void removeItem(int index) {
        listView.getItems().remove(index);
    }
    public ObservableList<String> items() {
        return listView.getItems();
    }

}
