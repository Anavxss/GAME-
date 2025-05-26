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

    public GameOverScreen(FlappyShapeGame game, int score) {
        this.game = game;
        this.batch = game.getBatch();
        this.finalScore = score;

        // Atualiza highscore se necessário
        if (score > game.getHighScore()) {
            game.setHighScore(score);
        }

        font = new BitmapFont(); // Usa a fonte padrão, sem erro
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "FIM DE JOGO", 300, 350);
        font.draw(batch, "Pontuação: " + finalScore, 300, 300);
        font.draw(batch, "Recorde: " + game.getHighScore(), 300, 250);
        font.draw(batch, "Toque para voltar ao menu", 250, 180);
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
