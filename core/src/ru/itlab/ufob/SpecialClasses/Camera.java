package ru.itlab.ufob.SpecialClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import ru.itlab.ufob.Characters.Player;

import static ru.itlab.ufob.Utils.Constants.CamScale;
import static ru.itlab.ufob.Utils.Constants.PPM;


public class Camera {
    public OrthographicCamera camera;
    Player player;

    public Camera(Player player){
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CamScale.x / PPM / 1.75f, CamScale.y / PPM / 1.75f);
    }

    public void update(){
        camera.position.set(new Vector3(player.body.getBody().getPosition(), camera.position.z));
        camera.update();
    }
}
