package io.github.eco;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Obstacle {
    public float x, y, width, height;

    public Obstacle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(float delta) {
        x -= 200 * delta;
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(0, 1, 0, 1); // Verde
        sr.rect(x, y, width, height);
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }
}
