package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import ru.itlab.ufob.SpecialClasses.DialogWindow;
import ru.itlab.ufob.SpecialClasses.MyDialogWindow;

public class SettingsScreen implements Screen {

    float height;
    public static Stage stage;
    Skin skin;
    TextureAtlas atlas;
    static DialogWindow dw;
    ResultsScreen rs;
    static MyDialogWindow mydw;
    public static boolean rend = false;

    @Override
    public void show() {
        stage = new Stage();
        atlas = new TextureAtlas(Gdx.files.internal("Settings/set.pack"));
        skin = new Skin(atlas);

        height = Gdx.graphics.getHeight()/6f;
        createImageButton(Gdx.graphics.getWidth()/2-height/2,Gdx.graphics.getHeight()-height,
                height,height,"rename");
        createImageButton(Gdx.graphics.getWidth()/2-height/2,Gdx.graphics.getHeight()-height*2,
                height,height,"delete");

        rs = new ResultsScreen();
        dw = new DialogWindow(rs);

        mydw = new MyDialogWindow();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        if(rend)mydw.render();
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
        mydw.dispose();
    }

    public void createImageButton(float x, float y, float width, float height, String text){
        ImageButton.ImageButtonStyle imageButtonStyle;
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetY = -1;
        imageButtonStyle.up = skin.getDrawable(text);
        ImageButton imageButton = new ImageButton(imageButtonStyle);
        imageButton.setSize(width, height);
        imageButton.setPosition(x, y);
        imageButton.setName(text);
        //btn.setTransform(true);
        //btn.setScale(0.1f);

        imageButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SettingsScreen.choose(actor);
            }
        });
        stage.addActor(imageButton);
    }

    public static void choose(Actor actor){
        String text = actor.getName();
        if(text.equals("rename")){
            Gdx.input.getTextInput(dw, "Enter your name for Records", "", "Enter Your Name");
        }
        if(text.equals("delete")){
            rend = true;
            mydw.create();
        }
    }
}
