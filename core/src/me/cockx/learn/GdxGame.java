package me.cockx.learn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private TextureAtlas textureAtlas;
	private Sprite banana;
	private ExtendViewport extendViewport;
	private OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		textureAtlas = new TextureAtlas("sprites.txt");
		banana = textureAtlas.createSprite("banana");
		camera = new OrthographicCamera();
		extendViewport = new ExtendViewport(800,600,camera);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.draw(img,250,250);
		banana.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		textureAtlas.dispose();
	}

	@Override
	public void resize(int width, int height) {
		extendViewport.update(width,height);
		batch.setProjectionMatrix(camera.combined);
	}
}
