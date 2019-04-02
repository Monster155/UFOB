package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ru.itlab.ufob.SpecialClasses.MyDialogWindow;
import ru.itlab.ufob.SpecialClasses.SoundSystem;

public class SettingsScreen implements Screen {

    float height;
    public static Stage stage;
    Skin skin;
    TextureAtlas atlas;
    static MyDialogWindow mydw;
    Preferences prefs;
    public static boolean rend = false;
    SoundSystem soundSys;
    Table table;

    public SettingsScreen(SoundSystem soundSystem){
        soundSys = soundSystem;
    }

    @Override
    public void show() {
        stage = new Stage();
        atlas = new TextureAtlas(Gdx.files.internal("Settings/set.pack"));
        skin = new Skin(atlas);
        table = new Table();

        height = Gdx.graphics.getHeight()/6f;
        createDialogWindowButton(Gdx.graphics.getWidth()/2-height/2,Gdx.graphics.getHeight()-height,
                height,height,"rename");
        createDialogWindowButton(Gdx.graphics.getWidth()/2-height/2,Gdx.graphics.getHeight()-height*2,
                height,height,"delete");
        createBooleanButton(Gdx.graphics.getWidth()/2-height/2,Gdx.graphics.getHeight()-height*3,
                height,height,"music");
        createBooleanButton(Gdx.graphics.getWidth()/2-height/2,Gdx.graphics.getHeight()-height*4,
                height,height,"sound");

        mydw = new MyDialogWindow();
        //stage.addActor(table);
        //stage.setViewport(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight())));
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
        //mydw.dispose();
    }

    public void createDialogWindowButton(float x, float y, float width, float height, String text){
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
                SettingsScreen.mydw.create(actor.getName(), stage);
            }
        });
        //table.add(imageButton);
        stage.addActor(imageButton);
    }

    public void createBooleanButton(float x, float y, float width, float height, String text){
        ImageButton.ImageButtonStyle imageButtonStyle;
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.pressedOffsetY = -1;
        ImageButton imageButton = new ImageButton(imageButtonStyle);
        imageButton.setSize(width, height);
        imageButton.setPosition(x, y);
        prefs = Gdx.app.getPreferences("Settings");
        //btn.setTransform(true);
        //btn.setScale(0.1f);

        imageButton.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Preferences prefs = Gdx.app.getPreferences("Settings");
                if(actor.getName().equals("music")) {
                    prefs.putBoolean("music", !prefs.getBoolean("music")).flush();
                    soundSys.playSound("playTheme", true);
                }
                if(actor.getName().equals("sound"))
                    prefs.putBoolean("sound", !prefs.getBoolean("sound")).flush();
                Gdx.app.log("Settings", actor.getName()+" changed");
                updateBooleanButton((ImageButton) actor, actor.getName());
            }
        });
        updateBooleanButton(imageButton, text);
        //table.add(imageButton);
        stage.addActor(imageButton);
    }
    public void updateBooleanButton(ImageButton button, String text){
        if(prefs.getBoolean(text))
            button.getStyle().up = skin.getDrawable(text+"On");
        else button.getStyle().up = skin.getDrawable(text+"Off");
        button.setName(text);
    }
}
