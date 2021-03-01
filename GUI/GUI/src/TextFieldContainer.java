import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TextFieldContainer {
    private TextField textField;
    private Label label;
    public TextFieldContainer(String initialContent, String type, double x, double y, double width, double height, ObservableList<Node> children) {
        textField = new TextField(initialContent);
        label = new Label(type);
        textField.setDisable(true);
        setPosition(x,y,width,height);
        children.add(textField);
        children.add(label);
    }
    private void setPosition(double x, double y, double width, double height) {
        textField.relocate(x+50,y);
        label.relocate(x,y+5);
        textField.setPrefWidth(width);
        textField.setPrefHeight(height);
    }
    public void updateContent(String word) {
        textField.setText(word);
    }
    public String getContent() {
        return textField.getText();
    }
}
