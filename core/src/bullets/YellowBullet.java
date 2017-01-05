package bullets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public class YellowBullet extends Bullet {

	public YellowBullet(int posX, int posY, IMonster m, IGem gem, AssetManager manager) {
		super(posX, posY, m, gem, manager.get("yellowBullet.png", Texture.class));
	}
}
