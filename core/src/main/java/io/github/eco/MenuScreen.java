package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen implements Screen {

    private final FlappyShapeGame game;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;

    private Texture backgroundTexture;
    private Rectangle playBounds;

    private float screenWidth;
    private float screenHeight;

    public MenuScreen(FlappyShapeGame game) {
        this.game = game;

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

        batch = game.getBatch();

        backgroundTexture = new Texture("start.jpeg");

        float btnWidth = screenWidth * 0.5f;
        float btnHeight = screenHeight * 0.12f;
        float btnX = (screenWidth - btnWidth) / 2f;
        float btnY = screenHeight * 0.27f;

        playBounds = new Rectangle(btnX, btnY, btnWidth, btnHeight);
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, screenWidth, screenHeight);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            if (playBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new DificuldadeScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        camera.setToOrtho(false, screenWidth, screenHeight);

        float btnWidth = screenWidth * 0.75f;
        float btnHeight = screenHeight * 0.12f;
        float btnX = (screenWidth - btnWidth) / 3f;
        float btnY = screenHeight * 0.1f;

        playBounds.set(btnX, btnY, btnWidth, btnHeight);
    }

    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }
}
