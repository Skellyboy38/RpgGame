package gem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.game.RpgGame;

import tile.Coordinate;
import tile.TileClickHandler;

public class Rock {
	
	private SpriteBatch batch;
	private TextureRegion textureRegion;
	private Texture texture;
	private TileClickHandler clickHandler;
	private int posX;
	private int posY;
	private TextButton button;
	private Rock instance;
	private Coordinate coordinates;

	public Rock (SpriteBatch batch, Stage stage, TileClickHandler clickHandler, Texture texture, int posX, int posY) {
		this.texture = texture;
		this.textureRegion = new TextureRegion(texture);
		this.textureRegion.setRegionWidth(RpgGame.WIDTH/50);
		this.textureRegion.setRegionHeight(RpgGame.HEIGHT/30);
		this.batch = batch;
		this.clickHandler = clickHandler;
		this.posX = posX;
		this.posY = posY;
		createButton();
		stage.addActor(button);
		this.coordinates = new Coordinate(posX, posY);
		instance = this;
	}

	public void dispose() {
		texture.dispose();
	}
	
	public void removeListeners() {
		button.remove();
	}

	public void createButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		button = new TextButton("", style);
		button.setHeight(textureRegion.getRegionHeight());
		button.setWidth(textureRegion.getRegionWidth());
		button.setPosition(posX, posY);

		button.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				clickHandler.clickRock(instance);
				return false;
			}
		});
	}
	
	public TextureRegion getTexture() {
		return textureRegion;
	}
	
	public Coordinate getCoordinates() {
		return coordinates;
	}

	public void render() {
		batch.begin();
		batch.draw(texture, posX, posY);
		batch.end();
	}
}	
