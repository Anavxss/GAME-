package io.github.eco;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Player {
    public Circle circle;
    private Texture texture;

    // Construtor que agora recebe x, y e raio como parâmetros
    public Player(float x, float y, float radius) {
        circle = new Circle(x, y, radius);
        texture = new Texture("circle.png");  // Use uma imagem de círculo, substitua por sua imagem desejada
    }

    // Atualiza o jogador, agora aceita o parâmetro 'delta'
    public void update(float delta) {
        // Lógica de movimentação do jogador (exemplo simples)
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            circle.y += 200 * delta; // Move o jogador para cima
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            circle.y -= 200 * delta; // Move o jogador para baixo
        }
    }

    public void draw(SpriteBatch batch) {
        // Desenho do jogador com a textura carregada
        batch.draw(texture, circle.x - circle.radius, circle.y - circle.radius, circle.radius * 2, circle.radius * 2);
    }

    public void dispose() {
        texture.dispose();  // Libera recursos quando o jogador não for mais necessário
    }
}
