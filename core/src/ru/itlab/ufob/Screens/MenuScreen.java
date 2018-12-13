package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


import ru.itlab.ufob.Utils.Constants;

public class MenuScreen implements Screen {

    Texture texture;
    TextureAtlas atlas;
    Skin skin;
    Stage stage;
    BitmapFont font;
    public int screen;
    float btnScale, width, height;
    TextButton.TextButtonStyle textButtonStyle;
    ImageButton.ImageButtonStyle imageButtonStyle;

    @Override
    public void show(){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        texture = new Texture("masterpiece.png");
        Constants.Scale = Gdx.graphics.getHeight() * texture.getWidth() / texture.getHeight();
        Constants.DrawX = (Constants.Scale - Gdx.graphics.getWidth()) / 2 * -1;

        atlas = new TextureAtlas(Gdx.files.internal("buttons/button.pack"));
        skin = new Skin(atlas);
        font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        font.getData().setScale(0.3f); //размер текста не изменяется (всегда 640)

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = font;

        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetY = -1;

        btnScale = 5;
        height = Gdx.graphics.getHeight()/6f;
        width = Gdx.graphics.getWidth()/2f - btnScale*height/2f;

        createTextButton(width,0, btnScale*height,height,"tutorial");
        createTextButton(width,height,btnScale*height,height,"records");
        createTextButton(width,height*2f,btnScale*height,height,"play");
        createImageButton(0,0,height, height,"settings");

        screen = 0;
    }

    @Override
    public void render(float delta){
        stage.act();
        //Render
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(texture,
                Constants.DrawX,
                0,
                Constants.Scale,
                Gdx.graphics.getHeight());
        stage.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();
        atlas.dispose();
        skin.dispose();
        font.dispose();
    }

    public void createTextButton(float x, float y, float width, float height, final String text){
        textButtonStyle.up = skin.getDrawable(text);
        TextButton textButton = new TextButton(text, textButtonStyle);
        textButton.setSize(width, height);
        textButton.setPosition(x, y);
        //btn.setTransform(true);
        //btn.setScale(0.1f);

        textButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
                if(text.equals("play"))screen = 1;
                if(text.equals("records"))screen = 2;
                if(text.equals("tutorial"))screen = 3;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                return true;
            }
        });

        stage.addActor(textButton);
    }

    public void createImageButton(float x, float y, float width, float height, final String text){
        imageButtonStyle.up = skin.getDrawable(text);
        ImageButton imageButton = new ImageButton(imageButtonStyle);
    }
}
