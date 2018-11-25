package ru.itlab.ufob.SpecialClasses;

import com.badlogic.gdx.Input;

import ru.itlab.ufob.Screens.ResultsScreen;

public class DialogWindow implements Input.TextInputListener{

    ResultsScreen rs;

    public DialogWindow(ResultsScreen rs){
        this.rs = rs;
    }

    @Override
    public void input(String text) {
        rs.name = text;
        rs.contin();
    }

    @Override
    public void canceled() {
        rs.contin();
    }
}
