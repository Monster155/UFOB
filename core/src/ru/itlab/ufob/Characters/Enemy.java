package ru.itlab.ufob.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Objects;

import ru.itlab.ufob.Utils.Constants;
import ru.itlab.ufob.Utils.Utils;

import static ru.itlab.ufob.Utils.Constants.E_SPEED;
import static ru.itlab.ufob.Utils.Constants.SIZE;

public class Enemy {

    public Fixture body;
    World world;
    Texture texture;
    public Vector2 rot = new Vector2(0,0);
    public boolean inGame = true;
    public int live = 5;
    String path;
    public long change = TimeUtils.nanoTime() - 10;

    public Enemy(World world, Vector2 pos){
        this.world = world;
        body = Utils.createBox(world, rand(pos), SIZE.x, SIZE.y,
                false, "enemy", (short) -1);
        switch((int)(Math.random()*4+1)){
            case 1:path = "PNG/green";break;
            case 2:path = "PNG/pink";break;
            case 3:path = "PNG/sand";break;
            case 4:path = "PNG/yellow";break;
        }
        Gdx.app.log("Path for Enemy", path+"");
        texture = new Texture(path + "1.png");
    }

    public void update(float delta, Vector2 pos){
        if(MathUtils.nanoToSec*(TimeUtils.nanoTime()-change) > (int)(Math.random()*3+2)) {
            calcRot(pos);
            change = TimeUtils.nanoTime();
        }
        body.getBody().setLinearVelocity(delta*E_SPEED*rot.x, delta*E_SPEED*rot.y);
        if(!inGame){
            world.destroyBody(body.getBody());
            body = null;
            texture.dispose();
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture,
                body.getBody().getPosition().x - SIZE.x/2,
                body.getBody().getPosition().y - SIZE.y/2,
                SIZE.x,
                SIZE.y);
    }

    public Vector2 rand(Vector2 pos){
        float x,y;
        //Размеры смотреть в логах - самый первые строки, они описываны в TiledObjectUtil
        float ax = 25, bx = 75, ay = 25, by = 75;
        do
            x = ax + (float)(Math.random() * (bx-ax));
        while(pos.x+SIZE.x*2 > x && pos.x-SIZE.x*2 < x);
        do
            y = ay + (float)(Math.random() * (by-ay));
        while(pos.y+SIZE.y*2 > y && pos.y-SIZE.y*2 < y);
        return new Vector2(x, y);
    }

    public void calcRot(Vector2 pos){
        float x = body.getBody().getPosition().x, y = body.getBody().getPosition().y;
        if(x > pos.x+SIZE.x)
            rot.x = -1;
        else if(x < pos.x-SIZE.x)
            rot.x = 1;
        else rot.x = 0;

        if(y > pos.y+SIZE.y)
            rot.y = -1;
        else if(y < pos.y-SIZE.y)
            rot.y = 1;
        else rot.y = 0;

        if(rot.x == 0 && rot.y == 0){
            if(x > pos.x)
                rot.x = -1;
            else if(x < pos.x)
                rot.x = 1;
            else rot.x = 0;

            if(y > pos.y)
                rot.y = -1;
            else if(y < pos.y)
                rot.y = 1;
            else rot.y = 0;
        }
    }

    public void damaged(){
        live--;
        if(live > 6)
            texture = new Texture(path+"1.png");
        else if(live > 3)
            texture = new Texture(path+"2.png");
        else if(live > 0)
            texture = new Texture(path+"3.png");
        else inGame = false;
    }
}
