package io.github.eco;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
public class Player {
    public Circle circle;
    public Player(float x, float y, float radius) {
        circle = new Circle(x, y, radius);
    }
    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            circle.y += 200 * delta;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            circle.y -= 200 * delta;
        }
    }
    public void draw(ShapeRenderer sr) {
        sr.setColor(0, 1, 0, 1);
        sr.circle(circle.x, circle.y, circle.radius);
    }
}