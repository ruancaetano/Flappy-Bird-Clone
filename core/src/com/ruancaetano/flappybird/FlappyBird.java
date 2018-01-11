package com.ruancaetano.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.omg.PortableInterceptor.Interceptor;

import javax.xml.soap.Text;

public class FlappyBird extends ApplicationAdapter {

	private  SpriteBatch batch;
	private Passaro passaro;
	private Canos canos;
	private Controlador controlador;
	private Texture fundo;
	private Texture gameover;
	private int x = 0,y = 0;
	private float larguraDispositivo;
	private float alturaDispositivo;
	private int estado = 0; // 0 - jogo pausado / 1 - jogo iniciado / 2 gameover
	private int pontuacao=0;
	private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;

	@Override
	public void create () {
	    //Inicializando o batch
		batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
		//Inicializando o objeto pássaro e canos
		passaro = new Passaro(batch,shapeRenderer);
		canos = new Canos(batch,shapeRenderer);
		controlador = new Controlador(batch,passaro,canos);
		//Definindo texturas
		fundo = new Texture("fundo.png");
		gameover = new Texture("game_over.png");
        //Recuperando dimensões totais do device
		larguraDispositivo = VIRTUAL_WIDTH;
		alturaDispositivo = VIRTUAL_HEIGHT;

		camera = new OrthographicCamera();
		camera.position.set(VIRTUAL_WIDTH /2,VIRTUAL_HEIGHT / 2,0);
		viewport = new StretchViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera);
	}

	@Override
	public void render () {
	    camera.update();

	    //limpar frames anteriores
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.setProjectionMatrix( camera.combined);
		batch.begin();
		batch.draw(fundo,0,0,larguraDispositivo ,alturaDispositivo );
		passaro.render();
		canos.render();
		controlador.renderPontuacao();

		if (estado == 0){
			if (Gdx.input.justTouched()){
				estado = 1;
			}
		}else if(estado == 1){
			if (Gdx.input.justTouched()){
				passaro.subir();
			}

			boolean resultadoRenderPassaro = passaro.update();
			canos.update();
			controlador.update();
			//Verifica se o passáro chegou ao chão e dipara gameover
			if (!resultadoRenderPassaro){
				//GameOver
				estado = 2;
			}
		}else{
            passaro.update();
            if (Gdx.input.justTouched()){
                passaro.reset();
                controlador.reset();
                canos.reset();
                estado = 0;
            }
            batch.draw(gameover,(larguraDispositivo / 2) - 173, (alturaDispositivo / 2) - 42);
        }
        batch.end();

		//Desenha formas para colisão
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        passaro.renderShape();
        canos.renderShape();
        //shapeRenderer.setColor(Color.RED);
        shapeRenderer.end();

        //Teste Colisão
        if (controlador.testeColisao()){
           Gdx.app.log("colisão", "colisãaaaaaaao!");
           estado = 2;
        }

	}

    @Override
    public void resize(int width, int height) {
	    viewport.update(width,height);
    }
}
