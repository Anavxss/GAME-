package io.github.eco;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
    private boolean paused = false;

    // botãos jogaveis
    private Rectangle upButton;
    private Rectangle downButton;
    private Texture upTexture;
    private Texture downTexture;

    // até aqui
    private TextureRegion playerRegion;

    private Rectangle pauseButton;
    private final FlappyShapeGame game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture background;

    private Texture backgroundpause;
    private float bgX;

    private Texture pngpause;
    private Texture playerTexture;
    private Rectangle player;
    private float playerSpeed = 300f;

    private Texture lixoTexture;    // coletável
    private Array<Rectangle> lixos;

    private Texture bulletTexture;  // obstáculo
    private Array<Rectangle> bullets;

    private float spawnLixoTimer;
    private float spawnBulletTimer;

    private float spawnLixoInterval = 1f;
    private float spawnBulletInterval = 0.8f;

    private int score;
    private int velocidadeLixo;
    private int velocidadeBullet;

    private OrthographicCamera camera;

    private boolean isButtonPressed(Rectangle bounds){
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new
                    Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            return
                    bounds.contains(touchPos.x, touchPos.y);
        }
        return false;
    }

    public GameScreen(FlappyShapeGame game, String dificuldade) {
        this.game = game;
        this.batch = game.batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280,720);

        switch (dificuldade) {
            case "Lento":
                velocidadeLixo = 200;
                velocidadeBullet = 250;
                break;
            case "Medio":
                velocidadeLixo = 350;
                velocidadeBullet = 400;
                break;
            case "Rapido":
                velocidadeLixo = 550;
                velocidadeBullet = 600;
                break;
            default:
                velocidadeLixo = 350;
                velocidadeBullet = 400;
        }

        backgroundpause = new Texture("pause.jpeg");

    }

    @Override
    public void show() {

        upTexture = new Texture("up.jpeg");
        downTexture = new Texture("down.jpeg");
        pngpause = new Texture("pause.png");
        background = new Texture("background.png");
        playerTexture = new Texture("player.png");
        playerRegion = new TextureRegion(playerTexture);
        playerRegion.flip(true, false);
        lixoTexture = new Texture("lixo.png");
        bulletTexture = new Texture("bullet.png");
        pauseButton = new Rectangle(1280 - 74, 656, 64, 64);

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

        float btnSize = 64;
        float padding = 20;

        upButton = new Rectangle(Gdx.graphics.getWidth() - btnSize - padding,
                Gdx.graphics.getHeight() - btnSize - padding,  btnSize, btnSize);

        downButton = new Rectangle(Gdx.graphics.getWidth() - btnSize - padding,
                Gdx.graphics.getHeight() - 2 * (btnSize + padding),  btnSize, btnSize);
    }

    @Override
    public void render(float delta) {


        // Atualizar fundo rolante
        bgX -= 200 * delta;
        if (bgX <= -background.getWidth()) bgX = 0;

        // Verificar clique no botão de pausa
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touch);
            if (pauseButton.contains(touch.x, touch.y)) {
                paused = true;
            }
        }

// Se pausado, exibe menu de pausa
        if (paused) {
            batch.begin();
            batch.draw(backgroundpause, 0, 0, 1280, 720);
            font.draw(batch, "Retomar", 250, 120);
            font.draw(batch, "Reiniciar", 550, 120);
            font.draw(batch, "Menu Principal", 850, 120);
            batch.end();

            if (Gdx.input.justTouched()) {
                Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                game.camera.unproject(touch);

                if (touch.y > 100 && touch.y < 140 && touch.x > 200 && touch.x < 300) paused = false; // Retomar
                else if (touch.y > 100 && touch.y < 140 && touch.x > 500 && touch.x < 600)
                    game.setScreen(new GameScreen(game, "Medio")); // Reinicia com mesma dificuldade (ajustar se necessário)
                else if (touch.y > 120 && touch.y < 140 && touch.x > 800 && touch.x < 900)
                    game.setScreen(new MenuScreen(game)); // Volta ao menu
            }
            return; // não processa jogo se pausado
        }


        // Controle do player W e S, limitar dentro da tela (0 a 720)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.y += playerSpeed * delta;
            if (player.y > 720 - player.height) player.y = 720 - player.height;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.y -= playerSpeed * delta;
            if (player.y < 0) player.y = 0;
        }

        // Controle do player botões, limitar dentro da tela (0 a 720)
        if (isButtonPressed(upButton)) {
            player.y += playerSpeed * delta;
            if (player.y > 720 - player.height) player.y = 720 - player.height;
        }
        if (isButtonPressed(downButton)) {
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
                game.setScreen(new GameOverScreen(game, score)); // voltar para tela de Game Over
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
        batch.draw(playerRegion, player.x, player.y, 95, 78);

        // Lixos (coletáveis)
        for (Rectangle lixo : lixos) {
            batch.draw(lixoTexture, lixo.x, lixo.y, lixo.width, lixo.height);
        }

        // Bullets (obstáculos)
        for (Rectangle bullet : bullets) {
            batch.draw(bulletTexture, bullet.x, bullet.y, 78, 78);
        }

        // Pontuação
        font.getData().setScale(2f);
        font.draw(batch, "Pontos: " + score, 50, 700);

        batch.end();

        batch.begin();
        batch.draw(pngpause, pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height);
        batch.end();


        // Setas
        batch.begin();
        batch.draw(upTexture, 900, 300, 300, 200);
        batch.draw(downTexture, 900, 50, 300, 200);
        batch.end();



        // setas fim

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
        upTexture.dispose();
        downTexture.dispose();

    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
