package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
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
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Game Over", 300, 400);
        font.draw(batch, "Score: " + game.getScore(), 280, 350);
        font.draw(batch, "High Score: " + game.getHighScore(), 280, 300);
        font.draw(batch, "Press SPACE to Restart", 250, 250);
        batch.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.resetScore();
            game.setScreen(new GameScreen(game));
        }
    }

    @Override
    public void show() {
        // Código a ser executado quando esta tela for mostrada
    }

    @Override
    public void resize(int width, int height) {
        // Responde a mudanças de tamanho de tela
    }

    @Override
    public void pause() {
        // Lógica ao pausar o jogo
    }

    @Override
    public void resume() {
        // Lógica ao retomar o jogo
    }

    @Override
    public void hide() {
        // Lógica quando a tela é escondida
    }

    @Override
    public void dispose() {
        // Libere recursos aqui
    }


}