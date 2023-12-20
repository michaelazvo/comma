package sk.comma;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainScene extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainSceneController controller = new MainSceneController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainSceneComma.fxml"));

        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);


        primaryStage.getIcons().add(new Image(Objects.requireNonNull(MainScene.class.getResourceAsStream("comma_logo.png"))));
        primaryStage.setScene(scene);
        primaryStage.setTitle("comma");
        primaryStage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}
