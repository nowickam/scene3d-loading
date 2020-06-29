# scene3d-loading

3D primitives loading application. 

The primitives and their attributes are saved in a file with customary .3d extension.

To create a new scene, uncomment loadMeshesLocal() and scene.draw(gc) in /src/main/java/pl/scene/Main, and edit localMeshesLocal content accordingly.

The scene examples are located in the /examples directory.

**To run:** 

- mvn clean javafx:run

**Usage**:

- **Load** to load a .3d scene
- **Save** to save the scene in .3d extension
- **Reset** to clear the canvas and reset the camera position
- Use the **arrows, enter and backspace** to move the camera
- Use **WSAD** to rotate the camera
