import javafx.application.Application;
import javafx.stage.Stage;

public class ElectronicStoreApp extends Application {
    public static Stage theStage;
    @Override
    public void start(Stage stage) throws Exception {
        theStage = stage;
        theStage.setResizable(false);
        theStage.setScene(new ElectronicStoreView().getScene());
        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
