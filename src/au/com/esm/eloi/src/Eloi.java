package au.com.esm.eloi.src;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;

import java.io.*;
import java.nio.FloatBuffer;

public class Eloi {
	public static Eloi gameInstance;
	
	private boolean runGame = false;
	
	public static int screenWidth = 900;
	public static int screenHeight = 500;
	public static boolean fullscreen = false;
	
	protected RenderEngine renderEngine;
	protected GlobalRenderer globalRenderer;
	private RendererText textRenderer;	
	
	protected Camera renderView;	
	protected InputController inputController;
	
	//Temporary camera stuff
	public static int fieldOfView = 70;
	public static float zNear = 0.02f;
	public static float zFar = 100.0f;
	private float fogNear = 20.0f;
	private float fogFar = 40.0f;
	private Color fogColor = new Color(0.7f, 0.7f, 0.8f, 1f);
		
	//Temporary timing control stuff.
	private long lastFrame;
	private int fps;
	private long lastFPS;
	public static int debugFPS;
	public static int delta;
	
	
	//Debug stuff 
	private boolean allowMouseOrientation = true;
	
	
	public static void main(String[] args){
		//Parse arguments and initilise game obj.
		Eloi game = new Eloi();
	}
	
	public Eloi(){
		//Initilise basic game stuff.
		gameInstance = this;
		this.start();
	}
	
	public void start(){
		//Initilise OGL etc.
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
			Display.setFullscreen(fullscreen);
			Display.create(new PixelFormat());
		} catch (LWJGLException ex){
			writeExceptionToFile(ex);			
		}
		
