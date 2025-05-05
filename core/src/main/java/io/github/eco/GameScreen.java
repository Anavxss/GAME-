package io.github.eco;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

    private final FlappyShapeGame game;
    private Player player;
    private Array<Obstacle> obstacles;
    private Array<Collectible> collectibles;
    private ShapeRenderer shapeRenderer;

    public GameScreen(FlappyShapeGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        player = new Player(100, Gdx.graphics.getHeight() / 2f, 20);
        obstacles = new Array<>();
        collectibles = new Array<>();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        player.update(delta);

        for (Obstacle obstacle : obstacles) {
            obstacle.update(delta);
        }

        for (Collectible collectible : collectibles) {
            collectible.update(delta);
        }

        SpriteBatch batch = game.getBatch();
        batch.begin();
        player.draw(batch);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(batch);
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Collectible collectible : collectibles) {
            collectible.draw(shapeRenderer);
        }
        shapeRenderer.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        player.dispose();
        shapeRenderer.dispose();
    }
}
