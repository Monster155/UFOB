package ru.itlab.ufob.SpecialClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static ru.itlab.ufob.Utils.Constants.SCORE;

public class UI {
    double time;
    float xScore;
    BitmapFont font;
    GlyphLayout glyphLayoutScore, glyphLayoutTime;

    public UI(){
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(Gdx.graphics.getWidth() / 1280f);

        glyphLayoutScore = new GlyphLayout();
        glyphLayoutTime = new GlyphLayout();

        glyphLayoutScore.setText(font, "Your score: XXX");
        xScore = glyphLayoutScore.width;

        glyphLayoutScore.setText(font, "Your score: 0");
        glyphLayoutTime.setText(font, "Your time: 0");
    }

    public void act(float delta){
        time+=delta;
        glyphLayoutScore.setText(font, "Your score: "+SCORE);
        glyphLayoutTime.setText(font, "Your time: "+(int)time);
    }

    public void draw(SpriteBatch batch){
        font.draw(batch, //right position
                glyphLayoutScore,
                Gdx.graphics.getWidth() - xScore,
                Gdx.graphics.getHeight() - glyphLayoutScore.height);
        font.draw(batch, //center position (left - lives)
                glyphLayoutTime,
                Gdx.graphics.getWidth()/2 - glyphLayoutTime.width/2,
                Gdx.graphics.getHeight() - glyphLayoutTime.height);
    }

    public void dispose(){
        font.dispose();
        glyphLayoutScore.reset();
        glyphLayoutTime.reset();
    }

}
