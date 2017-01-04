package bullets;

import com.badlogic.gdx.graphics.Texture;

public interface IBullet {

	public Texture getTexture();
	
	public int getX();
	
	public int getY();
	
	public boolean hasArrived();
	
	public void update();
	
	public boolean isCrit();

	public void dispose();
}
