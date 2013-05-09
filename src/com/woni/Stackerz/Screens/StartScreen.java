package com.woni.Stackerz.Screens;

import javax.microedition.khronos.opengles.GL10;

import woni.Stackerz.Difficulty.DifficultyCrazy;
import woni.Stackerz.Difficulty.DifficultyFast;
import woni.Stackerz.Difficulty.DifficultyInsane;
import woni.Stackerz.Difficulty.DifficultyMedium;
import woni.Stackerz.Difficulty.DifficultySlow;
import woni.Stackerz.Difficulty.IDifficulty;

import com.woni.Stackerz.Sounds.SoundManager;
import com.woni.utils.Font;
import com.woni.utils.GameActivity;
import com.woni.utils.Mesh;
import com.woni.utils.Texture;
import com.woni.utils.Font.FontStyle;
import com.woni.utils.Font.Text;
import com.woni.utils.Mesh.PrimitiveType;
import com.woni.utils.Texture.TextureFilter;
import com.woni.utils.Texture.TextureWrap;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLU;
import android.util.Log;


public class StartScreen implements GameScreen
{      
        boolean isDone = false;
        Font font;
        Text text;
        Text byText;
        Font titleFont;
        Text titleText;
        Font difficultyFont;
        Text difficultyTextSlow;
        Text difficultyTextMedium;
        Text difficultyTextFast;
        Text difficultyTextInsane;
        Text difficultyTextCrazy;
        String pressText = "Touch Screen to Start!";
        String byString = "by www.woni.at";
        
        GameActivity act;
        
        public IDifficulty difficulty;
       
        public StartScreen( GL10 gl, GameActivity activity )
        {                                
        	act = activity;
        	AssetManager manager = activity.getAssets();
            	titleFont = new Font(gl, manager, "Blox2.ttf", 72, FontStyle.Plain );
                font = new Font( gl, manager, "font.ttf", activity.getViewportWidth() > 480?32:16, FontStyle.Plain );
                difficultyFont = new Font( gl, manager, "font.ttf", 32, FontStyle.Plain );
                
                text = font.newText( gl );
                text.setText( pressText );
                
                titleText = titleFont.newText( gl );
                titleText.setText( "Stackerz" );
                
                difficultyTextSlow = difficultyFont.newText(gl);
                difficultyTextSlow.setText("Slow");
                
                difficultyTextMedium = difficultyFont.newText(gl);
                difficultyTextMedium.setText("Medium");
                
                difficultyTextFast = difficultyFont.newText(gl);
                difficultyTextFast.setText("Fast");
                
                difficultyTextInsane = difficultyFont.newText(gl);
                difficultyTextInsane.setText("Insane");
                
                difficultyTextCrazy = difficultyFont.newText(gl);
                difficultyTextCrazy.setText("Crazy");
                
                byText = font.newText(gl);
                byText.setText( byString);
                
                activity.listenerLoop = this;
        }      

        public boolean isDone()
        {      
                return isDone;
        }

        public void update(GameActivity activity)
        {      
        }
       
        public void render(GL10 gl, GameActivity activity)
        {      
    		gl.glViewport( 0, 0, activity.getViewportWidth(), activity.getViewportHeight() );
    		gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
    		gl.glEnable( GL10.GL_TEXTURE_2D );
    		gl.glMatrixMode( GL10.GL_PROJECTION );
    		gl.glLoadIdentity();
    		gl.glMatrixMode( GL10.GL_MODELVIEW );
    		gl.glLoadIdentity();
    		
    		gl.glEnable( GL10.GL_BLEND );
    		gl.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
    		
    		gl.glMatrixMode( GL10.GL_PROJECTION );
    		GLU.gluOrtho2D( gl, 0, activity.getViewportWidth(), 0, activity.getViewportHeight() );
    		gl.glMatrixMode( GL10.GL_MODELVIEW );
    		gl.glLoadIdentity();
    		
    		gl.glLoadIdentity();
    		gl.glTranslatef( activity.getViewportWidth() / 2 - font.getStringWidth( pressText ) / 2, 100, 0 );
    		text.render();
    		
    		gl.glLoadIdentity();
    		gl.glTranslatef( activity.getViewportWidth() / 2 - titleFont.getStringWidth( "Stackerz") / 2, activity.getViewportHeight() - 20, 0 );
    		titleText.render();
    		
    		gl.glLoadIdentity();
    		gl.glTranslatef( activity.getViewportWidth() / 2 - difficultyFont.getStringWidth( difficultyTextSlow.text) / 2, activity.getViewportHeight() - 210, 0 );
    		difficultyTextSlow.render();
    		
    		gl.glLoadIdentity();
    		gl.glTranslatef( activity.getViewportWidth() / 2 - difficultyFont.getStringWidth( difficultyTextMedium.text) / 2, activity.getViewportHeight() - 310, 0 );
    		difficultyTextMedium.render();
    		
    		gl.glLoadIdentity();
    		gl.glTranslatef( activity.getViewportWidth() / 2 - difficultyFont.getStringWidth( difficultyTextFast.text) / 2, activity.getViewportHeight() - 410, 0 );
    		difficultyTextFast.render();
    		
    		gl.glLoadIdentity();
    		gl.glTranslatef( activity.getViewportWidth() / 2 - difficultyFont.getStringWidth( difficultyTextInsane.text) / 2, activity.getViewportHeight() - 510, 0 );
    		difficultyTextInsane.render();
    		
       		gl.glLoadIdentity();
    		gl.glTranslatef( activity.getViewportWidth() / 2 - difficultyFont.getStringWidth( difficultyTextCrazy.text) / 2, activity.getViewportHeight() - 610, 0 );
    		difficultyTextCrazy.render();
    		    		
    		gl.glDisable( GL10.GL_TEXTURE_2D );
    		gl.glDisable( GL10.GL_BLEND );
        }
       
        public void dispose()
        {      
                font.dispose();
                text.dispose();
                byText.dispose();
                titleFont.dispose();
                titleText.dispose();
                difficultyTextFast.dispose();
                difficultyTextInsane.dispose();
                difficultyTextMedium.dispose();
                difficultyTextSlow.dispose();
                difficultyFont.dispose();
        }

		public void OnUp() {
			isDone = difficulty != null;
		}

		public void OnTouch(float x, float y) {
			if(y > 200f && y < 250f)
				difficulty = new DifficultySlow();
			if(y > 300f && y < 350f)
				difficulty = new DifficultyMedium();
			if(y > 400f && y < 450f)
				difficulty = new DifficultyFast();
			if(y > 500f && y < 550f)
				difficulty = new DifficultyInsane();
			if(y > 600f && y < 650f)
				difficulty = new DifficultyCrazy();
		}
}