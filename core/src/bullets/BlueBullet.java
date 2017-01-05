package bullets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;

public class BlueBullet extends Bullet {
	
	public BlueBullet(int posX, int posY, IMonster m, IGem gem, AssetManager manager) {
		super(posX, posY, m, gem, manager.get("blueBullet.png", Texture.class));
	}
}
