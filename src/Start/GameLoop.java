package Start;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Entities.Player;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.*;

import Shaders.StaticShader;
import Terrains.FlatTerrain;
import Terrains.Terrain;
import Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLoop {

    public static void main(String[] args) {
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        //Models:
        TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
                new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        ModelTexture bunnyTexture = new ModelTexture(loader.loadTexture("lightBlue"));
        TexturedModel playerModel = new TexturedModel(OBJLoader.loadObjModel("bunny", loader), bunnyTexture);
        playerModel.getTexture().setReflectivity(0.7f);
        playerModel.getTexture().setShineDamper(5f);
        ModelTexture terrainTexture = new ModelTexture(loader.loadTexture("grass"));
        terrainTexture.setReflectivity(0.3f);
        terrainTexture.setShineDamper(20);
        //Terrain flatTerrain = new Terrain(0, 0, loader);
        Terrain terrain = new Terrain(0, 0, loader, terrainTexture, "heightmap");

        //Randomize Model positions and put into List<Entity>
        List<Entity> entities = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 500; i++){
            float x = random.nextFloat() * 400;
            float z = random.nextFloat() * 400;
            float y = terrain.getHeightOfTerrain(x, z);
            entities.add(new Entity(tree, new Vector3f(x, y, z), 0 , 0 , 0 , 0.5f));
            entities.add(new Entity(grass, new Vector3f(x, y, z), 0 , 0 , 0 , 1));
            entities.add(new Entity(fern, new Vector3f(x, y, z), 0 , 0 , 0 , 0.6f));
        }

        Player player = new Player(playerModel, new Vector3f(0, 10, 0), 0, 0, 0 ,2);
        Camera camera = new Camera(player);
        Light light = new Light(new Vector3f(20, 20, -20), new Vector3f(1, 1, 1));



        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()){
            //entity.increaseRotation(0, 1,0);
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            camera.move();
            player.move(terrain);
            for(Entity entity : entities){
                renderer.processEntity(entity);
            }

            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}