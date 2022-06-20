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

<img width="1680" alt="Screenshot 2020-12-28 at 14 58 26" src="https://user-images.githubusercontent.com/49707233/174539012-2639d3ef-99aa-4e0e-a2bf-4e3e92095a5e.png">
<img width="1680" alt="Screenshot 2020-12-28 at 14 59 00" src="https://user-images.githubusercontent.com/49707233/174539019-a530b697-0cfd-4df6-95b7-90467b6922ce.png">
<img width="1680" alt="Screenshot 2020-12-28 at 14 57 46" src="https://user-images.githubusercontent.com/49707233/174539022-9ec53a3d-d2ee-424b-9d52-6d75f0897212.png">
