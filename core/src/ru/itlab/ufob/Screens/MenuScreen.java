package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;


import ru.itlab.ufob.Utils.Constants;

public class MenuScreen implements Screen {

    Texture texture, tB1, tB2, tB3, tBS;
    SpriteBatch batch;
    float scale, drawX, btnScale, height, width;
    public Rectangle rectBtn1, rectBtn2, rectBtn3, rectBtnSet;

    Stage stage;
    Camera camera;
    public int screen;

    @Override
    public void show(){
        batch = new SpriteBatch();
        texture = new Texture("masterpiece.png");
        scale = Gdx.graphics.getHeight() * texture.getWidth() / texture.getHeight();
        drawX = (scale - Gdx.graphics.getWidth()) / 2 * -1;
        Constants.DrawX = drawX;
        Constants.Scale = scale;

        //посчитать координаты кнопок и текстуры
        tB1 = new Texture("buttons/tutorBtn.png");
        tB2 = new Texture("buttons/recordsBtn.png");
        tB3 = new Texture("buttons/playBtn.png");
        tBS = new Texture("buttons/setBtn.png");

        btnScale = tB1.getWidth() / tB1.getHeight();
        height = Gdx.graphics.getHeight()/6f;
        width = Gdx.graphics.getWidth()/2f - btnScale*height/2f;

        rectBtn1 = new Rectangle(width,0, btnScale*height,height);
        rectBtn2 = new Rectangle(width,height,btnScale*height,height);
        rectBtn3 = new Rectangle(width,height*2f,btnScale*height,height);
        rectBtnSet = new Rectangle(0,0,height, height);

        stage = new Stage();
        camera = stage.getCamera();
        screen = 0;
    }

    @Override
    public void render(float delta){
        buttons();
        //Render
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture,
                drawX,
                0,
                scale,
                Gdx.graphics.getHeight());
        draw(batch, tB1, rectBtn1);
        draw(batch, tB2, rectBtn2);
        draw(batch, tB3, rectBtn3);
        draw(batch, tBS, rectBtnSet);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    public void draw(SpriteBatch batch, Texture texture, Rectangle rect){
        batch.draw(texture,
                rect.x,
                rect.y,
                rect.width,
                rect.height);
    }

    public void buttons() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            //Кнопка обучения
            if (touchPos.x > rectBtn1.x && touchPos.x < rectBtn1.x + rectBtn1.width &&
                    touchPos.y > rectBtn1.y && touchPos.y < rectBtn1.y + rectBtn1.height) {
                screen = 3;
            }
            //Кнопка таблицы результатов
            else if (touchPos.x > rectBtn2.x && touchPos.x < rectBtn2.x + rectBtn2.width &&
                    touchPos.y > rectBtn2.y && touchPos.y < rectBtn2.y + rectBtn2.height) {
                screen = 2;
            }
            //Кнопка старта
            else if (touchPos.x > rectBtn3.x && touchPos.x < rectBtn3.x + rectBtn3.width &&
                    touchPos.y > rectBtn3.y && touchPos.y < rectBtn3.y + rectBtn3.height) {
                screen = 1;
            }
            //Кнопка настроек
            else if (touchPos.x > rectBtnSet.x && touchPos.x < rectBtnSet.x + rectBtnSet.width &&
                    touchPos.y > rectBtnSet.y && touchPos.y < rectBtnSet.y + rectBtnSet.height) {
                screen = 4;
            }
        }
    }
}
