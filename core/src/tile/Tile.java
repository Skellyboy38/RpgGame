package tile;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.RpgGame;

public class Tile implements ITile {
	
	private boolean isOccupied;
	private boolean isClicked;
	private boolean isHoverable;
	private boolean isClickable;
	private boolean isCheckpoint;
	
	private TextButton button;
	private SpriteBatch batch;
	private TextureRegion texture;
	private Texture originalTexture;
	private TileClickHandler clickHandler;
	private List<ITile> neighbors;
	
	private int posX;
	private int posY;
	private int id;
	
	public Tile(int id, SpriteBatch batch, Stage stage, TileClickHandler clickHandler, String texture, int x, int y, boolean isCheckpoint) {
		
		this.isOccupied = false;
		this.isClicked = false;
		this.isCheckpoint = isCheckpoint;
		this.isHoverable = !isCheckpoint;
		this.isClickable = !isCheckpoint;
		
		this.posX = x;
		this.posY = y;
		this.id = id;
		
		this.originalTexture = new Texture(texture);
		this.texture = new TextureRegion(originalTexture);
		this.texture.setRegionWidth(RpgGame.WIDTH/50);
		this.texture.setRegionHeight(RpgGame.HEIGHT/30);
		this.batch = batch;
		this.clickHandler = clickHandler;
		this.neighbors = new ArrayList<ITile>();
		createButton();
		stage.addActor(button);
	}
	
	public int getId() {
		return id;
	}
	
	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}
	
	public void createButton() {
		button = new TextButton("", RpgGame.BUTTON_STYLE);
		button.setHeight(texture.getRegionHeight());
		button.setWidth(texture.getRegionWidth());
		button.setPosition(posX, posY);
		
		if(!isCheckpoint) {
			button.addListener(new InputListener() {
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					if(isClickable) {
						if(isClicked) {
							resetTexture();
							setClicked(false);
							unoccupy();
							clickHandler.drawPath();
						}
						else {
							clickHandler.clickTile(posX, posY);
							texture.setTexture(new Texture("tilePressed.png"));
							setClicked(true);
							occupy();
							clickHandler.drawPath();
						}
						return true;
					}
					return false;
				}
				
				@Override
				public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {
					if(!isClicked && isHoverable) {
						texture.setTexture(new Texture("tileHover.png"));
					}
				}
				
				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					if(!isClicked && isHoverable) {
						resetTexture();
					}
				}
			});
		}
	}
	
	public void softReset() {
		setClicked(false);
		enableHover();
		enableClick();
		resetTexture();
	}
	
	public void hardReset() {
		setClicked(false);
		enableHover();
		enableClick();
		unoccupy();
		resetTexture();
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

	public boolean isOccupied() {
		return isOccupied;
	}
	
	public void resetTexture() {
		texture.setTexture(originalTexture);
	}
	
	public void setTexture(Texture texture) {
		this.texture.setTexture(texture);
	}
	
	public void render() {
		batch.begin();
		batch.draw(texture, posX, posY);
		batch.end();
	}
	
	public boolean equals(ITile toCompare) {
		return this.id == toCompare.getId();
	}
	
	public boolean isCheckpoint() {
		return isCheckpoint;
	}
	
	public Coordinate getPosition() {
		return new Coordinate(posX, posY);
	}
	
	public void addNeighbor(ITile tile) {
		neighbors.add(tile);
	}

	public List<ITile> getNeighbors() {
		return neighbors;
	}

}
