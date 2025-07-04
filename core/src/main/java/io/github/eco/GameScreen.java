package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

    private final FlappyShapeGame game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture background;
    private float bgX;

    private Texture playerTexture;
    private Rectangle player;
    private float playerSpeed = 300f;

    private Texture lixoTexture;    // coletável
    private Array<Rectangle> lixos;

    private Texture bulletTexture;  // obstáculo
    private Array<Rectangle> bullets;

    private float spawnLixoTimer;
    private float spawnBulletTimer;

    private float spawnLixoInterval = 2.5f;
    private float spawnBulletInterval = 1.8f;

    private int score;
    private int velocidadeLixo;
    private int velocidadeBullet;

    public GameScreen(FlappyShapeGame game, String dificuldade) {
        this.game = game;
        this.batch = game.batch;

        switch (dificuldade) {
            case "Lento":
                velocidadeLixo = 150;
                velocidadeBullet = 180;
                break;
            case "Medio":
                velocidadeLixo = 250;
                velocidadeBullet = 300;
                break;
            case "Rapido":
                velocidadeLixo = 400;
                velocidadeBullet = 450;
                break;
            default:
                velocidadeLixo = 250;
                velocidadeBullet = 300;
        }
    }

    @Override
    public void show() {
        background = new Texture("background.png");
        playerTexture = new Texture("player.png");
        lixoTexture = new Texture("lixo.png");
        bulletTexture = new Texture("bullet.png");

        player = new Rectangle();
        player.x = 100;
        player.y = 720 / 2f - 32; // centralizado verticalmente (720/2 - metade do player)
        player.width = 64;
        player.height = 64;

        lixos = new Array<>();
        bullets = new Array<>();

        spawnLixoTimer = 0;
        spawnBulletTimer = 0;

        bgX = 0;

        font = new BitmapFont();
        score = 0;
    }

    @Override
    public void render(float delta) {
        // Atualizar fundo rolante
        bgX -= 200 * delta;
        if (bgX <= -background.getWidth()) bgX = 0;

        // Controle do player W e S, limitar dentro da tela (0 a 720)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.y += playerSpeed * delta;
            if (player.y > 720 - player.height) player.y = 720 - player.height;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.y -= playerSpeed * delta;
            if (player.y < 0) player.y = 0;
        }

        // Spawnar lixo (coletável)
        spawnLixoTimer -= delta;
        if (spawnLixoTimer <= 0) {
            Rectangle lixo = new Rectangle();
            lixo.x = 1280;
            lixo.y = MathUtils.random(0, 720 - 64);
            lixo.width = 64;
            lixo.height = 64;
            lixos.add(lixo);
            spawnLixoTimer = spawnLixoInterval;
        }

        // Spawnar bullets (obstáculos)
        spawnBulletTimer -= delta;
        if (spawnBulletTimer <= 0) {
            Rectangle bullet = new Rectangle();
            bullet.x = 1280;
            bullet.y = MathUtils.random(0, 720 - 64);
            bullet.width = 64;
            bullet.height = 64;
            bullets.add(bullet);
            spawnBulletTimer = spawnBulletInterval;
        }

        // Atualizar lixos e verificar coleta
        for (int i = 0; i < lixos.size; i++) {
            Rectangle lixo = lixos.get(i);
            lixo.x -= velocidadeLixo * delta;

            // Remover lixo que saiu da tela
            if (lixo.x + lixo.width < 0) {
                lixos.removeIndex(i);
                i--;
                continue;
            }

            // Coleta
            if (lixo.overlaps(player)) {
                score += 10;  // pontos ao coletar lixo
                lixos.removeIndex(i);
                i--;
            }
        }

        // Atualizar bullets e verificar colisão
        for (int i = 0; i < bullets.size; i++) {
            Rectangle bullet = bullets.get(i);
            bullet.x -= velocidadeBullet * delta;

            // Remover bullet que saiu da tela
            if (bullet.x + bullet.width < 0) {
                bullets.removeIndex(i);
                i--;
                continue;
            }

            // Colisão que termina jogo
            if (bullet.overlaps(player)) {
                if (score > game.getHighScore()) {
                    game.setHighScore(score);
                }
                game.setScreen(new MenuScreen(game)); // voltar para menu
                dispose();
                return;
            }
        }

        // Renderização
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Fundo rolando (duas vezes lado a lado para loop contínuo)
        batch.draw(background, bgX, 0, background.getWidth(), 720);
        batch.draw(background, bgX + background.getWidth(), 0, background.getWidth(), 720);

        // Player
        batch.draw(playerTexture, player.x, player.y, player.width, player.height);

        // Lixos (coletáveis)
        for (Rectangle lixo : lixos) {
            batch.draw(lixoTexture, lixo.x, lixo.y, lixo.width, lixo.height);
        }

        // Bullets (obstáculos)
        for (Rectangle bullet : bullets) {
            batch.draw(bulletTexture, bullet.x, bullet.y, bullet.width, bullet.height);
        }

        // Pontuação
        font.getData().setScale(2f);
        font.draw(batch, "Pontos: " + score, 50, 700);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Centralizar viewport se necessário, mas estamos usando resolução fixa
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        background.dispose();
        playerTexture.dispose();
        lixoTexture.dispose();
        bulletTexture.dispose();
        font.dispose();
    }
}
