package io.github.eco;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle {
    public float x, y, width, height;
    private Texture obstacleTexture;

    public Obstacle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.obstacleTexture = new Texture("obstacle.png"); // Certifique-se de ter esta imagem
    }

    public void update(float delta) {
        x -= 200 * delta;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(obstacleTexture, x, y, width, height);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public void dispose() {
        obstacleTexture.dispose();
    }
}
