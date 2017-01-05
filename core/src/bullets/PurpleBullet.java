package bullets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public class PurpleBullet extends Bullet {

	public PurpleBullet(int posX, int posY, IMonster m, IGem gem, AssetManager manager) {
		super(posX, posY, m, gem, manager.get("purpleBullet.png", Texture.class));
	}
}
