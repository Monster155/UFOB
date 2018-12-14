package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ru.itlab.ufob.SpecialClasses.DialogWindow;

public class SettingsScreen implements Screen {

    float height;
    Stage stage;
    Skin skin;
    TextureAtlas atlas;
    DialogWindow dw;
    ResultsScreen rs;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas(Gdx.files.internal("Settings/set.pack"));
        skin = new Skin(atlas);

        height = Gdx.graphics.getHeight()/6f;
        createImageButton(Gdx.graphics.getWidth()/2-height/2,Gdx.graphics.getHeight()-height,
                height,height,"rename");

        rs = new ResultsScreen();
        dw = new DialogWindow(rs);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
        stage.dispose();
    }

    public void createImageButton(float x, float y, float width, float height, String text){
        ImageButton.ImageButtonStyle imageButtonStyle;
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetY = -1;
        imageButtonStyle.up = skin.getDrawable(text);
        ImageButton imageButton = new ImageButton(imageButtonStyle);
        imageButton.setSize(width, height);
        imageButton.setPosition(x, y);
        //btn.setTransform(true);
        //btn.setScale(0.1f);

        imageButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.getTextInput(dw, "Enter your name for Records", "", "Enter Your Name");
            }
        });
        stage.addActor(imageButton);
    }
}
