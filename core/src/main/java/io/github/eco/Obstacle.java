package io.github.eco;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle {
    public float x, y, width, height;

    public Obstacle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(float delta) {
        x -= 200 * delta;  // Move os obstáculos para a esquerda
    }

    public void draw(SpriteBatch batch) {
        batch.draw(obstacleTexture, x, y, width, height);  // Supondo que 'obstacleTexture' seja uma textura carregada
    }

    public boolean isOffScreen() {
        return x + width < 0;  // Verifica se o obstáculo está fora da tela
    }
}
