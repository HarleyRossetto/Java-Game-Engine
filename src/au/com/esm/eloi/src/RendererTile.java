package au.com.esm.eloi.src;

import java.util.Random;

public class RendererTile {
	private final Constructor constructor = Constructor.instance;

	public void renderTileByType(Tile tile){
		if (tile != null) {
			switch(tile.getRenderType()){
			case 0:
				try {
					renderTile(tile);
				} catch (Exception ex){
					System.out.println(ex.getMessage());
				}			
				break;
			}
		}
	}
	
	private void renderTile(Tile tile){
		TextureUV uv = RenderEngine.getTextureMap(0).getUVCoordinates(tile.getId());
		float tileX = tile.getX();
		float tileY = tile.getY();
		float tileZ = tile.getZ();
		float size = 0.25f;
		float yRenderOffset = RenderHelper.getRenderOffset(1); //0.01f
		Random rdm = new Random();
		yRenderOffset += rdm.nextFloat() / 10;
		
		constructor.addVertexAndUV(tileX * size, tileY * yRenderOffset, tileZ * size, uv.uMin, uv.vMin);
		constructor.addVertexAndUV((tileX * size) + size, tileY * yRenderOffset, tileZ * size, uv.uMax, uv.vMin);
		constructor.addVertexAndUV((tileX * size) + size, tileY * yRenderOffset, (tileZ * size) + size, uv.uMax, uv.vMax);
		constructor.addVertexAndUV(tileX * size, tileY * yRenderOffset, (tileZ * size) + size, uv.uMin, uv.vMax);
	}
}
