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
        camera.setToOrtho(false, CamScale.x / PPM / 2f, CamScale.y / PPM / 2f);
    }

    public void update(float delta){
        Vector3 position = camera.position;
        position.x = player.body.getBody().getPosition().x;
        position.y = player.body.getBody().getPosition().y;
        camera.position.set(position);

        camera.update();
    }
}
