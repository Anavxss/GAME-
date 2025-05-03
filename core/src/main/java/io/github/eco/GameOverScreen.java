package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;

public class GameOverScreen implements Screen {

    private final FlappyShapeGame game;
    private final BitmapFont font;
    private final SpriteBatch batch;

    public GameOverScreen(FlappyShapeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f + 50);
        font.draw(batch, "Score: " + game.getScore(), Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f);
        font.draw(batch, "Press SPACE to Restart", Gdx.graphics.getWidth() / 2f - 150, Gdx.graphics.getHeight() / 2f - 50);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.resetScore();
            game.setScreen(new GameScreen(game));
        }
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
        font.dispose();
    }
}
