package tile;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public interface ITile {
	
	public List<ITile> getNeighbors();
	
	public void addNeighbor(ITile tile);
	
	public boolean isOccupied();
	
	public void render();
	
	public void resetTexture();
	
	public void setTexture(Texture texture);
	
	public int getId();
	
	public boolean isCheckpoint();
	
	public void disable();
	
	public void enableHover();
	
	public void enableClick();
	
	public void disableHover();
	
	public void disableClick();
	
	public void setDefaultTexture(Texture texture);
	
	public void occupy();
	
	public void unoccupy();
	
	public Coordinate getPosition();
	
	public void softReset();
	
	public void hardReset();

	public void dispose();
	
	public void restart();
}
