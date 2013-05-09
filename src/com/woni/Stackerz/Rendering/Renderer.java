package com.woni.Stackerz.Rendering;

import java.io.IOException;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.woni.Stackerz.Objects.Block;
import com.woni.Stackerz.Objects.Line;
import com.woni.Stackerz.Simulation.Simulation;
import com.woni.utils.Font;
import com.woni.utils.GameActivity;
import com.woni.utils.Mesh;
import com.woni.utils.MeshLoader;
import com.woni.utils.Texture;
import com.woni.utils.Font.FontStyle;
import com.woni.utils.Font.Text;
import com.woni.utils.Mesh.PrimitiveType;
import com.woni.utils.Texture.TextureFilter;
import com.woni.utils.Texture.TextureWrap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.opengl.GLU;
import android.util.Log;

public class Renderer
{
        Mesh blockMesh;
        Texture blockTexture;
        Text scoreText;
        Font scoreFont;
        ArrayList<Color> colors = new ArrayList<Color>();
        
        class Color {
        	public Color(float r, float g, float b){
        		R = r;
        		G = g;
        		B = b;
        	}
        	
        	public float R;
        	public float G;
        	public float B;
        }
       
        public Renderer( GL10 gl, GameActivity activity )
        {
        	gl.glLoadIdentity();
        	gl.glTranslatef(0,0,0);
            gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
            
            scoreFont = new Font( gl, activity.getAssets(), "font.ttf", 16, FontStyle.Plain );
            scoreText = scoreFont.newText( gl );     
            
			try {
	        	Bitmap bitmap;
				bitmap = BitmapFactory.decodeStream( activity.getAssets().open( "block.png" ) );
	        	blockTexture = new Texture( gl, bitmap, TextureFilter.MipMap, TextureFilter.Linear, TextureWrap.ClampToEdge, TextureWrap.ClampToEdge );
	        	bitmap.recycle();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			colors.add(new Color(1f,1f,1f));
			colors.add(new Color(1f,0.1f,0.1f));
			colors.add(new Color(0.2f,1f,0.2f));
			colors.add(new Color(0.2f,0.2f,1f));
			colors.add(new Color(1f,1f,0.2f));
			colors.add(new Color(1f,0.2f,1f));
			colors.add(new Color(0.2f,1f,1f));
			colors.add(new Color(0.5f,0.8f,0.2f));
			colors.add(new Color(1f,0.5f,0.1f));
			colors.add(new Color(0.1f,0.1f,0.5f));
        }
       
        public void render( GL10 gl, GameActivity activity, Simulation simulation )
        {            		
        	gl.glClear( GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT );
            gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
    		
    		gl.glMatrixMode( GL10.GL_PROJECTION );
    		gl.glLoadIdentity();
    		GLU.gluOrtho2D( gl, 0, activity.getViewportWidth(), 0, activity.getViewportHeight() );
    		gl.glMatrixMode( GL10.GL_MODELVIEW );
    		gl.glLoadIdentity();
    		
            renderLines(gl, simulation.lines, activity, simulation.multiplier);
            
            gl.glLoadIdentity();
    		gl.glTranslatef( 0, activity.getViewportHeight(), -1 );
            scoreText.setText("score: "+ simulation.difficulty.difficultyname + "  " + simulation.score + "    level: " + simulation.multiplier);
            scoreText.render();
            
    		gl.glDisable( GL10.GL_TEXTURE_2D );
        }      
        
        private void renderLines( GL10 gl, ArrayList<Line> lines, GameActivity activity, int level)
        {        
        	Color color = colors.get(level % colors.size());
    		gl.glEnable( GL10.GL_BLEND );
    		gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
    		gl.glEnable( GL10.GL_TEXTURE_2D );
            blockTexture.bind();
            for( int i = 0; i < lines.size(); i++ )
            {
                    Line line = lines.get(i);
                    
                    float lineopacity = 0.01f * i;
                    
                    if(blockMesh == null)
                    	initBlockMesh(gl, line.blockSize, activity);
                	gl.glColor4f( color.R, color.G, color.B, 1 - lineopacity );	
                    for(int b = 0; b < line.blockCount; b++){
	                    gl.glPushMatrix();
	                    gl.glTranslatef( line.position.x + line.blockSize * b, line.position.y + line.blockSize * line.level, 1);
	                    blockMesh.render(PrimitiveType.TriangleFan);
	                    gl.glPopMatrix();
                    }
                	gl.glColor4f( color.R, color.G, color.B, 0.5f - lineopacity );	
                    if(line.removeBlocks != 0){
                    	int absRemovedBlocks = Math.abs(line.removeBlocks);
                		float startposition = line.removeBlocks < 0 ? line.position.x - line.blockSize * absRemovedBlocks : line.position.x + line.blockSize * (line.blockCount);
                    	for(int b = 0; b < absRemovedBlocks; b++){
    	                    gl.glPushMatrix();
    	                    gl.glTranslatef( startposition + line.blockSize * b, line.position.y + line.blockSize * line.level, 1);
    	                    blockMesh.render(PrimitiveType.TriangleFan);
    	                    gl.glPopMatrix();
                    	}
                    }
                	gl.glColor4f( 1f, 1f, 1f, 1 );	
            }
    		gl.glDisable( GL10.GL_BLEND );
        }
        
       
        private void initBlockMesh(GL10 gl, float size, GameActivity activity){
        	float correctedSize = size -1;
        	blockMesh = new Mesh( gl, 4, false, true, false );
        	blockMesh.texCoord(1,0);
        	blockMesh.vertex( correctedSize, correctedSize, 0 );
        	blockMesh.texCoord(0,0);
        	blockMesh.vertex( 0, correctedSize, 0 );
        	blockMesh.texCoord(0,1);
        	blockMesh.vertex( 0, 0, 0 );
        	blockMesh.texCoord(1,1);
        	blockMesh.vertex( correctedSize, 0, 0 );
        }
        
        public void dispose( )
        {
                blockMesh.dispose();
                blockTexture.dispose();
                scoreFont.dispose();
                scoreText.dispose();
        }
}

