package io.github.eco;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player {
    public Circle circle;
    private Texture texture;

    public Player(float x, float y, float radius) {
        circle = new Circle(x, y, radius);
        texture = new Texture("circle.png");  // Certifique-se de ter essa imagem
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            circle.y += 200 * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            circle.y -= 200 * delta;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, circle.x - circle.radius, circle.y - circle.radius, circle.radius * 2, circle.radius * 2);
    }

    public void dispose() {
        texture.dispose();
    }
}
