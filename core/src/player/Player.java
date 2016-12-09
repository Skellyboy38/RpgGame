package player;

public class Player {
	private int hp;
	private int money;
	private int currentHp;
	private int gemChancesLevel;
	
	public Player(int hp) {
		this.hp = hp;
		this.currentHp = hp;
		this.money = 0;
		this.gemChancesLevel = 1;
	}
	
	public void reset() {
		this.currentHp = hp;
		this.money = 0;
	}
	
	public void addMoney(int toAdd) {
		money += toAdd;
	}
	
	public void spendMoney(int toSpend) {
		money -= toSpend;
	}
	
	public boolean canBuy(int amount) {
		return amount <= money;
	}
	
	public boolean isDead() {
		return currentHp <= 0;
	}
	
	public void damage(int amount) {
		currentHp -= amount;
	}
	
	public void addHealth(int amount) {
		currentHp += amount;
	}
	
	public int getHealth() {
		return currentHp;
	}
	
	public int getMoney() {
		return money;
	}
}
