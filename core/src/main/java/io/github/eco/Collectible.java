package io.github.eco;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Intersector;

public class Collectible {
    public Rectangle rect;
    public boolean collected = false;

    public Collectible(float x) {
        // Gerando a posição aleatória do item coletável
        float y = MathUtils.random(100, 500);
        rect = new Rectangle(x, y, 20, 20);  // Tamanho do item (20x20 pixels)
    }

    public void update() {
        // Atualizando a posição do item coletável (movendo para a esquerda)
        rect.x -= 4;
    }

    public boolean isCollected(Player player) {
        // Verificando a colisão entre o círculo (player) e o retângulo (item coletável)
        return Intersector.overlaps(player.circle, rect);
    }

    public boolean isOffScreen() {
        // Verifica se o item saiu da tela (não está mais visível)
        return rect.x + rect.width < 0;
    }

    public void draw(ShapeRenderer sr) {
        // Desenhando o item coletável na tela
        sr.setColor(1, 0, 0, 1);  // Cor vermelha
        sr.rect(rect.x, rect.y, rect.width, rect.height);  // Desenhando o retângulo
    }
}
