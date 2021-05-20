package Entity;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private int id;
	private int width;
	private int height;
	
	public Texture(String filename) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File(filename));
			width = bi.getWidth();
			height = bi.getHeight();
			//System.out.println(width+" by "+ height);
			int[] pixels_raw = new int[width*height*4];
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			ByteBuffer pixels = BufferUtils.createByteBuffer(width*height*4);
			
			int diff = 0;
			for(int i = 0; i < width;i++) {
				for (int j = 0; j < height; j ++) {
					int pixel = pixels_raw[i*width + j - diff * i ];
					pixels.put((byte) ((pixel >> 16) & 0xFF));	//R
					pixels.put((byte) ((pixel >> 8) & 0xFF)); 	//G
					pixels.put((byte) (pixel & 0xFF));			//B
					pixels.put((byte) ((pixel >> 24) & 0xFF));	//A
				}
				 diff = width - height;
			}
			
			pixels.flip();
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexImage2D(GL_TEXTURE_2D, 0,GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	

}
