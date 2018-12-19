package me.cockx.learn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;

public class GdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private TextureAtlas textureAtlas;
	private ExtendViewport extendViewport;
	private OrthographicCamera camera;
	private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	private World world;

	private static final float STEP_TIME = 1f / 60f;
	private static final int VELOCITY_ITERATIONS = 6;
	private static final int POSITION_ITERATIONS = 2;
	private static final float SCALE = 0.05f;

	private float accumulator = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		textureAtlas = new TextureAtlas("sprites.txt");
		camera = new OrthographicCamera();
		extendViewport = new ExtendViewport(50,50,camera);
		addSprites();
		Box2D.init();
		world = new World(new Vector2(0, -10), true);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		drawSprite("crate",0,0);
		drawSprite("crate",8,12);
		drawSprite("cherries",30,0);
		batch.end();
		stepWorld();
	}

	private void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();

		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= STEP_TIME) {
			accumulator -= STEP_TIME;

			world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		textureAtlas.dispose();
		sprites.clear();
		world.dispose();
	}

	@Override
	public void resize(int width, int height) {
		extendViewport.update(width,height,true);
		batch.setProjectionMatrix(camera.combined);
	}

	private void drawSprite(String name, float x, float y) {
		Sprite sprite = sprites.get(name);
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

	private void addSprites() {
		Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
		for (TextureAtlas.AtlasRegion region : regions) {
			Sprite sprite = textureAtlas.createSprite(region.name);
			float width = sprite.getWidth()*SCALE;
			float height = sprite.getHeight()*SCALE;

			sprite.setSize(width,height);
			sprite.setOrigin(0,0);

			sprites.put(region.name, sprite);

		}
	}
}
