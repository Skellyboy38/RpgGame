package gem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.game.RpgGame;
import java.util.List;

import bullets.IBullet;
import monster.IMonster;
import tile.Coordinate;
import tile.TileClickHandler;

public abstract class Gem implements IGem {
	
	public static final int GEM_WIDTH = RpgGame.WIDTH/50;
	public static final int GEM_HEIGHT = RpgGame.HEIGHT/30;

	private SpriteBatch batch;
	private Rectangle body;
	protected TextureRegion texture;
	private TileClickHandler clickHandler;
	protected int posX;
	protected int posY;
	private TextButton button;
	private boolean isTemporary;
	private IGem instance;
	private Coordinate coordinates;

	public Gem(SpriteBatch batch, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY) {
		this.texture = new TextureRegion(texture);
		this.body = new Rectangle();
		body.setWidth(20);
		body.setHeight(20);
		body.setX(posX);
		body.setY(posY);
		this.texture.setRegionWidth(GEM_WIDTH);
		this.texture.setRegionHeight(GEM_HEIGHT);
		this.batch = batch;
		this.clickHandler = clickHandler;
		this.posX = posX;
		this.posY = posY;
		createButton();
		stage.addActor(button);
		this.coordinates = new Coordinate(posX, posY);
		isTemporary = true;
		instance = this;
	}
	
	public Rectangle getBody() {
		return body;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}
	
	public void removeListeners() {
		button.remove();
	}

	public void createButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		button = new TextButton("", style);
		button.setHeight(texture.getRegionHeight());
		button.setWidth(texture.getRegionWidth());
		button.setPosition(posX, posY);

		button.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				clickHandler.registerClickedGem(instance);
				return false;
			}
		});
	}
	
	public boolean isTemporary() {
		return isTemporary;
	}

	public Coordinate getCoordinates() {
		return coordinates;
	}

	@Override
	public void render() {
		update();
		batch.begin();
		batch.draw(texture, posX, posY);
		for(IBullet b : getBullets()) {
			if(!b.hasArrived()) {
				batch.draw(b.getTexture(), b.getX(), b.getY());
			}
		}
		batch.end();
	}
	
	public void setPermanent() {
		isTemporary = false;
	}
	
	@Override
	public void hit(List<IMonster> monsters) {
		//do nothing
	}
	
	abstract public void drawCollisionBox();
}
