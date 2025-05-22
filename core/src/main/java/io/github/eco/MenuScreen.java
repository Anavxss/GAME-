<<<<<<< HEAD
package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
public class MenuScreen implements Screen {
    private final FlappyShapeGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    public MenuScreen(FlappyShapeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Flappy Shapes", 300, 400);
        font.draw(batch, "Press ENTER to Start", 280, 300);
        font.draw(batch, "High Score: " + game.getHighScore(), 280, 250);
        batch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
        }
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() {
        font.dispose();
    }
=======
package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
public class MenuScreen implements Screen {
    private final FlappyShapeGame game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    public MenuScreen(FlappyShapeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Flappy Shapes", 300, 400);
        font.draw(batch, "Press ENTER to Start", 280, 300);
        font.draw(batch, "High Score: " + game.getHighScore(), 280, 250);
        batch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
        }
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void show() {}
    @Override public void dispose() {
        font.dispose();
    }
>>>>>>> 503f9ffd8403a83543285cce9f4d5c7d59c1b789
}