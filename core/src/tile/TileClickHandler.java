package tile;

import java.util.Map;

import com.mygdx.game.RpgGame;

import gem.IGem;

public class TileClickHandler {
	
	private Map<Coordinate, ITile> tileMap;
	private Coordinate[][] coordinates;
	private ITile clickedTile;
	private IGem clickedGem;
	
	public TileClickHandler(Map<Coordinate, ITile> tileMap, Coordinate[][] coordinates) {
		this.tileMap = tileMap;
		this.coordinates = coordinates;
		this.clickedTile = null;
	}
	
	public void clickTile(int posX, int posY) {
		if(clickedTile != null) {
			clickedTile.resetTexture();
		}
		
		int x = posX/(RpgGame.WIDTH/50);
		int y = posY/(RpgGame.HEIGHT/30);
		
		Coordinate c = coordinates[x][y];
		clickedTile = tileMap.get(c);
	}
	
	public ITile getClickedTile() {
		return clickedTile;
	}
	
	public void unclickTile() {
		clickedTile = null;
	}
}
