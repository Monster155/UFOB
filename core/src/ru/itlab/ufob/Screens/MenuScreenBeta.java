package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.itlab.ufob.SpecialClasses.Camera;
import ru.itlab.ufob.Utils.Constants;

public class MenuScreenBeta implements Screen {

    Texture texture;
    SpriteBatch batch;
    public int screen;
    Stage stage;
    TextureAtlas atlas;
    Skin skin;
    TextButton btn, btn1;
    BitmapFont font;

    @Override
    public void show(){
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("masterpiece.png"));
        Constants.Scale = Gdx.graphics.getHeight() * texture.getWidth() / texture.getHeight();
        Constants.DrawX = (Constants.Scale - Gdx.graphics.getWidth()) / 2 * -1;
        screen = 0;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas(Gdx.files.internal("buttons/newBtn/button.pack"));
        skin = new Skin(atlas);
        font = new BitmapFont(Gdx.files.internal("data/font.fnt"));

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = font;

        btn = new TextButton("EXIT", textButtonStyle);
        btn1 = new TextButton("ENTER", textButtonStyle);
        btn.setSize(Gdx.graphics.getWidth(), btn.getHeight());
        btn.setPosition(Gdx.graphics.getWidth()/2-btn.getWidth()/2, Gdx.graphics.getHeight()/2-btn.getHeight()/2);
        //btn.setTransform(true);
        //btn.setScale(0.1f);

        stage.addActor(btn);
        stage.addActor(btn1);
    }

    @Override
    public void render(float delta){
        //Render
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        /*batch.draw(texture,
                Constants.DrawX,
                0,
                Constants.Scale,
                Gdx.graphics.getHeight());*/
        stage.act(delta);
        stage.draw();
        batch.end();
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
        batch.dispose();
        texture.dispose();
    }

    public void draw(SpriteBatch batch, Texture texture, Rectangle rect){
        batch.draw(texture,
                rect.x,
                rect.y,
                rect.width,
                rect.height);
    }
}
