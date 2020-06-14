package pl.scene;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class Main extends Application {
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

        Rectangle2D screenSize = Screen.getPrimary().getBounds();
        //width = (int)screenSize.getMaxX();
        //height = (int)screenSize.getMaxY()-20;
        width=1000;
        height=800;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc= canvas.getGraphicsContext2D();
        Camera camera = new Camera(width, height);
        MeshScene scene = new MeshScene(width, height);
        //pl.scene.loadMeshes(null);
        scene.setCamera(camera);

        FileChooser loadFile = new FileChooser();
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
        });


        root.getChildren().add(canvas);
        root.getChildren().add(loadButton);
        root.getChildren().add(saveButton);
        root.getChildren().add(resetButton);


        primaryStage.setTitle("Scene3D");
        Scene myScene = new Scene(root, Color.WHITE);
//        myScene.setOnKeyPressed(keyEvent -> {
//            System.out.println(keyEvent.getCode());
//            if(keyEvent.getCode() == KeyCode.ENTER){
//                camera.setPosition(new Point3D(camera.getPosition().getX(), camera.getPosition().getY()-10, camera.getPosition().getZ()));
//            }
//            else if(keyEvent.getCode() == KeyCode.UP){
//                camera.setPosition(new Point3D(camera.getPosition().getX(), camera.getPosition().getY()+10, camera.getPosition().getZ()));
//            }
//            else if(keyEvent.getCode() == KeyCode.LEFT){
//                camera.setPosition(new Point3D(camera.getPosition().getX()-10, camera.getPosition().getY(), camera.getPosition().getZ()));
//            }
//            else if(keyEvent.getCode() == KeyCode.RIGHT){
//                camera.setPosition(new Point3D(camera.getPosition().getX()+10, camera.getPosition().getY(), camera.getPosition().getZ()));
//            }
//            pl.scene.moveCamera();
//            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//            pl.scene.draw(gc);
//        });
        primaryStage.setScene(myScene);
        //primaryStage.setMaximized(true);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
