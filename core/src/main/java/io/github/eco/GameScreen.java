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

    private Rectangle upButton;
    private Rectangle downButton;
    private Texture upTexture;
    private Texture downTexture;

    private TextureRegion playerRegion;
    private Rectangle pauseButton;
    private final FlappyShapeGame game;
    private SpriteBatch batch;
    private BitmapFont font;

    private Texture background;
    private Texture backgroundpause;
    private float bgX;

    private float bgSpeed = 100f;

    private Texture pngpause;
    private Texture playerTexture;
    private Rectangle player;
    private float playerSpeed = 300f;

    private Texture lixoTexture;
    private Array<Rectangle> lixos;

    private Texture lixo20Texture;
    private Texture lixo40Texture;
    private Array<Rectangle> lixos20;
    private Array<Rectangle> lixos40;


    private Texture bulletTexture;
    private Array<Rectangle> bullets;

    private float spawnLixoTimer;
    private float spawnBulletTimer;

    private float spawnLixoInterval = 1f;
    private float spawnBulletInterval = 0.8f;

    private int score;
    private int velocidadeLixo;
    private int velocidadeBullet;

    private OrthographicCamera camera;

    private float screenWidth;
    private float screenHeight;

    private float buttonOpacity = 0.5f;

    private boolean isButtonTouched(Rectangle bounds) {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        return bounds.contains(touchPos.x, touchPos.y);
    }

    private void drawButtonWithOpacity(Texture texture, Rectangle buttonBounds) {
        batch.setColor(1, 1, 1, buttonOpacity);
        batch.draw(texture, buttonBounds.x, buttonBounds.y, buttonBounds.width, buttonBounds.height);
        batch.setColor(1, 1, 1, 1);
    }

    public GameScreen(FlappyShapeGame game, String dificuldade) {
        this.game = game;
        this.batch = game.batch;

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);

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

        lixo20Texture = new Texture("lixo20.png");
        lixo40Texture = new Texture("lixo40.png");

        lixos20 = new Array<>();
        lixos40 = new Array<>();

    }

    @Override
    public void show() {
        upTexture = new Texture("uppng.png");
        downTexture = new Texture("downpng.png");
        pngpause = new Texture("pause.png");
        background = new Texture("background.png");
        playerTexture = new Texture("player.png");
        playerRegion = new TextureRegion(playerTexture);
        playerRegion.flip(true, false);
        lixoTexture = new Texture("lixo.png");
        bulletTexture = new Texture("bullet.png");

        pauseButton = new Rectangle(screenWidth - 148, screenHeight - 148, 128, 128);

        player = new Rectangle();
        player.x = 100;
        player.y = screenHeight / 2f - 48;
        player.width = 150;
        player.height = 150;



        lixos = new Array<>();
        bullets = new Array<>();

        spawnLixoTimer = 0;
        spawnBulletTimer = 0;

        bgX = 0;

        font = new BitmapFont();
        score = 0;

        float btnSize = screenWidth * 0.12f;
        float padding = screenWidth * 0.03f;

        upButton = new Rectangle(screenWidth - btnSize - padding,
                padding + btnSize + padding, btnSize, btnSize);

        downButton = new Rectangle(screenWidth - btnSize - padding,
                padding, btnSize, btnSize);
    }

    @Override
    public void render(float delta) {
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Fundo
        bgX -= bgSpeed * delta;
        if (bgX <= -screenWidth) {
            bgX = 0;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, bgX, 0, screenWidth, screenHeight);
        batch.draw(background, bgX + screenWidth, 0, screenWidth, screenHeight);
        batch.end();

        // Botão de pausa
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            if (pauseButton.contains(touch.x, touch.y)) {
                paused = true;
            }
        }

        if (paused) {
            batch.begin();
            batch.draw(backgroundpause, 0, 0, screenWidth, screenHeight);
            batch.end();

            if (Gdx.input.justTouched()) {
                Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touch);

                float btnWidth = screenWidth * 0.6f;
                float btnHeight = screenHeight * 0.12f;
                float centerX = screenWidth / 3f - btnWidth / 2f;

                Rectangle btnRetornar = new Rectangle(centerX, screenHeight * 0.4f, btnWidth / 1.35f - 10, btnHeight);
                Rectangle btnReiniciar = new Rectangle(centerX + btnWidth / 1.5f + 100, screenHeight * 0.4f, btnWidth / 1.35f - 10, btnHeight);
                Rectangle btnMenu = new Rectangle(centerX, screenHeight * 0.1f, btnWidth, btnHeight);

                if (btnRetornar.contains(touch.x, touch.y)) paused = false;
                else if (btnReiniciar.contains(touch.x, touch.y)) game.setScreen(new GameScreen(game, "Medio"));
                else if (btnMenu.contains(touch.x, touch.y)) game.setScreen(new MenuScreen(game));
            }
            return;
        }


        // controle
        if (Gdx.input.isTouched()) {
            if (isButtonTouched(upButton)) {
                player.y += playerSpeed * delta;
                if (player.y > screenHeight - player.height) player.y = screenHeight - player.height;
            }
            if (isButtonTouched(downButton)) {
                player.y -= playerSpeed * delta;
                if (player.y < 0) player.y = 0;
            }
        }


        // spawn dos obstaculos
        spawnLixoTimer -= delta;
        if (spawnLixoTimer <= 0) {
            float tipo = MathUtils.random();

            Rectangle lixo = new Rectangle();
            lixo.x = screenWidth;
            lixo.y = MathUtils.random(0, screenHeight - 64);
            lixo.width = 64;
            lixo.height = 98;

            if (tipo < 0.7f) {
                lixos.add(lixo);
            } else if (tipo < 0.95f) {
                lixos20.add(lixo);
            } else {
                lixos40.add(lixo);
            }

            spawnLixoTimer = spawnLixoInterval;
        }


        spawnBulletTimer -= delta;
        if (spawnBulletTimer <= 0) {
            Rectangle bullet = new Rectangle();
            bullet.x = screenWidth;
            bullet.y = MathUtils.random(0, screenHeight - 96);
            bullet.width = 100;
            bullet.height = 100;
            bullets.add(bullet);
            spawnBulletTimer = spawnBulletInterval;
        }

        // colisão lixo

        for (int i = 0; i < lixos.size; i++) {
            Rectangle lixo = lixos.get(i);
            lixo.x -= velocidadeLixo * delta;

            if (lixo.x + lixo.width < 0) {
                lixos.removeIndex(i);
                i--;
                continue;
            }

            Rectangle playerHitbox = new Rectangle(
                    player.x + player.width * 0.1f,
                    player.y + player.height * 0.1f,
                    player.width * 0.8f,
                    player.height * 0.8f
            );

            Rectangle lixoHitbox = new Rectangle(
                    lixo.x + lixo.width * 0.1f,
                    lixo.y + lixo.height * 0.1f,
                    lixo.width * 0.8f,
                    lixo.height * 0.8f
            );

            if (lixoHitbox.overlaps(playerHitbox)) {
                score += 10;
                lixos.removeIndex(i);
                i--;
            }
        }

        for (int i = 0; i < lixos20.size; i++) {
            Rectangle lixo = lixos20.get(i);
            lixo.x -= velocidadeLixo * delta;

            if (lixo.x + lixo.width < 0) {
                lixos20.removeIndex(i);
                i--;
                continue;
            }

            Rectangle playerHitbox = new Rectangle(
                    player.x + player.width * 0.1f,
                    player.y + player.height * 0.1f,
                    player.width * 0.8f,
                    player.height * 0.8f
            );

            Rectangle lixoHitbox = new Rectangle(
                    lixo.x + lixo.width * 0.1f,
                    lixo.y + lixo.height * 0.1f,
                    lixo.width * 0.8f,
                    lixo.height * 0.8f
            );

            if (lixoHitbox.overlaps(playerHitbox)) {
                score += 20;
                lixos20.removeIndex(i);
                i--;
            }
        }


        for (int i = 0; i < lixos40.size; i++) {
            Rectangle lixo = lixos40.get(i);
            lixo.x -= velocidadeLixo * delta;

            if (lixo.x + lixo.width < 0) {
                lixos40.removeIndex(i);
                i--;
                continue;
            }

            Rectangle playerHitbox = new Rectangle(
                    player.x + player.width * 0.1f,
                    player.y + player.height * 0.1f,
                    player.width * 0.8f,
                    player.height * 0.8f
            );

            Rectangle lixoHitbox = new Rectangle(
                    lixo.x + lixo.width * 0.1f,
                    lixo.y + lixo.height * 0.1f,
                    lixo.width * 0.8f,
                    lixo.height * 0.8f
            );

            if (lixoHitbox.overlaps(playerHitbox)) {
                score += 40;
                lixos40.removeIndex(i);
                i--;
            }
        }



        // colisão tubarão
        for (int i = 0; i < bullets.size; i++) {
            Rectangle bullet = bullets.get(i);
            bullet.x -= velocidadeBullet * delta;

            if (bullet.x + bullet.width < 0) {
                bullets.removeIndex(i);
                i--;
                continue;
            }

            Rectangle playerHitbox = new Rectangle(
                    player.x + player.width * 0.1f,
                    player.y + player.height * 0.1f,
                    player.width * 0.8f,
                    player.height * 0.8f
            );

            Rectangle bulletHitbox = new Rectangle(
                    bullet.x + bullet.width * 0.1f,
                    bullet.y + bullet.height * 0.1f,
                    bullet.width * 0.8f,
                    bullet.height * 0.8f
            );

            if (bulletHitbox.overlaps(playerHitbox)) {
                if (score > game.getHighScore()) game.setHighScore(score);
                game.setScreen(new GameOverScreen(game, score));
                dispose();
                return;
            }
        }


        batch.begin();
        batch.draw(playerRegion, player.x, player.y, 100, 100);
        for (Rectangle lixo : lixos)
            batch.draw(lixoTexture, lixo.x, lixo.y, lixo.width, lixo.height);
        for (Rectangle lixo : lixos20)
            batch.draw(lixo20Texture, lixo.x, lixo.y, lixo.width, lixo.height);
        for (Rectangle lixo : lixos40)
            batch.draw(lixo40Texture, lixo.x, lixo.y, lixo.width, lixo.height);
        for (Rectangle bullet : bullets)
            batch.draw(bulletTexture, bullet.x, bullet.y, bullet.width, bullet.height);

        font.getData().setScale(4f);
        font.draw(batch, "Pontos: " + score, 20, screenHeight - 40);
        batch.draw(pngpause, pauseButton.x, pauseButton.y, pauseButton.width, pauseButton.height);
        drawButtonWithOpacity(upTexture, upButton);
        drawButtonWithOpacity(downTexture, downButton);
        batch.end();
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
        background.dispose();
        playerTexture.dispose();
        lixoTexture.dispose();
        bulletTexture.dispose();
        font.dispose();
        upTexture.dispose();
        downTexture.dispose();
        lixo20Texture.dispose();
        lixo40Texture.dispose();

    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
