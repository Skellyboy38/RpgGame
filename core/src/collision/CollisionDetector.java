package collision;

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
			for(IMonster m : monsters) {
				if(Intersector.overlaps(g.getCollisionBox(), m.getCollisionBox()) && g.canHit() && !m.isDead()) {
					g.hit(m);
					m.hit(g.getDamage());
				}
			}
		}
	}
}
