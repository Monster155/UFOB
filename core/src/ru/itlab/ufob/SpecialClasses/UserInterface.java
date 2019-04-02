package ru.itlab.ufob.SpecialClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.ufob.Utils.Constants;

import static java.lang.Math.hypot;
import static ru.itlab.ufob.Utils.Constants.LIVES;
import static ru.itlab.ufob.Utils.Constants.MAXLIVES;
import static ru.itlab.ufob.Utils.Constants.MaxEnemy;
import static ru.itlab.ufob.Utils.Constants.PPM;
import static ru.itlab.ufob.Utils.Constants.SCORE;
import static ru.itlab.ufob.Utils.Constants.SIZE;

public class UserInterface {

    Camera camera;
    Vector3 touchPos/*For Left*/, touchPos2/*For Right*/;
    Vector2 ciSize/*For Circle(white)*/, cuSize/*For Cursor(red)*/;

    float scale;//For different screen sizes
    int fingers = 4;//max num of fingers on the screen

    String lifePath = "lifes/hudHeart_";
    private Texture circleTex, cursorTex, lifeTextures[];
    private Sprite circleSp, cursorSp;

    double time;
    float xScore;
    BitmapFont font;
    GlyphLayout glyphLayout;

    public UserInterface(Camera camera) {
        this.camera = camera;
        circleTex = new Texture("Controller/joystick.png");
        cursorTex = new Texture("Controller/button.png");

        circleSp = new Sprite(circleTex);
        cursorSp = new Sprite(cursorTex);

        scale = Gdx.graphics.getHeight()/2;

        touchPos = new Vector3(scale/2, scale/2, 0);
        touchPos2 = new Vector3(Gdx.graphics.getWidth()-scale/2,scale/2, 0);

        ciSize = new Vector2(scale, scale);
        cuSize = new Vector2(scale/(circleTex.getWidth()/cursorTex.getWidth()), scale/(circleTex.getHeight()/cursorTex.getHeight()));

        lifeTextures = new Texture[MAXLIVES/2];
        for(int i = 0; i < lifeTextures.length; i++)
            lifeTextures[i] = new Texture(lifePath+"full.png");

        //score and time
        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.getData().setScale(Gdx.graphics.getWidth() / 2560f);

        glyphLayout = new GlyphLayout();

        glyphLayout.setText(font, "Your score: XXX");
        xScore = glyphLayout.width;
    }

