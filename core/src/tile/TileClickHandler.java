package tile;

import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.RpgGame;

import gem.IGem;
import gem.Rock;

public class TileClickHandler {
	
	private Map<Coordinate, ITile> tileMap;
	private Coordinate[][] coordinates;
	private ITile clickedTile;
	private IGem clickedGem;
	private Rock clickedRock;
	private SpriteBatch batch;
	private TextureRegion clickedGemTexture;
	
	public TileClickHandler(Map<Coordinate, ITile> tileMap, Coordinate[][] coordinates, SpriteBatch batch) {
		this.tileMap = tileMap;
		this.batch = batch;
		this.coordinates = coordinates;
		this.clickedTile = null;
		this.clickedGemTexture = new TextureRegion(new Texture("clickedGem.png"));
	}
	
	public void render() {
		if(clickedGem != null) {
			batch.begin();
			batch.draw(clickedGemTexture, clickedGem.getCoordinates().getX(), clickedGem.getCoordinates().getY());
			batch.end();
			clickedGem.drawCollisionBox();
		}
	}
	
	public void clickTile(int posX, int posY) {
		if(clickedTile != null) {
			clickedTile.resetTexture();
		}
		
		int x = posX/(RpgGame.WIDTH/50);
		int y = posY/(RpgGame.HEIGHT/30);
		
		Coordinate c = coordinates[x][y];
		clickedTile = tileMap.get(c);
		unclickGem();
		unclickRock();
	}
	
	public ITile getClickedTile() {
		return clickedTile;
	}
	
	public void unclickTile() {
		if(clickedTile != null) {
			clickedTile.resetTexture();
		}
		clickedTile = null;
	}
	
	public void registerClickedGem(IGem gem) {
		this.clickedGem = gem;
		unclickTile();
		unclickRock();
	}
	
	public void registerClickedRock(Rock rock) {
		this.clickedRock = rock;
		unclickTile();
		unclickGem();
	}
	
	public IGem getClickedGem() {
		return clickedGem;
	}
	
	public void unclickGem() {
		clickedGem = null;
	}
	
	public void clickRock(Rock r) {
		clickedRock = r;
	}
	
	public void unclickRock() {
		clickedRock = null;
	}
}
