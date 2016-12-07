package tile;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.mygdx.game.RpgGame;

public abstract class AbstractTile implements ITile {
	
	private boolean isOccupied;
	private boolean isPressed;
	private boolean isHoverable;
	private boolean isClickable;
	private List<Coordinate> coordinates;
	private TextButton button;
	private SpriteBatch batch;
	private TextureRegion texture;
	private Texture originalTexture;
	private TileClickHandler clickHandler;
	private int posX;
	private int posY;
	private int id;
	private boolean isCheckpoint;
	
	public AbstractTile(int id, SpriteBatch batch, Stage stage, TileClickHandler clickHandler, Texture texture, int x, int y, boolean isCheckpoint) {
		this.originalTexture = texture;
		this.texture = new TextureRegion(originalTexture);
		isOccupied = false;
		isPressed = false;
		coordinates = new ArrayList<Coordinate>();
		this.texture.setRegionWidth(RpgGame.WIDTH/50);
		this.texture.setRegionHeight(RpgGame.HEIGHT/30);
		this.batch = batch;
		this.clickHandler = clickHandler;
		this.posX = x;
		this.posY = y;
		this.id = id;
		this.isCheckpoint = isCheckpoint;
		this.isHoverable = !isCheckpoint;
		this.isClickable = !isCheckpoint;
		createButton();
		addCoordinates();
		stage.addActor(button);
	}
	
	public int getId() {
		return id;
	}
	
	public void createButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		button = new TextButton("", style);
		button.setHeight(texture.getRegionHeight());
		button.setWidth(texture.getRegionWidth());
		button.setPosition(posX, posY);
		
		if(!isCheckpoint) {
			button.addListener(new InputListener() {
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					if(isClickable) {
						if(isPressed) {
							resetTexture();
							isPressed = false;
						}
						else {
							clickHandler.clickTile(posX, posY);
							texture.setTexture(new Texture("tilePressed.png"));
							isPressed = true;
						}
						return true;
					}
					return false;
				}
				
				@Override
				public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
					if(!isPressed && isHoverable) {
						texture.setTexture(new Texture("tileHover.png"));
					}
				}
				
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					if(!isPressed && isHoverable) {
						resetTexture();
					}
				}
			});
		}
	}
	
	public void disable() {
		disableHover();
		disableClick();
	}
	
	public void enableHover() {
		isHoverable = true;
	}
	
	public void enableClick() {
		isClickable = true;
	}
	
	public void disableHover() {
		isHoverable = false;
	}
	
	public void disableClick() {
		isClickable = false;
	}
	
	public void occupy() {
		isOccupied = true;
	}
	
	public void unoccupy() {
		isOccupied = false;
	}
	
	public void setDefaultTexture(Texture texture) {
		this.originalTexture = texture;
	}
	
	public void addCoordinates() {
		coordinates.add(new Coordinate(posX, posY));
		coordinates.add(new Coordinate(posX, posY + texture.getRegionHeight()));
		coordinates.add(new Coordinate(posX + texture.getRegionWidth(), posY + texture.getRegionHeight()));
		coordinates.add(new Coordinate(posX + texture.getRegionWidth(), posY));
	}

	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void resetTexture() {
		texture.setTexture(originalTexture);
	}
	
	public List<Coordinate> getCoordinates() {
		return coordinates;
	}
	
	public void setTexture(Texture texture) {
		this.texture.setTexture(texture);
	}
	
	public void render() {
		batch.begin();
		batch.draw(texture, posX, posY);
		batch.end();
	}
	
	public abstract void update();
	
	public boolean equals(ITile toCompare) {
		return this.id == toCompare.getId();
	}
	
	public boolean isCheckpoint() {
		return isCheckpoint();
	}
	
	public Coordinate getPosition() {
		return new Coordinate(posX, posY);
	}

}
