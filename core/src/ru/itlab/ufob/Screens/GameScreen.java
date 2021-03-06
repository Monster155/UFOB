package ru.itlab.ufob.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import ru.itlab.ufob.Characters.Bullet;
import ru.itlab.ufob.SpecialClasses.Camera;
import ru.itlab.ufob.Characters.Enemy;
import ru.itlab.ufob.SpecialClasses.LevelUpdater;
import ru.itlab.ufob.SpecialClasses.TypesOfLevels;
import ru.itlab.ufob.SpecialClasses.UserInterface;
import ru.itlab.ufob.Characters.Player;
import ru.itlab.ufob.SpecialClasses.SoundSystem;
import ru.itlab.ufob.Utils.Constants;
import ru.itlab.ufob.Utils.TiledObjectUtil;

import static ru.itlab.ufob.Utils.Constants.LIVES;
import static ru.itlab.ufob.Utils.Constants.MAXLIVES;
import static ru.itlab.ufob.Utils.Constants.MaxEnemy;
import static ru.itlab.ufob.Utils.Constants.PM;
import static ru.itlab.ufob.Utils.Constants.SCORE;
import static ru.itlab.ufob.Utils.Constants.SHOOT_RATE;

public class GameScreen implements Screen {

    SpriteBatch batch;
    Player player;
    Array<Bullet> bullets = new Array<Bullet>();
    Array<Enemy> enemies = new Array<Enemy>();
    Camera camera;
    World world;
    TiledMap map;
    OrthogonalTiledMapRenderer tmr;
    Box2DDebugRenderer b2dr;
    double lastShootTime;
    Stage stage;
    UserInterface userInterface;
    SoundSystem soundSystem;
    TypesOfLevels tol;

    @Override
    public void show() {
        tol = new TypesOfLevels();
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        lastShootTime = 0;
        world = new World(new Vector2(0,0), false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fa = contact.getFixtureA(), fb = contact.getFixtureB();
                if(fa.getUserData()==null || fb.getUserData()==null)
                    return;
                if((fa.getUserData().equals("bullet") && fb.getUserData().equals( "enemy"))
                        || (fb.getUserData().equals("bullet") && fa.getUserData().equals("enemy"))) {
                    for(Bullet bullet : bullets)
                        if(bullet.body == fa || bullet.body == fb) {
                            bullet.inGame = false;
                            break;
                        }
                    for(Enemy enemy : enemies)
                        if(enemy.body == fa || enemy.body == fb) {
                            enemy.damaged();
                            break;
                        }
                    SCORE++;
                }
                if((fa.getUserData().equals("world") && fb.getUserData().equals("bullet"))
                        || (fb.getUserData().equals("world") && fa.getUserData().equals("bullet"))){
                    for(Bullet bullet : bullets)
                        if(bullet.body == fa || bullet.body == fb) {
                            bullet.inGame = false;
                            break;
                        }
                }
                if((fa.getUserData().equals("world") && fb.getUserData().equals("enemy"))
                        || (fb.getUserData().equals("world") && fa.getUserData().equals("enemy"))){
                    for(Enemy enemy : enemies)
                        if (enemy.body == fa || enemy.body == fb) {
                            enemy.calcRot(player.body.getBody().getPosition());
                            break;
                        }
                }
                if((fa.getUserData().equals("player") && fb.getUserData().equals("enemy"))
                        || (fb.getUserData().equals("player") && fa.getUserData().equals("enemy"))){
                    for(Enemy enemy : enemies)
                        if(enemy.body == fa || enemy.body == fb) {
                            if(enemy.rot.x != 0 && enemy.rot.y != 0) {
                                enemy.rot.x *= -1;
                                enemy.rot.y *= -1;
                            }
                            enemy.change = TimeUtils.nanoTime()+3;
                            break;
                        }
                    player.damaged();
                }
            }
            @Override
            public void endContact(Contact contact) {}
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
        b2dr = new Box2DDebugRenderer();

        player = new Player(world);
        camera = new Camera(player);

        map = new TmxMapLoader().load("GameMap/map100x100.tmx");
        tmr = new OrthogonalTiledMapRenderer(map, 1f/PM);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("col").getObjects());
        batch = new SpriteBatch();

        Gdx.app.log("SIZE", Gdx.graphics.getWidth()+" "+Gdx.graphics.getHeight());

        stage = new Stage();
        userInterface = new UserInterface(stage.getCamera());

        for(int i = 0; i < MaxEnemy; i++) //TODO make more interesting II
            enemies.add(new Enemy(world, player.body.getBody().getPosition()));

        soundSystem = new SoundSystem();

        SCORE = 0;
    }

    @Override
    public void render(float delta) {
        //Update
        world.step(1 / 60f, 6, 2);
        userInterface.act(delta);
        player.update(delta);
        camera.update();
        tmr.setView(camera.camera);
        if((Constants.gunCS.x != 0 || Constants.gunCS.y != 0)
                && MathUtils.nanoToSec*(TimeUtils.nanoTime()-lastShootTime)*SHOOT_RATE >= 1){
            lastShootTime = TimeUtils.nanoTime();
            bullets.add(new Bullet(Constants.gunCS, world, player.body.getBody().getPosition()));
            soundSystem.playSound("shoot1", false);
        }
        for(Bullet bullet : bullets){
            bullet.update(delta);
            if(!bullet.inGame){
                bullets.removeValue(bullet, false);
            }
        }
        if(enemies.size < MaxEnemy && tol.isRespawn)
            enemies.add(new Enemy(world, player.body.getBody().getPosition()));
        for(Enemy enemy : enemies){
            enemy.update(delta, player.body.getBody().getPosition());
            if(!enemy.inGame){
                enemies.removeValue(enemy, true);
                SCORE++;
            }
        }
        //Render
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.camera.combined);
        b2dr.render(world, camera.camera.combined);
        tmr.render();
        batch.begin();
        for(Enemy enemy : enemies)
            enemy.render(batch);
        for(Bullet bullet : bullets)
            bullet.render(batch);
        player.render(batch);
        userInterface.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        Gdx.app.log("GameScreen", "dispose()");
        Gdx.gl.glClearColor(94f/256,63f/256,107f/256,256f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        world.dispose();
        stage.dispose();
        b2dr.dispose();
        player.dispose();
        tmr.dispose();
        map.dispose();
        batch.dispose();
        userInterface.dispose();
        enemies.clear();
        bullets.clear();
        LIVES = MAXLIVES;
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
}
