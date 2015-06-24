package cluedoNetworkLayer;


import enums.Weapons;

public class CluedoWeapon {
	Weapons weapon;
	CluedoPosition position;
	
	public CluedoWeapon(Weapons w,CluedoPosition startpos) {
		weapon = w;
		position = startpos;
	}
	
	public CluedoPosition getPosition() {
		return position;
	}
	
	public Weapons getWeapon() {
		return weapon;
	}
	
	public String getWeaponName(){
		return weapon.getName();
	}
	
	
	public void setPosition(int x, int y) {
		this.position.setX(x);
		this.position.setY(y);
	}
}
