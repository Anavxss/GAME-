package io.github.eco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class FlappyShapeGame extends Game {
    public int score;
    public int highScore = 0;
    private SpriteBatch batch;
    @Override
    public void create() {
        batch = new SpriteBatch();
        score = 0;
        this.setScreen(new MenuScreen(this));
    }
    public void resetScore() {
        score = 0;
    }
    public SpriteBatch getBatch() {
        return batch;
    }
    public int getScore() {
        return score;
    }
    public int getHighScore() {
        return highScore;
    }
    public void updateHighScore() {
        if(score > highScore) highScore = score;
    }
    @Override
    public void dispose() {
        batch.dispose();
    }
}