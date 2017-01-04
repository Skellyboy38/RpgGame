package collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Intersector;

import gem.IGem;
import monster.Monster;

public class CollisionDetector {
	
	List<IGem> gems;
	List<Monster> monsters;
	
	public CollisionDetector(List<IGem> gems, List<Monster> monsters) {
		this.gems = gems;
		this.monsters = monsters;
	}
	
	public void detectCollisions() {
		for(IGem g : gems) {
			List<Monster> allMonstersInRange = new ArrayList<Monster>();
			boolean areAllMonstersSlowed = true;
			String type = g.getType();
			for(Monster m : monsters) {
				if(Intersector.overlaps(g.getCollisionBox(), m.getCollisionBox()) && !m.isDead()) {
					allMonstersInRange.add(m);
					if(!m.isSlowed()) {
						areAllMonstersSlowed = false;
					}
					if(type.equals("green") && g.canHit()) {
						g.hit(m);
					}
					if(type.equals("black") && g.canHit()) {
						g.hit(m);
					}
					if(type.equals("purple") && g.canHit()) {
						g.hit(m);
					}
					if(type.equals("white") && g.canHit()) {
						g.hit(m);
					}
					if(type.equals("pink") && g.canHit()) {
						g.hit(m);
					}
					if(type.equals("red") && g.canHit()) {
						g.hit(m, monsters);
					}
				}
			}
			if(type.equals("yellow") && g.canHit() && !allMonstersInRange.isEmpty()) {
				g.hit(allMonstersInRange);
			}
			if(type.equals("blue") && g.canHit()) {
				if(areAllMonstersSlowed && !allMonstersInRange.isEmpty()) {
					g.hit(allMonstersInRange.get(0));
				}
				else {
					for(Monster m : allMonstersInRange) {
						if(!m.isSlowed()) {
							g.hit(m);
							break;
						}
					}
				}
			}
			if(type.equals("black")) {
				for(IGem other : gems) {
					if(!g.equals(other) && Intersector.overlaps(g.getCollisionBox(), other.getBody())) {
						other.speedUp(g.getSpeedUpAmount());
					}
				}
			}
			if(type.equals("purple")) {
				for(IGem other : gems) {
					if(!g.equals(other) && Intersector.overlaps(g.getCollisionBox(), other.getBody())) {
						other.increaseCrit(g.getCritAmount());
					}
				}
			}
		}
	}
}
