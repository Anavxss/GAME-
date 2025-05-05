package io.github.eco;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;

public class Collectible {
    public Rectangle rect;
    public boolean collected = false;

    public Collectible(float x) {
        float y = MathUtils.random(100, 500);
        rect = new Rectangle(x, y, 20, 20);
    }

    public void update(float delta) {
        rect.x -= 200 * delta;
    }

    public boolean isCollected(Player player) {
        return Intersector.overlaps(player.circle, rect);
    }

    public boolean isOffScreen() {
        return rect.x + rect.width < 0;
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 0, 0, 1);
        sr.rect(rect.x, rect.y, rect.width, rect.height);
    }
}
