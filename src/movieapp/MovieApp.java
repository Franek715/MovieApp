package movieapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MovieApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root);
        
        stage.setTitle("Megnézett filmek");
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(840);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