		try {
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GLU.gluPerspective(Eloi.fieldOfView, (float)screenWidth / (float)screenHeight, zNear, zFar);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBlendFunc(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glCullFace(GL11.GL_BACK);
			GL11.glEnable(GL11.GL_FOG);
			
			FloatBuffer fogColors = BufferUtils.createFloatBuffer(4);
			fogColors.put(new float[] {fogColor.r, fogColor.g, fogColor.b, fogColor.a});
			GL11.glClearColor(fogColor.r, fogColor.g, fogColor.b, fogColor.a);
			fogColors.flip();
			GL11.glFog(GL11.GL_FOG_COLOR, fogColors);
			GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
			GL11.glHint(GL11.GL_FOG_HINT, GL11.GL_NICEST);
			GL11.glFogf(GL11.GL_FOG_END, fogFar);
			GL11.glFogf(GL11.GL_FOG_DENSITY, 0.005f);
		} catch (Exception ex){
			writeExceptionToFile(ex);
		}
		
		renderEngine = new RenderEngine();
		renderEngine.loadTextures();
		
		globalRenderer = new GlobalRenderer(renderEngine);
		renderView = new Camera(0, 0, 0);
		
		textRenderer = new RendererText();
		
		inputController = new InputController();
		
		getDelta();
		lastFPS = getSystemTime();
		
		if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object){
			runGame = true;
			if (allowMouseOrientation) Mouse.setGrabbed(true);
			Mouse.setGrabbed(true);
			this.run();
		} else {
			Writer writer = null;
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("EloiOutput.txt"), "UTF-8"));
				writer.write("-=Eloi=- \nError: \nYour computer cannot run this game, sorry!\nReason: \nYour computer does not support the OpenGL GL_ARB_vertex_buffer_object extension.");
			} catch (IOException ioex){
				
			} finally {
				try {
					writer.close();
				} catch (Exception ex){
					System.out.println("Something is seriously wrong, Eloi can't even write an error file for you...");
				}
			}
		}
	}
	
	public void run(){
		try {
			while (runGame){
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				Eloi.delta = getDelta();
				this.runGameLoop();
				Display.update();
			}
		} catch (Exception ex){
			writeExceptionToFile(ex);
			System.exit(1);
		} finally {
			//Run any cleanup code.
			Display.destroy();
		}
	}
	
	public void runGameLoop(){
		//Update
		
		renderView.mbc.moveFoward = 0;
		renderView.mbc.moveStrafe = 0;
		renderView.mbc.moveUp = 0;
		renderView.mbc.pitch = 0;
		renderView.mbc.roll = 0;
		renderView.mbc.yaw = 0;
		if (inputController.moveFoward.isPressed){
			renderView.mbc.moveFoward = 1;
		}
		if (inputController.moveBack.isPressed){
			renderView.mbc.moveFoward = -1;
		}
		if (inputController.moveRight.isPressed){
			renderView.mbc.moveStrafe = 1;
		}
		if (inputController.moveLeft.isPressed){
			renderView.mbc.moveStrafe = -1;
		}
		if (inputController.moveUp.isPressed){
			renderView.mbc.moveUp = 1;
		}
		if (inputController.moveDown.isPressed){
			renderView.mbc.moveUp = -1;
		}
		if (inputController.pitchPlus.isPressed){
			renderView.mbc.pitch = 1;
		}
		if (inputController.pitchMinus.isPressed){
			renderView.mbc.pitch = -1;
		}
		if (inputController.yawPlus.isPressed){
			renderView.mbc.yaw = 1;
		}
		if (inputController.yawMinus.isPressed){
			renderView.mbc.yaw = -1;
		}
		if (inputController.rollPlus.isPressed){
			renderView.mbc.roll = 1;
		}
		if (inputController.rollMinus.isPressed){
			renderView.mbc.roll = -1;
		}
		
		if (allowMouseOrientation){
			float mouseX = Mouse.getDX() * 1.2F;
			float mouseY = -(Mouse.getDY() * 1.2F);		
			float pitch = renderView.getPitchRotation() + mouseY;
			float yaw = renderView.getYawRotation() + mouseX;
			//Pitch needs calculations?
			renderView.setCameraRotation(yaw, pitch, 0.0F);
		}
		
		while (Keyboard.next()){
			inputController.updateController(Keyboard.getEventKey(), Keyboard.getEventKeyState());
			if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE){
				this.runGame = false;
			}
		}
						
		renderView.update();	
				
		globalRenderer.prepareForRender(renderView);
				
		//Render
		testRender();		
		
		textRenderer.drawString3D("STRING", -5, -4, 3);
		
		textRenderer.drawString3D("STRING", -5, -4, 3, 0, 0, 0, 0F, 0F, 0F, 0.5F);
		textRenderer.drawString("FPS " + debugFPS, 0, 10, 0.5f);
		textRenderer.drawString("DELTA " + delta, 0, 35, 0.5f);
		textRenderer.drawString("X " + renderView.getX(), 0, 60, 0.5f);
		textRenderer.drawString("Y " + renderView.getY(), 0, 85, 0.5f);
		textRenderer.drawString("Z " + renderView.getZ(), 0, 110, 0.5f);		
		textRenderer.drawString("ORIGIN POS " + renderView.getDistanceSquaredTo(0, 0, 0), 0, 135, 0.5f);
		textRenderer.drawString("PITCH " + renderView.getPitchRotation(), 0, 160, 0.5f);
		textRenderer.drawString("YAW " + renderView.getYawRotation(), 0, 185, 0.5f);
		textRenderer.drawString("ROLL " + renderView.getRollRotation(), 0, 210, 0.5f);
		
		if (Display.isCloseRequested()){
			this.runGame = false;
		}
		
		Display.setTitle("Project Eloi - V0.01");
		
		updateFPS();
	}
	
	private void testRender(){
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(0f, 0f, -2f);		
		GL11.glColor3f(0, 1, 0);
		GL11.glVertex3f(1f, 0f, -2f);		
		GL11.glColor3f(0, 0, 1);
		GL11.glVertex3f(0.5f, 1f, -2.5f);
		
		
		GL11.glColor3f(0, 1, 0);
		GL11.glVertex3f(1f, 0f, -2f);		
		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(1f, 0f, -3f);		
		GL11.glColor3f(0, 0, 1);
		GL11.glVertex3f(0.5f, 1f, -2.5f);
		

		GL11.glColor3f(0, 0, 1);
		GL11.glVertex3f(0.5f, 1f, -2.5f);		
		GL11.glColor3f(0, 1, 0);
		GL11.glVertex3f(0f, 0f, -3f);	
		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(0f, 0f, -2f);
		

		GL11.glColor3f(0, 0, 1);
		GL11.glVertex3f(0.5f, 1f, -2.5f);		
		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(1f, 0f, -3f);		
		GL11.glColor3f(0, 1, 0);
		GL11.glVertex3f(0f, 0f, -3f);		
		GL11.glEnd();
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public static Eloi getEloi(){
		return gameInstance;
	}
	
	public int getDelta() 
    {
        long time = getSystemTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	    Eloi.delta = delta;

	    return delta;
    }
    
    public static long getSystemTime()
    {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
	
    public void updateFPS() 
    {
        if (getSystemTime() - lastFPS > 1000)
        {
            debugFPS = fps;
            fps = 0;
            lastFPS += 1000;
	    }
        fps++;
    }
    
    public static void writeExceptionToFile(Exception ex){
    	PrintStream stream;
		try {
			stream = new PrintStream(new FileOutputStream("EloiError" + String.valueOf(getSystemTime()) + ".txt"), false, "UTF-8");
			ex.printStackTrace(stream);
			System.out.println("Error file written!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("Something is seriously wrong, Eloi can't even write an error file for you...");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Something is seriously wrong, Eloi can't even write an error file for you...");
		}
    }
}
