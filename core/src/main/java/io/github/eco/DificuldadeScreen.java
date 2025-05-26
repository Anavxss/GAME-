package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class DificuldadeScreen implements Screen {
    private final FlappyShapeGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Texture background;

    public DificuldadeScreen(FlappyShapeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        font = new BitmapFont();
        background = new Texture("background.png");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, 1280, 720);
        font.getData().setScale(2.5f);
        font.draw(batch, "Escolha a Dificuldade", 420, 550);
        font.getData().setScale(2f);
        font.draw(batch, "1. Facil", 550, 450);
        font.draw(batch, "2. Medio", 550, 380);
        font.draw(batch, "3. Dificil", 550, 310);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);

            if (touchPos.y > 430 && touchPos.y < 470) game.setScreen(new GameScreen(game, "Lento"));
            else if (touchPos.y > 360 && touchPos.y < 400) game.setScreen(new GameScreen(game, "Medio"));
            else if (touchPos.y > 290 && touchPos.y < 330) game.setScreen(new GameScreen(game, "Rapido"));
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
        background.dispose();
    }
}