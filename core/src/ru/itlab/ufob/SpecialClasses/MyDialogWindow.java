package ru.itlab.ufob.SpecialClasses;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import ru.itlab.ufob.Screens.SettingsScreen;

public class MyDialogWindow extends ApplicationAdapter{

    Dialog dialog;
    Skin skin;
    Stage stage;
    TextureAtlas atlas;

    public void create() {
        atlas = new TextureAtlas(Gdx.files.internal("uiskin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin/skin.json"), atlas);
        stage = new Stage();
        dialog = new Dialog("Do you want to reset records?", skin) {
            protected void result(Object object) {
                if (object.equals(1L)) {
                    System.out.println("Yes");
                    Gdx.input.setInputProcessor(SettingsScreen.stage);
                    SettingsScreen.rend = false;
                } else { // 2-нче тапкыр эшлэми
                    System.out.println("No");
                    Gdx.input.setInputProcessor(SettingsScreen.stage);
                    SettingsScreen.rend = false;
                }
            }
        };
        dialog.text("You can't recover your records!");
        dialog.button("Yes", 1L);
        dialog.button("No", 2L);
        /*Timer.schedule(new Task() {
            @Override
            public void run() {
                dialog.show(stage);
            }
        }, 1);*/
        dialog.scaleBy(Gdx.graphics.getHeight() / 640 * 1.5f);
        dialog.setPosition(Gdx.graphics.getWidth()/2-dialog.getWidth()/2, Gdx.graphics.getHeight()/2-dialog.getWidth()/2);
        dialog.show(stage);
    }

    public void render() {
        Gdx.input.setInputProcessor(stage);
        //Gdx.gl.glClearColor(1, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
