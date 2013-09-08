package au.com.esm.eloi.src;

import org.lwjgl.opengl.GL11;

public class RendererWorld {
	/*
	private World theWorld;
	private int oglListId;
	private RendererTile tileRenderer;
	
	public RendererWorld(World world){
		theWorld = world;
		tileRenderer = new RendererTile();
		oglListId = OpenGLHelper.generateDisplayLists(1);
	}
	
	public void render(){
		GL11.glNewList(oglListId, GL11.GL_COMPILE);
		for (int y = 0; y < theWorld.getCollocateYsize(); y++){
			for (int z = 0; z < theWorld.getCollocateZsize(); z++){
				for (int x = 0; x < theWorld.getCollocateXsize(); x++){
					Tile focusedTile = theWorld.getTileAtPosition(x, y, z);
					if (focusedTile != null){
						if (focusedTile.getId() >= 0){
							tileRenderer.renderTileByType(focusedTile);
						}
					} else {
						//System.out.println("NULL TILE");
					}
				}
			}
		}
		GL11.glEndList();
		GL11.glCallList(oglListId);
	}
	*/
}
