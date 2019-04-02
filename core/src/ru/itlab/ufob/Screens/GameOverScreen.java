package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.itlab.ufob.Utils.Constants;

import static ru.itlab.ufob.Utils.Constants.SCORE;

public class GameOverScreen implements Screen {

    SpriteBatch batch;
    BitmapFont font;
    Texture texture;
    GlyphLayout glyphLayout;
    float x1, x2, y1, y2;
    ResultsScreen rs;

    @Override
    public void show() {
        rs = new ResultsScreen();
        rs.saveResults();

        batch = new SpriteBatch();
        texture = new Texture("endScreen.png");

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));

        font.getData().setScale(Gdx.graphics.getWidth() / 1280f);

        glyphLayout = new GlyphLayout();

        glyphLayout.setText(font, "Your score: XXX");
        x1 = Gdx.graphics.getWidth()/2f - glyphLayout.width/2f;
        y1 = Constants.CamScale.y / 8;
        glyphLayout.setText(font, "Tap screen to continue!");
        x2 = Gdx.graphics.getWidth()/2f - glyphLayout.width/2f;
        y2 = Constants.CamScale.y / 16;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture,
                Constants.DrawX,
                0,
                Constants.Scale,
                Gdx.graphics.getHeight());
        font.draw(batch, "Your score: "+SCORE, x1, y1);
        //font.draw(batch, "Tap screen to continue!", x2, y2);
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
        font.dispose();
        texture.dispose();
        glyphLayout.reset();
    }
}
