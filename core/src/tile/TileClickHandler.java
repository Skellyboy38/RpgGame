package tile;

import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.RpgGame;

import gem.IGem;
import gem.Rock;
import monster.Summoner;

public class TileClickHandler {
	
	private Map<Coordinate, ITile> tileMap;
	private Coordinate[][] coordinates;
	private SpriteBatch batch;
	private TextureRegion clickedTexture;
	private Summoner summoner;
	
	private ITile clickedTile;
	private IGem clickedGem;
	private Rock clickedRock;
	
	public TileClickHandler(Map<Coordinate, ITile> tileMap, Coordinate[][] coordinates, SpriteBatch batch, Summoner summoner) {
		this.tileMap = tileMap;
		this.batch = batch;
		this.summoner = summoner;
		this.coordinates = coordinates;
		this.clickedTexture = new TextureRegion(new Texture("clickedGem.png"));
		
		this.clickedTile = null;
		this.clickedGem = null;
		this.clickedRock = null;
	}
	
	public void render() {
		batch.begin();
		if(clickedRock != null) {
			batch.draw(clickedTexture, clickedRock.getCoordinates().getX(), clickedRock.getCoordinates().getY());
		}
		else if(clickedGem != null) {
			clickedGem.drawCollisionBox();
		}
		batch.end();
	}
	
	public void drawPath() {
		summoner.drawPath();
	}
	
	public void clickTile(int posX, int posY) {
		if(clickedTile != null) {
			clickedTile.hardReset();
		}
		
		int x = posX/RpgGame.TILE_WIDTH;
		int y = posY/RpgGame.TILE_WIDTH;
		
		Coordinate c = coordinates[x][y];
		clickedTile = tileMap.get(c);
		unclickGem();
		unclickRock();
	}
	
	public ITile getClickedTile() {
		return clickedTile;
	}
	
	public void buryTile() {
		if(clickedTile != null) {
			clickedTile.softReset();
		}
		clickedTile = null;
	}
	
	public void unclickTile() {
		if(clickedTile != null) {
			clickedTile.hardReset();
		}
		clickedTile = null;
	}
	
	public void clickGem(IGem gem) {
		this.clickedGem = gem;
		unclickTile();
		unclickRock();
	}
	
	public void clickRock(Rock rock) {
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
	
	public Rock getClickedRock() {
		return clickedRock;
	}
	
	public void unclickRock() {
		clickedRock = null;
	}
	
	public void reset() {
		unclickGem();
		unclickRock();
		unclickTile();
	}
}
