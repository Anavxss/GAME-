package io.github.eco;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class FlappyShapeGame extends Game {
    public int score;  // Variável para armazenar a pontuação do jogo
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.score = 0;  // Inicializando a pontuação
        setScreen(new GameScreen(this));  // Passando a instância do jogo para a tela inicial
    }

    @Override
    public void render() {
        super.render();  // Atualiza a tela atual (Tela do Jogo ou GameOver)
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    // Método para resetar a pontuação (útil para o GameOverScreen)
    public void resetScore() {
        this.score = 0;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
