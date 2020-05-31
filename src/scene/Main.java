package scene;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        Canvas canvas = new Canvas(screenSize.getMaxX(), screenSize.getMaxY()-20);
        GraphicsContext gc= canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        draw(gc);

        primaryStage.setTitle("Scene3D");
        primaryStage.setScene(new Scene(root, Color.WHITE));
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeLine(0,0,100,100);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
