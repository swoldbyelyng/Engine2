package Models;

import Textures.ModelTexture;

public class TexturedModel {

    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture modelTexture){
        this.rawModel = rawModel;
        this.texture = modelTexture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public void setTexture(ModelTexture texture) {
        this.texture = texture;
    }
}
