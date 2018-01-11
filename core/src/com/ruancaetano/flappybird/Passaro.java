package com.ruancaetano.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

/**
 * Created by ruans on 11/01/2018.
 */

public class Passaro {

    private Texture[] passaro;
    private SpriteBatch batch;
    private float x;
    private float y;
    private float larguraDispositivo;
    private float alturaDispositivo;
    private float variacao;
    private int velociadadeQueda;
    private Circle circlePassaro;
    private ShapeRenderer shapeRenderer;
    private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;


    Passaro (SpriteBatch batch,ShapeRenderer shapeRenderer  ){
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;

        //Recuperando dimensões totais do device
        larguraDispositivo = VIRTUAL_WIDTH;
        alturaDispositivo = VIRTUAL_HEIGHT;
        //Definindo posicionamento inicial, velocidade de queda
        x = 120;
        y = alturaDispositivo / 2;
        velociadadeQueda = 0;
        //Definindo as texturas do pássaro
        passaro = new Texture[3];
        passaro[0] = new Texture("passaro1.png");
        passaro[1] = new Texture("passaro2.png");
        passaro[2] = new Texture("passaro3.png");
        circlePassaro = new Circle();
    }

    public void render (){
        batch.draw(passaro[(int) variacao], x,y , passaro[0].getWidth(),passaro[0].getHeight());
    }

    public void renderShape(){
        circlePassaro.set(x + (passaro[0].getWidth()/ 2) ,y + (passaro[0].getHeight() / 2),passaro[0].getWidth()/2);
        //shapeRenderer.circle(circlePassaro.x,circlePassaro.y,circlePassaro.radius);
    }

    public boolean update(){
        //Verifica se pássaro está no nivel do chão, ou seja, se rolou gameover
        if (y < 0 && velociadadeQueda > 0){
            //GameOver
            batch.draw(passaro[(int) variacao], x, 0 , passaro[0].getWidth(),passaro[0].getHeight());
            return false;
        }
        velociadadeQueda++;
        variacao += (Gdx.graphics.getDeltaTime() * 10);
        if(variacao > 2) variacao = 0;
        y -= velociadadeQueda;
        return  true;
    }

    public void subir(){
        velociadadeQueda -= 20;
    }

    public float getX(){
        return x;
    }

    public  float getY (){
        return y;
    }

    public Circle getShape(){
        return circlePassaro;
    }

    public void reset(){
        x = 120;
        y = alturaDispositivo / 2;
        velociadadeQueda = 0;
    }
}
