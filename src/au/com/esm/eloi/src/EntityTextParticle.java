package au.com.esm.eloi.src;

import java.util.Random;

public class EntityTextParticle extends EntityParticle{
	private String particleText;
	Random rdm = new Random();
	
	public EntityTextParticle(int id) {
		super(id);
		this.speed = 0.00025f;
	}

	public EntityTextParticle setText(String text){
		this.particleText = text;
		return this;
	}
	
	public void update(){
		xVelocity += Math.sin(Math.toRadians(rdm.nextInt(360)) + 3);
		yVelocity += Math.cos(Math.toRadians(rdm.nextInt(360)) + 3);
		zVelocity += Math.tan(Math.toRadians(rdm.nextInt(360)) + 3);
		yawRotation -= rdm.nextFloat();
		super.update();
	}
	
	public void render(){
		Eloi.getEloi().textRenderer.drawString3D(particleText, xPosition, yPosition, zPosition, pitchRotation, yawRotation, rollRotation, rdm.nextFloat(), rdm.nextFloat(), rdm.nextFloat(), rdm.nextFloat() + 0.5f);
	}
}
