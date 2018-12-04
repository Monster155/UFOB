package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.itlab.ufob.SpecialClasses.DialogWindow;

public class SettingsScreen implements Screen {

    Texture tB1;
    Rectangle rB1;
    float width, height;
    SpriteBatch batch;

    Stage stage;
    Camera camera;

    DialogWindow dw;
    ResultsScreen rs;

    @Override
    public void show() {
        tB1 = new Texture("Settings/rename.png");//TODO
        height = Gdx.graphics.getHeight()/6f;
        width = Gdx.graphics.getWidth()/2f - tB1.getWidth() / tB1.getHeight()*height/2f;
        rB1 = new Rectangle(width, Gdx.graphics.getHeight()-height, tB1.getWidth() / tB1.getHeight()*height, height);
        batch = new SpriteBatch();

        stage = new Stage();
        camera = stage.getCamera();

        rs = new ResultsScreen();
        dw = new DialogWindow(rs);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(tB1,
                rB1.x,
                rB1.y,
                rB1.width,
                rB1.height);
        batch.end();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x > rB1.x && touchPos.x < rB1.x + rB1.width &&
                    touchPos.y > rB1.y && touchPos.y < rB1.y + rB1.height) {
                Gdx.input.getTextInput(dw, "Enter your name for Records", "", "Enter Your Name");
            }
        }
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

    }
}
