package io.github.eco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class FlappyShapeGame extends Game {

    public SpriteBatch batch;
    public OrthographicCamera camera;

    private int highScore = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720); // resolução em modo paisagem

        setScreen(new MenuScreen(this));
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int score) {
        if(score > highScore) highScore = score;
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
