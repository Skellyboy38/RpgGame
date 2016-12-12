package bullets;

import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public class BlackBullet extends Bullet {
	
	public BlackBullet(int posX, int posY, IMonster m, IGem gem) {
		super(posX, posY, m, gem, new Texture("blackBullet.png"));
	}
}
