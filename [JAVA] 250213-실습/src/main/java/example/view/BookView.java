package example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //화면구성
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/table.fxml"));

        try{
            root = loader.load();
            System.out.println("화면 로드 성공");
        } catch (Exception e){
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Book Search");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
