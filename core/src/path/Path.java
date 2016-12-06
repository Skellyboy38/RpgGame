package path;

import java.util.ArrayList;
import java.util.List;

import tile.ITile;

public class Path {
	
	List<ITile> path;
	
	public Path() {
		path = new ArrayList<ITile>();
	}
	
	public void add(ITile tile) {
		path.add(tile);
	}
	
	public List<ITile> getPath() {
		return path;
	}
}
