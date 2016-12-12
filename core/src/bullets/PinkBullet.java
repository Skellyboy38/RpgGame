package bullets;

import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public class PinkBullet extends Bullet {

	public PinkBullet(int posX, int posY, IMonster m, IGem gem) {
		super(posX, posY, m, gem, new Texture("pinkBullet.png"));
	}
}
