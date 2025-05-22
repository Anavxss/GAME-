package io.github.eco;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
public class Obstacle {
    public Rectangle rect;
    public Obstacle(float x, float y, float width, float height) {
        rect = new Rectangle(x, y, width, height);
    }
    public void update(float delta) {
        rect.x -= 200 * delta;
    }
    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 0, 1);
        sr.rect(rect.x, rect.y, rect.width, rect.height);
    }
    public boolean isOffScreen() {
        return rect.x + rect.width < 0;
    }
}