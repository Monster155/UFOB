package ru.itlab.ufob.SpecialClasses;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import ru.itlab.ufob.Screens.ResultsScreen;
import ru.itlab.ufob.Screens.SettingsScreen;

public class MyDialogWindow extends ApplicationAdapter{

    Dialog dialog;
    Skin skin;
    Stage stage;
    TextureAtlas atlas;
    TextField textField;

    public void create(String name) {
        atlas = new TextureAtlas(Gdx.files.internal("uiskin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin/skin.json"), atlas);
        stage = new Stage();
        createDialog(name);
        dialog.scaleBy(Gdx.graphics.getHeight() / 640 * 1.5f);
        dialog.setPosition(Gdx.graphics.getWidth()/2-dialog.getWidth()/2, Gdx.graphics.getHeight()/2-dialog.getWidth()/2);
        dialog.show(stage);
        stage.setKeyboardFocus(textField);
        textField = (TextField) stage.getKeyboardFocus();
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

    void createDialog(String name){
        if(name.equals("rename")){
            SettingsScreen.rend = true;
            textField = new TextField("", skin);
            dialog = new Dialog("Change your name", skin) {
                protected void result(Object object) {
                    if (object.equals(1L)) {
                        System.out.println("OK");
                        Gdx.input.setInputProcessor(SettingsScreen.stage);
                        ResultsScreen.name = textField.getText(); //Всё работает, кроме этого присваивания
                        SettingsScreen.rend = false;
                    } else {
                        System.out.println("Cancel");
                        Gdx.input.setInputProcessor(SettingsScreen.stage);
                        SettingsScreen.rend = false;
                    }
                }
            };
            dialog.text("All your previous records will save by your previous name");
            dialog.addActor(textField);
            dialog.button("OK", 1L);
            dialog.button("Cancel", 2L);
        }
        if(name.equals("delete")){
            SettingsScreen.rend = true;
            dialog = new Dialog("Do you want to reset records?", skin) {
                protected void result(Object object) {
                    if (object.equals(1L)) {
                        System.out.println("Yes");
                        Gdx.input.setInputProcessor(SettingsScreen.stage);
                        SettingsScreen.rend = false;
                        ResultsScreen.removePrefs();
                    } else {
                        System.out.println("No");
                        Gdx.input.setInputProcessor(SettingsScreen.stage);
                        SettingsScreen.rend = false;
                    }
                }
            };
            dialog.text("You can't recover your records!");
            dialog.button("Yes", 1L);
            dialog.button("No", 2L);
        }
        if(name.equals("start")){
            textField = new TextField("", skin);
            dialog = new Dialog("Enter your name", skin) {
                protected void result(Object object) {
                    if (object.equals(1L)) {
                        System.out.println("OK");
                        Gdx.input.setInputProcessor(SettingsScreen.stage);
                        ResultsScreen.name = textField.getText();
                        SettingsScreen.rend = false;
                    } else {
                        System.out.println("Cancel");
                        Gdx.input.setInputProcessor(SettingsScreen.stage);
                        SettingsScreen.rend = false;
                    }
                }
            };
            dialog.text("All your records will save by this name");
            dialog.addActor(textField);
            dialog.button("OK", 1L);
            dialog.button("Cancel", 2L);
        }
    }
}
