package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class DificuldadeScreen implements Screen {
    private final FlappyShapeGame game;
    private SpriteBatch batch;
    private Texture backgrounddificuldade;

    private float screenWidth;
    private float screenHeight;

    public DificuldadeScreen(FlappyShapeGame game) {
        this.game = game;
        this.batch = game.getBatch();
        backgrounddificuldade = new Texture("dificuldade.jpeg");
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgrounddificuldade, 0, 0, screenWidth, screenHeight);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);

            float buttonHeight = screenHeight * 0.25f; // Altura aproximada da área dos botões
            float buttonY = screenHeight * 0.1f;      // Margem inferior

            float buttonWidth = screenWidth / 3f;

            if (isTouched(touchPos, 0, buttonY, buttonWidth, buttonHeight)) {
                game.setScreen(new GameScreen(game, "Lento"));
            } else if (isTouched(touchPos, buttonWidth, buttonY, buttonWidth, buttonHeight)) {
                game.setScreen(new GameScreen(game, "Medio"));
            } else if (isTouched(touchPos, 2 * buttonWidth, buttonY, buttonWidth, buttonHeight)) {
                game.setScreen(new GameScreen(game, "Rapido"));
            }
        }
    }

    private boolean isTouched(Vector3 touch, float x, float y, float width, float height) {
        return touch.x > x && touch.x < x + width && touch.y > y && touch.y < y + height;
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        backgrounddificuldade.dispose();
    }
}
