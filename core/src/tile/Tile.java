package tile;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Tile extends AbstractTile {
	
	private List<ITile> neighbors;
	
	public Tile(int id, SpriteBatch batch, Stage stage, TileClickHandler clickHandler, String texture, int x, int y, boolean isCheckpoint) {
		super(id, batch, stage, clickHandler, new Texture(texture), x, y, isCheckpoint);
		this.neighbors = new ArrayList<ITile>();
	}
	
	public void addNeighbor(ITile tile) {
		neighbors.add(tile);
	}

	public List<ITile> getNeighbors() {
		return neighbors;
	}

	public void update() {
		// TODO Auto-generated method stub
	}
}
