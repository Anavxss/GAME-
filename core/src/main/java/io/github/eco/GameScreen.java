package io.github.eco;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

    private Player player;
    private Array<Obstacle> obstacles;
    private Array<Collectible> collectibles;
    private float delta;
    private SpriteBatch batch;

    @Override
    public void show() {
        batch = new SpriteBatch();
        player = new Player(100, Gdx.graphics.getHeight() / 2, 20);  // Exemplo de raio 20
        obstacles = new Array<>();
        collectibles = new Array<>();
    }

    @Override
    public void render(float delta) {
        this.delta = delta;

        // Atualiza o jogador
        player.update(delta);

        // Atualiza os obstáculos e os itens colecionáveis
        for (Obstacle obstacle : obstacles) {
            obstacle.update(delta);
        }

        for (Collectible collectible : collectibles) {
            collectible.update(delta);
        }

        // Desenha todos os objetos na tela
        batch.begin();
        player.draw(batch);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(batch);
        }
        for (Collectible collectible : collectibles) {
            collectible.draw(batch);
        }
        batch.end();
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
        batch.dispose();
    }
}
