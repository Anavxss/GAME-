package io.github.eco;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Intersector;

public class GameScreen implements Screen {
    private final FlappyShapeGame game;
    private Player player;
    private Array < Obstacle > obstacles;
    private Array < Collectible > collectibles;
    private ShapeRenderer sr;
    private float spawnTimer = 0;
    public GameScreen(FlappyShapeGame game) {
        this.game = game;
        sr = new ShapeRenderer();
        player = new Player(100, Gdx.graphics.getHeight() / 2, 20);
        obstacles = new Array < > ();
        collectibles = new Array < > ();
    }
    @Override
    public void render(float delta) {
        spawnTimer += delta;
        if(spawnTimer > 2f) {
            obstacles.add(new Obstacle(800, MathUtils.random(100, 500), 30, 10));
            collectibles.add(new Collectible(800));
            spawnTimer = 0;
        }
        player.update(delta);
        for(Obstacle o: obstacles) o.update(delta);
        for(Collectible c: collectibles) c.update(delta);
        for(Collectible c: collectibles) {
            if(!c.collected && c.isCollected(player)) {
                c.collected = true;
                game.score++;
            }
        }
        if(checkCollision()) {
            game.updateHighScore();
            game.setScreen(new GameOverScreen(game));
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(sr);
        for(Obstacle o: obstacles) o.draw(sr);
        for(Collectible c: collectibles)
            if(!c.collected) c.draw(sr);
        sr.end();
    }
    private boolean checkCollision() {
        for(Obstacle o: obstacles) {
            if (Intersector.overlaps(player.circle, o.rect)) return true;
        }
        return false;
    }
    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        sr.dispose();
    }
}