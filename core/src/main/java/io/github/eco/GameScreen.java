package io.github.eco;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
        shapeRenderer = new ShapeRenderer();
        player = new Player(100, Gdx.graphics.getHeight() / 2, 20);
        obstacles = new Array<>();
        collectibles = new Array<>();

        for (int i = 0; i < 5; i++) {
            obstacles.add(new Obstacle(400 + i * 300, 0, 50, 200 + i * 20));
            collectibles.add(new Collectible(600 + i * 300));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.update(delta);
        for (Obstacle obstacle : obstacles) obstacle.update(delta);
        for (Collectible collectible : collectibles) collectible.update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        player.draw(shapeRenderer);
        for (Obstacle obstacle : obstacles) obstacle.draw(shapeRenderer);
        for (Collectible collectible : collectibles) collectible.draw(shapeRenderer);
        shapeRenderer.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
