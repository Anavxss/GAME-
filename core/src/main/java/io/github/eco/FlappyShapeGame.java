package io.github.eco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class FlappyShapeGame extends Game {

    public SpriteBatch batch;
    public OrthographicCamera camera;

    private int highScore = 0;

    private float screenWidth;
    private float screenHeight;

    @Override
    public void create() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight); // resolução em modo paisagem



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
