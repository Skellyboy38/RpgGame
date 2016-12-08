package collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Intersector;

import gem.IGem;
import monster.IMonster;

public class CollisionDetector {
	
	List<IGem> gems;
	List<IMonster> monsters;
	
	public CollisionDetector(List<IGem> gems, List<IMonster> monsters) {
		this.gems = gems;
		this.monsters = monsters;
	}
	
	public void detectCollisions() {
		for(IGem g : gems) {
			List<IMonster> allMonstersInRange = new ArrayList<IMonster>();
			boolean areAllMonstersSlowed = true;
			String type = g.getType();
			for(IMonster m : monsters) {
				if(Intersector.overlaps(g.getCollisionBox(), m.getCollisionBox()) && g.canHit()) {
					allMonstersInRange.add(m);
					if(!m.isSlowed()) {
						areAllMonstersSlowed = false;
					}
					if(type.equals("green")) {
						g.hit(m);
						m.hit(g.getDamage());
					}
				}
			}
			if(type.equals("yellow") && g.canHit() && !allMonstersInRange.isEmpty()) {
				g.hit(allMonstersInRange);
				for(IMonster m : allMonstersInRange) {
					m.hit(g.getDamage());
				}
			}
			if(type.equals("blue")) {
				if(areAllMonstersSlowed && !allMonstersInRange.isEmpty()) {
					g.hit(allMonstersInRange.get(0));
					allMonstersInRange.get(0).hit(g.getDamage());
				}
				else {
					for(IMonster m : allMonstersInRange) {
						if(!m.isSlowed()) {
							g.hit(m);
							m.hit(g.getDamage());
							break;
						}
					}
				}
			}
		}
	}
}
