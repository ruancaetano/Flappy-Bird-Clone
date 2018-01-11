package com.ruancaetano.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by ruans on 11/01/2018.
 */

public class Controlador {

    private Passaro passaro;
    private SpriteBatch batch;
    private Canos canos;
    private int pontuacao;
    private BitmapFont fonte;
    private float larguraDispositivo;
    private float alturaDispositivo;
    private int ultimoCanoPontuado;
    private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;

    Controlador( SpriteBatch batch, Passaro passaro, Canos canos){
        //Recuperando dimensões totais do device
        larguraDispositivo = VIRTUAL_WIDTH;
        alturaDispositivo = VIRTUAL_HEIGHT;
        this.batch = batch;
        this.passaro = passaro;
        this.canos = canos;
        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);
        pontuacao = 0;
        ultimoCanoPontuado = -1;
    }

    public void renderPontuacao(){
        fonte.draw(batch,String.valueOf(pontuacao), larguraDispositivo /2 , alturaDispositivo - 50);
    }

    public void update(){
        float xPassaro = passaro.getX();
        float xCanos = canos.getX();

        if (xCanos < xPassaro && ultimoCanoPontuado != Canos.idCanoAtual){
            ultimoCanoPontuado = Canos.idCanoAtual;
            pontuacao++;
        }
    }

    public boolean testeColisao(){
        return Intersector.overlaps(passaro.getShape(), canos.getShapeCanoTopo()) ||
                Intersector.overlaps(passaro.getShape(), canos.getShapeCanoBaixo());
    }

    public void reset(){
        pontuacao = 0;
        ultimoCanoPontuado = -1;
    }
}
