package scene;

import com.sun.prism.Graphics;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scene.models.Cuboid;

public class Main extends Application {
    private int width, height;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        //width = (int)screenSize.getMaxX();
        //height = (int)screenSize.getMaxY()-20;
        width=1000;
        height=800;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc= canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        MeshScene scene = new MeshScene(width, height);
        scene.loadMeshes();
        scene.draw(gc);

        primaryStage.setTitle("Scene3D");
        primaryStage.setScene(new Scene(root, Color.WHITE));
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
