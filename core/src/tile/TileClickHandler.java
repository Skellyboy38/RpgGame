package tile;

import java.util.Map;

import com.mygdx.game.RpgGame;

public class TileClickHandler {
	
	private Map<Coordinate, ITile> tileMap;
	private Coordinate[][] coordinates;
	private ITile clickedTile;
	
	public TileClickHandler(Map<Coordinate, ITile> tileMap, Coordinate[][] coordinates) {
		this.tileMap = tileMap;
		this.coordinates = coordinates;
		this.clickedTile = null;
	}
	
	public void click(int posX, int posY) {
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
	
	public void unclick() {
		clickedTile = null;
	}
}
