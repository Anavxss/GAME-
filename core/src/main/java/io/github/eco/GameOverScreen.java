package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen implements Screen {
    final FlappyShapeGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private int finalScore;

    private Texture backgroundfim;

    private float screenWidth;
    private float screenHeight;

    public GameOverScreen(FlappyShapeGame game, int score) {
        this.game = game;
        this.batch = game.getBatch();
        this.finalScore = score;

        // Atualiza highscore se necessário
        if (score > game.getHighScore()) {
            game.setHighScore(score);
        }

        font = new BitmapFont(); // Usa a fonte padrão, sem erro

        backgroundfim = new Texture("gameover.jpeg");

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(backgroundfim, 0, 0, screenWidth, screenHeight);
        font.getData().setScale(4f);
        font.draw(batch, "Pontuação: " + finalScore, screenWidth / 1.5f + 100, screenHeight / 2 + 100);
        font.getData().setScale(4f);
        font.draw(batch, "Recorde: " + game.getHighScore(), screenWidth / 1.5f + 100, screenHeight / 2 + 50);
        batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    @Override public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() {
        font.dispose();
    }
}
