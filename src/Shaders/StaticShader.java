package Shaders;

import Entities.Camera;
import Entities.Light;
import Toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class StaticShader extends ShaderProgram{


    private static final String VERTEX_FILE = "src/Shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/Shaders/fragmentShader.txt";

    private int location_TransformationMatrix;
    private int location_ProjectionMatrix;
    private int location_ViewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_useFaketLighting;
    private int location_skyColor;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "uv");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {

        location_TransformationMatrix = super.getUniformLocation("transformationMatrix");
        location_ProjectionMatrix = super.getUniformLocation("projectionMatrix");
        location_ViewMatrix = super.getUniformLocation("viewMatrix");
        location_lightColor = super.getUniformLocation("lightColor");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_useFaketLighting = super.getUniformLocation("useFakeLighting");
        location_skyColor = super.getUniformLocation("skyColor");
    }

    public void loadSkyColor(float r, float g, float b){
        super.loadVector(location_skyColor, new Vector3f(r,g,b));
    }

    public void loadUseFakeLighting(boolean useFake){
        super.loadBoolean(location_useFaketLighting, useFake);
    }

    public void loadShineVariables(float damper, float reflectivity){
        super.loadFloat(location_reflectivity, reflectivity);
        super.loadFloat(location_shineDamper, damper);
    }

    public void loadTransformationMatrix(Matrix4f matrix) {

        super.loadMatrix(location_TransformationMatrix, matrix);
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColor, light.getColor());
    }

    public void loadProjectionMatrix(Matrix4f projection) {

        super.loadMatrix(location_ProjectionMatrix, projection);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_ViewMatrix, viewMatrix);
    }
}
