package bullets;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

import gem.IGem;
import monster.IMonster;
import player.Player;

public class JadeBullet extends Bullet {
	
	private Player player;
	private float moneyChance;
	private int moneyAmount;
	private Random random;

	public JadeBullet(int posX, int posY, IMonster m, IGem gem, Player player, float moneyChance, int moneyAmount) {
		super(posX, posY, m, gem, new Texture("jadeBullet.png"));
		
		this.player = player;
		this.moneyChance = moneyChance;
		this.moneyAmount = moneyAmount;
		this.random = new Random();
	}
	
	@Override
	public void update() {
		if(posX > toHit.getCenter().getX() - 10 && posX < toHit.getCenter().getX() + 10 && posY > toHit.getCenter().getY() - 10 && posY < toHit.getCenter().getY() + 10) {
			hasArrived = true;
			if(!alreadyHit) {
				float chance = random.nextFloat();
				if(isCrit) {
					toHit.hit((int)(gem.getDamage()*gem.getCritDamage()), gem.getType());
				}
				else {
					toHit.hit(gem.getDamage(), gem.getType());
				}
				alreadyHit = true;
				toHit.poison(gem.getPoisonDamage(), gem.getPoisonDuration());
				if(chance <= moneyChance) {
					player.addMoney(moneyAmount);
				}
			}
			return;
		}
		if(posX < toHit.getCenter().getX()) {
			posX += speed;
		}
		else if(posX > toHit.getCenter().getX()) {
			posX -= speed;
		}
		
		if(posY < toHit.getCenter().getY()) {
			posY += speed;
		}
		else if(posY > toHit.getCenter().getY()) {
			posY -= speed;
		}
	}

}
