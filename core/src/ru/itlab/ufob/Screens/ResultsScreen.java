package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ru.itlab.ufob.SpecialClasses.DialogWindow;

import static ru.itlab.ufob.Utils.Constants.SCORE;

public class ResultsScreen implements Screen{

    Preferences prefs;
    public static String name = "Player 1";
    String nums[] = new String[]{"First", "Second", "Third", "Fourth", "Fifth",
            "Sixth", "Seventh", "Eighth", "Ninth", "Tenth"};

    SpriteBatch batch;
    Vector2 pos;
    BitmapFont font;
    GlyphLayout glyphLayout;
    float x1, y1, x2, y2;

    public ResultsScreen(){
        prefs = Gdx.app.getPreferences("Preferences");
        if((prefs.getLong("First"+"s", 0)) == 0)
            generatePrefs();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        pos = new Vector2(0,0);
        font = new BitmapFont(Gdx.files.internal("data/font.fnt"));
        font.getData().setScale(Gdx.graphics.getWidth() / 2560f);

        glyphLayout = new GlyphLayout();

        glyphLayout.setText(font, "TheJoker");
        x1 = glyphLayout.width;
        y1 = glyphLayout.height;
        glyphLayout.setText(font, "150");
        x2 = glyphLayout.width;
        y2 = glyphLayout.height;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for(int i = 0; i < nums.length; i++) {
            font.draw(batch,
                    prefs.getString(nums[i]) + "",
                    50,
                    Gdx.graphics.getHeight() - y1*(i+1));
            font.draw(batch,
                    prefs.getLong(nums[i] + "s") + "",
                    Gdx.graphics.getWidth()-x2,
                    Gdx.graphics.getHeight() - y2*(i+1));

        }
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

    }

    public void saveResults() {
        int newPlace = 9;
        for(int i = 0; i < nums.length; i++) {
            if(prefs.getLong(nums[i]+"s", 0) < SCORE){
                newPlace = i;
                break;
            }
        }
        for(int i = nums.length-1; i > newPlace; i--) {
            prefs.putLong(nums[i]+"s", prefs.getLong(nums[i-1]+"s")).flush();
            prefs.putString(nums[i], prefs.getString(nums[i-1])).flush();
        }
        prefs.putLong(nums[newPlace]+"s", SCORE).flush();
        prefs.putString(nums[newPlace], name).flush();
        for(int i = 0; i < nums.length; i++)
            Gdx.app.log("Prefs", prefs.getLong(nums[i]+"s")+" "+prefs.getString(nums[i]));
    }

    public void generatePrefs(){
        DialogWindow dw = new DialogWindow(this);
        Gdx.input.getTextInput(dw, "Enter your name for Records", "", "Enter Your Name");

        prefs.putLong(nums[0]+"s", 135).flush();
        prefs.putString(nums[0], "Bulat").flush();
        prefs.putLong(nums[1]+"s", 114).flush();
        prefs.putString(nums[1], "Leonid").flush();
        prefs.putLong(nums[2]+"s", 105).flush();
        prefs.putString(nums[2], "Damir").flush();
        prefs.putLong(nums[3]+"s", 94).flush();
        prefs.putString(nums[3], "Kamilya").flush();
        prefs.putLong(nums[4]+"s", 87).flush();
        prefs.putString(nums[4], "Andrey").flush();
        prefs.putLong(nums[5]+"s", 73).flush();
        prefs.putString(nums[5], "Katya").flush();
        prefs.putLong(nums[6]+"s", 60).flush();
        prefs.putString(nums[6], "Amir").flush();
        prefs.putLong(nums[7]+"s", 59).flush();
        prefs.putString(nums[7], "Kirill").flush();
        prefs.putLong(nums[8]+"s", 47).flush();
        prefs.putString(nums[8], "Ayrat").flush();
    }
    public void contin(){
        prefs.putLong(nums[9]+"s", 0).flush();
        prefs.putString(nums[9], name+"").flush();
    }
}
