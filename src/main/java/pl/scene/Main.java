package pl.scene;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.scene.matrices.Matrices;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Main extends Application {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private int width, height;
    private final String buttonStyle =
            "-fx-font-size: 15; " +
                    "-fx-min-height: 20; " +
                    "-fx-min-width: 150; " +
                    "-fx-background-color: #ffffff; " +
                    "-fx-border-color: #000000";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        logger.info("START");

        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        width = (int)screenSize.getMaxX();
        height = (int)screenSize.getMaxY()-20;


        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc= canvas.getGraphicsContext2D();
        Camera camera = new Camera(width, height);
        MeshScene scene = new MeshScene(width, height);
        scene.setCamera(camera);

//      ** UNCOMMENT FOR NEW SCENE**
        scene.loadMeshesLocal();
        scene.draw(gc);


        FileChooser loadFile = new FileChooser();
        Path path;
        path = Paths.get("./examples").toAbsolutePath().normalize();
        if(Files.notExists(path))
        {
            path = Paths.get(".").toAbsolutePath().normalize();
        }

        loadFile.setInitialDirectory(new File(path.toString()));

        loadFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("3D Files", "*.3d"));
        Button loadButton = new Button("Load (*.3d)");
        loadButton.setStyle(buttonStyle);
        loadButton.setLayoutX(10);
        loadButton.setLayoutY(10);
        loadButton.setOnAction(e -> {
            File file = loadFile.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    scene.loadMeshes(file);
                    scene.draw(gc);
                    canvas.requestFocus();
                    logger.info("LOADED");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        FileChooser saveFile = new FileChooser();
        saveFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("3D Files", "*.3d"));
        Button saveButton = new Button("Save (*.3d)");
        saveButton.setStyle(buttonStyle);
        saveButton.setLayoutX(10);
        saveButton.setLayoutY(50);
        saveButton.setOnAction(e -> {
            File file = saveFile.showSaveDialog(primaryStage);
            if(file != null){
                try {
                    PrintWriter writer;
                    writer = new PrintWriter(file);
                    scene.saveMeshes(writer);
                    writer.close();
                    canvas.requestFocus();
                    logger.info("SAVED");
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        Button resetButton = new Button("Reset");
        resetButton.setStyle(buttonStyle);
        resetButton.setLayoutX(10);
        resetButton.setLayoutY(90);
        resetButton.setOnAction(e -> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            camera.setPosition(new Point3D(0, height/5, 0));
            scene.moveCamera();
            canvas.requestFocus();
        });

////      ** CAMERA MOVEMENT **
        canvas.setOnKeyPressed(keyEvent -> {
            // camera movement
            if(keyEvent.getCode() == KeyCode.DOWN){
                camera.setPosition(new Point3D(camera.getPosition().getX(), camera.getPosition().getY()+10, camera.getPosition().getZ()));
            }
            else if(keyEvent.getCode() == KeyCode.UP){
                camera.setPosition(new Point3D(camera.getPosition().getX(), camera.getPosition().getY()-10, camera.getPosition().getZ()));
            }
            else if(keyEvent.getCode() == KeyCode.LEFT){
                camera.setPosition(new Point3D(camera.getPosition().getX()-10, camera.getPosition().getY(), camera.getPosition().getZ()));
            }
            else if(keyEvent.getCode() == KeyCode.RIGHT){
                camera.setPosition(new Point3D(camera.getPosition().getX()+10, camera.getPosition().getY(), camera.getPosition().getZ()));
            }
            else if(keyEvent.getCode() == KeyCode.ENTER){
                camera.setPosition(new Point3D(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ()-10));
            }
            else if(keyEvent.getCode() == KeyCode.BACK_SPACE){
                camera.setPosition(new Point3D(camera.getPosition().getX(), camera.getPosition().getY(), camera.getPosition().getZ()+10));
            }
            // camera rotation
            else if(keyEvent.getCode() == KeyCode.A){
                camera.setTarget(new Point3D(camera.getTarget().getX()-10, camera.getTarget().getY(), camera.getTarget().getZ()));
            }
            else if(keyEvent.getCode() == KeyCode.D){
                camera.setTarget(new Point3D(camera.getTarget().getX()+10, camera.getTarget().getY(), camera.getTarget().getZ()));
            }
            else if(keyEvent.getCode() == KeyCode.W){
                camera.setTarget(new Point3D(camera.getTarget().getX(), camera.getTarget().getY()-10, camera.getTarget().getZ()));
            }
            else if(keyEvent.getCode() == KeyCode.S){
                camera.setTarget(new Point3D(camera.getTarget().getX(), camera.getTarget().getY()+10, camera.getTarget().getZ()));
            }
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            scene.moveCamera();
            scene.draw(gc);
        });

        root.getChildren().add(canvas);
        root.getChildren().add(loadButton);
        root.getChildren().add(saveButton);
        root.getChildren().add(resetButton);


        primaryStage.setTitle("Scene3D");
        Scene myScene = new Scene(root, Color.WHITE);
        primaryStage.setScene(myScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        canvas.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
