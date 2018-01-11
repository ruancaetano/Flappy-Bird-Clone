package com.ruancaetano.flappybird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import sun.security.provider.SHA;

/**
 * Created by ruans on 11/01/2018.
 */

public class Canos {
    private SpriteBatch batch;
    private Texture canoTopo;
    private Texture canoBaixo;
    private float larguraDispositivo;
    private float alturaDispositivo;
    private float espacoEntreCanos;
    private float posicaoHorizontalCanos;
    private float posicaoYCanoAlto;
    private float posicaoYCanoBaixo;
    private float variacao;
    private  float espacoEntreCanosRandomica;
    public static int idCanoAtual = 0;
    private Rectangle retanguloCanoBaixo;
    private Rectangle retanguloCanoAlto;
    private ShapeRenderer shapeRenderer;
    private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;

    Canos(SpriteBatch batch, ShapeRenderer shapeRenderer){
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        //Recuperando dimensões totais do device
        larguraDispositivo = VIRTUAL_WIDTH;
        alturaDispositivo = VIRTUAL_HEIGHT;
        //Valores inicias;
        espacoEntreCanos = 400;
        posicaoHorizontalCanos = larguraDispositivo - 100;
        canoTopo = new Texture("cano_topo.png");
        canoBaixo = new Texture("cano_baixo.png");
        retanguloCanoBaixo = new Rectangle();
        retanguloCanoAlto = new Rectangle();
        posicaoYCanoAlto = (alturaDispositivo / 2) + espacoEntreCanos/2 + espacoEntreCanosRandomica;
        posicaoYCanoBaixo = ((alturaDispositivo / 2) - canoBaixo.getHeight()) - espacoEntreCanos/2 + espacoEntreCanosRandomica;
        variacao = 0;
    }

    public void update(){
        variacao = Gdx.graphics.getDeltaTime() * 200;

        posicaoHorizontalCanos -= variacao;

        if (posicaoHorizontalCanos <= -canoTopo.getWidth()){
            idCanoAtual++;
            espacoEntreCanosRandomica = new Random().nextInt(400) - 200;
            posicaoHorizontalCanos = larguraDispositivo;
        }
        posicaoYCanoAlto = (alturaDispositivo / 2) + espacoEntreCanos/2 + espacoEntreCanosRandomica;
        posicaoYCanoBaixo = ((alturaDispositivo / 2) - canoBaixo.getHeight()) - espacoEntreCanos/2 + espacoEntreCanosRandomica;
    }

    public void render(){
        batch.draw(canoTopo,posicaoHorizontalCanos,posicaoYCanoAlto) ;
        batch.draw(canoBaixo, posicaoHorizontalCanos,posicaoYCanoBaixo) ;
    }

    public void renderShape(){
        retanguloCanoAlto.set(posicaoHorizontalCanos,posicaoYCanoAlto,canoTopo.getWidth(),canoTopo.getHeight());
        retanguloCanoBaixo.set(posicaoHorizontalCanos,posicaoYCanoBaixo,canoBaixo.getWidth(),canoBaixo.getHeight());
        //shapeRenderer.rect(retanguloCanoAlto.x,retanguloCanoAlto.y,retanguloCanoAlto.width,retanguloCanoAlto.height);
        //shapeRenderer.rect(retanguloCanoBaixo.x,retanguloCanoBaixo.y,retanguloCanoBaixo.width,retanguloCanoBaixo.height);
    }

    public int getX(){
        return (int) posicaoHorizontalCanos;
    }

    public Rectangle getShapeCanoTopo(){
        return retanguloCanoAlto;
    }
    public Rectangle getShapeCanoBaixo(){
        return retanguloCanoBaixo;
    }

    public void reset(){
        espacoEntreCanos = 400;
        posicaoHorizontalCanos = larguraDispositivo - 100;
        posicaoYCanoAlto = (alturaDispositivo / 2) + espacoEntreCanos/2 + espacoEntreCanosRandomica;
        posicaoYCanoBaixo = ((alturaDispositivo / 2) - canoBaixo.getHeight()) - espacoEntreCanos/2 + espacoEntreCanosRandomica;
        variacao = 0;
        idCanoAtual = 0;
    }

}
