package com.woni.Stackerz.Screens;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.woni.Stackerz.Sounds.SoundManager;
import com.woni.utils.Font;
import com.woni.utils.GameActivity;
import com.woni.utils.Highscore;
import com.woni.utils.Mesh;
import com.woni.utils.Texture;
import com.woni.utils.Font.FontStyle;
import com.woni.utils.Font.Text;
import com.woni.utils.Mesh.PrimitiveType;
import com.woni.utils.Texture.TextureFilter;
import com.woni.utils.Texture.TextureWrap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.opengl.GLU;
import android.util.Log;


public class GameOverScreen implements GameScreen 
{       
        boolean isDone = false;
        Font font;
        Text text;
        Text highscoreText;
        String pressText = "";
        Highscore scores;
        ArrayList<Text> highscoretexts = new ArrayList<Text>();
        
        public GameOverScreen( GL10 gl, GameActivity activity, int score, String difficulty )
        {                                      
                font = new Font( gl, activity.getAssets(), "font.ttf", activity.getViewportWidth() > 480?32:16, FontStyle.Plain );
                text = font.newText( gl );
                pressText = "Your Score: " + score + " Points!";
                text.setText( pressText );
                
                highscoreText = font.newText(gl);
                highscoreText.setText("Highscores:");
                
                activity.listenerLoop = this;
                scores = new Highscore(activity);
                scores.addScore(difficulty, score);
                long[] highscores = scores.getScores();
                String[] names = scores.getNames();
                
                for(int i = 0; i < highscores.length; i++){
                	Text t = font.newText(gl);
                    t.setText(names[i] + "  " + highscores[i]);
                    highscoretexts.add(t);
                }
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
                gl.glTranslatef( activity.getViewportWidth() / 2 - font.getStringWidth( pressText ) / 2, 200, 0 );
                text.render();
                
                gl.glLoadIdentity();
                gl.glTranslatef( activity.getViewportWidth() / 2 - font.getStringWidth( highscoreText.text ) / 2, activity.getViewportHeight() - 20, 0 );
                highscoreText.render();
                
                for(int i = 0; i < highscoretexts.size(); i++){
                	Text t = highscoretexts.get(i);
                	gl.glLoadIdentity();
                    gl.glTranslatef( activity.getViewportWidth() / 2 - font.getStringWidth(t.text) / 2, activity.getViewportHeight() - 50 - 18 * i, 0 );
                    t.render();
                }
                
                gl.glDisable( GL10.GL_TEXTURE_2D );
                gl.glDisable( GL10.GL_BLEND );
        }
        
        public void dispose() 
        {       
                font.dispose();
                text.dispose();
                
                for(int i = 0; i < highscoretexts.size(); i++){
                	highscoretexts.get(i).dispose();
                }
        }

		public void OnUp() {
            isDone = true;
		}

		public void OnTouch(float x, float y) {
			// TODO Auto-generated method stub
			
		}
}