    public void act(float delta) {
        touchPos = new Vector3(scale/2, scale/2, 0);
        Constants.stickCS = new Vector2(0,0);
        touchPos2 = new Vector3(Gdx.graphics.getWidth()-scale/2,scale/2,0);
        Constants.gunCS = new Vector2(0,0);

        for (int i = 0; i < fingers; i++) {
            if (Gdx.input.isTouched(i)) {
                if(Gdx.input.getX(i) < Gdx.graphics.getWidth()/2) { //Left joystick
                    touchPos.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                    camera.unproject(touchPos);
                    Vector2 tP = new Vector2(touchPos.x - ciSize.x/2, touchPos.y - ciSize.y/2);
                    /*float x = 0, y = 0;
                    //right & left
                    if (touchPos.x > ciSize.x/2)
                        x++;
                    else if(touchPos.x < ciSize.x/2)
                        x--;

                    //up & down
                    if (touchPos.y > ciSize.y/2)
                        y++;
                    else if (touchPos.y < ciSize.y/2)
                        y--;*/

                    float cosX = (float) (tP.x / Math.hypot(tP.x, tP.y));
                    float sinY = (float) (tP.y / Math.hypot(tP.x, tP.y));

                    if(tP.x*tP.x + tP.y*tP.y > (ciSize.x-cuSize.x/1.5)*(ciSize.x-cuSize.x/1.5)/4){
                        touchPos.x = cosX * (ciSize.x/2 - cuSize.x/3) + ciSize.x/2;
                        touchPos.y = sinY * (ciSize.y/2 - cuSize.y/3) + ciSize.x/2;
                    }

                    Constants.stickCS = new Vector2(cosX, sinY);
                } else { //Right UserInterface
                    touchPos2.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                    camera.unproject(touchPos2);

                    Vector2 tP = new Vector2(touchPos2.x - Gdx.graphics.getWidth() + ciSize.x/2,touchPos2.y - ciSize.y/2);

                    /*float x = 0, y = 0;
                    //right
                    if (touchPos2.x > Gdx.graphics.getWidth()-ciSize.x/2)
                        x++;
                    else if(touchPos2.x < Gdx.graphics.getWidth() - ciSize.x/2)
                        x--;

                    //up & down
                    if (touchPos2.y > ciSize.y/2)
                        y++;
                    else if(touchPos2.y < ciSize.y/2)
                        y--;*/

                    float cosX = (float) (tP.x / Math.hypot(tP.x, tP.y));
                    float sinY = (float) (tP.y / Math.hypot(tP.x, tP.y));

                    if(tP.x*tP.x + tP.y*tP.y > (ciSize.x-cuSize.x/1.5)*(ciSize.x-cuSize.x/1.5)/4){
                        touchPos2.x = cosX * (ciSize.x/2 - cuSize.x/3) + Gdx.graphics.getWidth() - ciSize.x/2;
                        touchPos2.y = sinY * (ciSize.y/2 - cuSize.y/3) + ciSize.y/2;
                    }

                    Constants.gunCS = new Vector2(cosX, sinY);
                }
            }
        }

        ui(delta);
    }

    public void draw(Batch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.draw(circleSp, 0, 0, ciSize.x, ciSize.y);
        batch.draw(cursorSp, touchPos.x-cuSize.x/2, touchPos.y-cuSize.y/2, cuSize.x, cuSize.y);
        batch.draw(circleSp, Gdx.graphics.getWidth()-ciSize.x, 0, ciSize.x, ciSize.y);
        batch.draw(cursorSp, touchPos2.x-cuSize.x/2, touchPos2.y-cuSize.y/2, cuSize.x, cuSize.y);
        float x = lifeTextures[0].getWidth()*Gdx.graphics.getWidth()/Constants.CamScale.x,
        y = lifeTextures[0].getHeight()*Gdx.graphics.getHeight()/Constants.CamScale.y;
        for(int i = 0; i < lifeTextures.length; i++)
            batch.draw(lifeTextures[i],i*x,Gdx.graphics.getHeight()-y, x, y);

        glyphLayout.setText(font, "Your time: "+(int)time);
        font.draw(batch, //right position
                glyphLayout,
                Gdx.graphics.getWidth() - xScore,
                Gdx.graphics.getHeight() - glyphLayout.height);

        glyphLayout.setText(font, "Your score: "+SCORE);
        font.draw(batch, //center position (left - lives)
                glyphLayout,
                Gdx.graphics.getWidth()/2 - glyphLayout.width/2,
                Gdx.graphics.getHeight() - glyphLayout.height);
    }

    public void dispose() {
        circleTex.dispose();
        cursorTex.dispose();
        for(int i = 0; i < lifeTextures.length; i++)
            lifeTextures[i].dispose();
        font.dispose();
        glyphLayout.reset();
    }

    public void ui(float delta){
        //hearts
        for(int i = 0; i < LIVES/2; i++){
            lifeTextures[i].dispose();
            lifeTextures[i] = new Texture(lifePath+"full.png");
        }
        if(LIVES%2 > 0){
            lifeTextures[LIVES/2].dispose();
            lifeTextures[LIVES/2] = new Texture(lifePath+"half.png");
        }
        for(int i = LIVES/2+LIVES%2; i < MAXLIVES/2; i++){
            lifeTextures[i].dispose();
            lifeTextures[i] = new Texture(lifePath+"empty.png");
        }

        //score and time
        time+=delta;
    }
}
