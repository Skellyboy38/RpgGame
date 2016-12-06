package path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import tile.ITile;

public class PathFinder {
	
	public Path findPathBetweenTwoTiles(ITile start, ITile end) {
		
		Map<ITile, ITile> cameFrom = new HashMap<ITile, ITile>();
		List<Integer> visited = new ArrayList<Integer>();
		Path path = new Path();
		Queue<ITile> pathQueue = new LinkedList<ITile>();
		pathQueue.add(start);
		visited.add(start.getId());
		
		outerloop:
		while(!pathQueue.isEmpty()) {
			ITile current = pathQueue.remove();
			for(ITile tile : current.getNeighbors()) {
				if(!visited.contains(tile.getId()) && !tile.isOccupied()) {
					cameFrom.put(tile, current);
					if(tile.getId() == end.getId()) {
						break outerloop;
					}
					else {
						pathQueue.add(tile);
						visited.add(tile.getId());
					}
				}
			}
		}
		List<ITile> reversePath = new ArrayList<ITile>();
		reversePath.add(end);
		while(cameFrom.containsKey(end)) {
			reversePath.add(cameFrom.get(end));
			end = cameFrom.get(end);
		}
		Collections.reverse(reversePath);
		for(ITile tile : reversePath) {
			path.add(tile);
		}
		return path;
	}
	
	public boolean pathExists(ITile start, ITile end) {
		boolean exists = false;
		Map<ITile, ITile> cameFrom = new HashMap<ITile, ITile>();
		List<Integer> visited = new ArrayList<Integer>();
		Queue<ITile> pathQueue = new LinkedList<ITile>();
		pathQueue.add(start);
		visited.add(start.getId());
		
		outerloop:
		while(!pathQueue.isEmpty()) {
			ITile current = pathQueue.remove();
			for(ITile tile : current.getNeighbors()) {
				if(!visited.contains(tile.getId()) && !tile.isOccupied()) {
					cameFrom.put(tile, current);
					if(tile.getId() == end.getId()) {
						exists = true;
						break outerloop;
					}
					else {
						pathQueue.add(tile);
						visited.add(tile.getId());
					}
				}
			}
		}
		return exists;
	}
}
