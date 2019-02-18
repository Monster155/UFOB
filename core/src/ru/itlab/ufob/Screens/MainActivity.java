package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import ru.itlab.ufob.SpecialClasses.MyDialogWindow;
import ru.itlab.ufob.SpecialClasses.SoundSystem;

import static ru.itlab.ufob.Utils.Constants.LIVES;
//TODO: AdMob
public class MainActivity extends Game {

    public GameScreen gs;
    public SettingsScreen ss;
    public GameOverScreen gos;
    public MenuScreen ms;
    public TutorialScreen ts;
    public ResultsScreen rs;
    Music mainMusic, GOMusic;
    long tutor;
    long time;
    Preferences prefs;
    SoundSystem soundSystem;

    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true);
        gs = new GameScreen();
        ms = new MenuScreen();
        gos = new GameOverScreen();
        ts = new TutorialScreen();
        rs = new ResultsScreen();
        soundSystem = new SoundSystem();
        ss = new SettingsScreen(soundSystem);
        prefs = Gdx.app.getPreferences("Settings");
        prefs.putBoolean("playTheme", true).flush(); // true for Menu Music
        soundSystem.playSound("playTheme", true);
        setScreen(ms);
    }

    @Override
    public void render() {
        super.render();
        if(getScreen().equals(ms) && ms.screen != 0){
            switch (ms.screen){
                case 1:
                    setScreen(gs);
                    ms.dispose();
                    prefs.putBoolean("playTheme", false).flush(); // true for Menu Music
                    soundSystem.playSound("playTheme", true);
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
        if(isEscape() && getScreen().equals(gs)) {
            setScreen(gos);
            gs.dispose();
            prefs.putBoolean("playTheme", true).flush(); // true for Menu Music
            soundSystem.playSound("playTheme", true);
            time = TimeUtils.nanoTime();
        }
        if((Gdx.input.isTouched() || isEscape()) && getScreen().equals(ts)
                && MathUtils.nanoToSec * (TimeUtils.nanoTime() - tutor) > 0.5f) {
            setScreen(ms);
            ts.dispose();
        }
        if(isEscape() && getScreen().equals(rs)){
            setScreen(ms);
            rs.dispose();
        }
        if ((Gdx.input.isTouched() || isEscape()) && getScreen().equals(gos)
                && MathUtils.nanoToSec * (TimeUtils.nanoTime() - time) > 1f) {
            Gdx.app.log("MainActivity", "setScreen = ms");
            setScreen(ms);
            gos.dispose();
        }
        if (LIVES <= 0) {
            Gdx.app.log("Screens", "GameOverScreen");
            setScreen(gos);
            gs.dispose();
            prefs.putBoolean("playTheme", true).flush(); // true for Menu Music
            soundSystem.playSound("playTheme", true);
            time = TimeUtils.nanoTime();
        }
        if(isEscape() && getScreen().equals(ss)){
            setScreen(ms);
            ss.dispose();
        }
    }

    public boolean isEscape(){
        return (Gdx.input.isKeyPressed(Input.Keys.BACK)
                || Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)
                || Gdx.input.isKeyPressed(Input.Keys.ESCAPE));
    }
}