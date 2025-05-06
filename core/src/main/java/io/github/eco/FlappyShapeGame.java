package io.github.eco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyShapeGame extends Game {
    public int score;
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.score = 0;
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
