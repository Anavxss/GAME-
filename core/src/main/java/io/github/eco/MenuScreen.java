package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen implements Screen {

    private final FlappyShapeGame game;
    private BitmapFont font;
    private SpriteBatch batch;
    private Texture background;

    private String dificuldade = "Medio"; // padrão

    public MenuScreen(FlappyShapeGame game) {
        this.game = game;
        this.batch = game.batch;
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

        font.getData().setScale(3f);
        font.draw(batch, "Flappy Shape", 480, 600);
        font.getData().setScale(2f);
        font.draw(batch, "High Score: " + game.getHighScore(), 500, 550);

        font.draw(batch, "Dificuldade: " + dificuldade, 520, 400);
        font.draw(batch, "Clique para mudar dificuldade", 450, 350);
        font.draw(batch, "Clique para iniciar jogo", 470, 250);

        batch.end();

        if(Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);

            // Alterna dificuldade ao clicar na área do texto
            if(touchPos.y > 350 && touchPos.y < 400) {
                if(dificuldade.equals("Lento")) dificuldade = "Medio";
                else if(dificuldade.equals("Medio")) dificuldade = "Rapido";
                else dificuldade = "Lento";
            }

            // Inicia jogo se clicar na área do texto iniciar
            if(touchPos.y > 240 && touchPos.y < 280) {
                game.setScreen(new GameScreen(game, dificuldade));
            }
        }
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        font.dispose();
        background.dispose();
    }
}
