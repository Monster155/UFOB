package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import static ru.itlab.ufob.Utils.Constants.LIVES;

public class MainActivity extends Game {

    public GameScreen gs;
    public SettingsScreen ss;
    public GameOverScreen gos;
    public MenuScreen ms;
    public TutorialScreen ts;
    public ResultsScreen rs;
    Music mainMusic, GOMusic;
    long time = TimeUtils.nanoTime();
    long tutor;

    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);

        gs = new GameScreen();
        ms = new MenuScreen();
        gos = new GameOverScreen();
        ts = new TutorialScreen();
        rs = new ResultsScreen();
        ss = new SettingsScreen();
        GOMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Title theme.mp3"));
        mainMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Abandon ship.mp3"));
        music(true, mainMusic);
        setScreen(ms);
    }

    @Override
    public void render() {
        super.render();
        if(getScreen() == ms && ms.screen != 0){
            switch (ms.screen){
                case 1:
                    setScreen(gs);
                    ms.dispose();
                    music(false, mainMusic);
                    music(true, GOMusic);
                    Gdx.app.log("ChangeScreen", "GameScreen");
                    break;
                case 2:
                    setScreen(rs);
                    ms.dispose();
                    Gdx.app.log("ChangeScreen", "ResultsScreen");
                    break;
                case 3:
                    setScreen(ts);
                    ms.dispose();
                    Gdx.app.log("ChangeScreen", "TutorialScreen");
                    break;
                case 4:
                    setScreen(ss);
                    ms.dispose();
                    Gdx.app.log("ChangeScreen", "SettingsScreen");
                    break;
            }
            ms.screen = 0;
            tutor = TimeUtils.nanoTime();
        }
        if ( Gdx.input.isKeyPressed(Input.Keys.BACK) && getScreen() == gs) {
            setScreen(gos);
            gs.dispose();
            music(false, GOMusic);
            music(true, mainMusic);
        }
        if((Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.BACK))
                && getScreen().equals(ts) && MathUtils.nanoToSec * (TimeUtils.nanoTime() - tutor) > 1f) {
            setScreen(ms);
            ts.dispose();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACK) && getScreen().equals(rs)){
            setScreen(ms);
            rs.dispose();
        }
        if ((Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.BACK))
                && getScreen() == gos && MathUtils.nanoToSec * (TimeUtils.nanoTime() - time)*2f > 1f) {
            Gdx.app.log("MainActivity", "setScreen = ms");
            setScreen(ms);
            gos.dispose();
        }
        if (LIVES <= 0) {
            Gdx.app.log("Screens", "GameOverScreen");
            setScreen(gos);
            gs.dispose();
            time = TimeUtils.nanoTime();
        }
    }

    public void music(boolean begin, Music music) {
        if (begin) {
            music.setLooping(true);
            music.play();
        } else {
            music.stop();
        }
    }
}
