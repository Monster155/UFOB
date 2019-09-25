package ru.itlab.ufob.SpecialClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;

import ru.itlab.ufob.Screens.MenuScreen;

public class TypesOfLevels {

    public boolean isRespawn = false;
    SelectBox selectBox;

    public TypesOfLevels() {
        selectBox = MenuScreen.stage.getRoot().findActor("SelectBox");
        switch (selectBox.getSelectedIndex()){
            case 0://Infinity
                isRespawn = true;
                break;
            case 1://Levels
                isRespawn = false;
                break;
        }
        Gdx.app.log("Add", ""+selectBox.getSelectedIndex());
    }
}
