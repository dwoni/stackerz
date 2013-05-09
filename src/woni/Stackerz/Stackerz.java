package woni.Stackerz;

import javax.microedition.khronos.opengles.GL10;

import com.woni.Stackerz.Screens.GameLoop;
import com.woni.Stackerz.Screens.GameOverScreen;
import com.woni.Stackerz.Screens.GameScreen;
import com.woni.Stackerz.Screens.StartScreen;
import com.woni.Stackerz.Simulation.Simulation;
import com.woni.utils.GameActivity;
import com.woni.utils.GameListener;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public class Stackerz extends GameActivity implements GameListener
{
        GameScreen screen;
        Simulation simulation = null;
        Bundle bundle;
       
        public void onCreate( Bundle bundle )
        {
                setRequestedOrientation(1);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
               
                super.onCreate( bundle );
                setGameListener( this );                
               
                if( bundle != null && bundle.containsKey( "simulation" ) )
                        simulation = (Simulation)bundle.getSerializable( "simulation" );
        }
       
        @Override
        public void onSaveInstanceState( Bundle outState )
        {
                super.onSaveInstanceState( outState );
                if( screen instanceof GameLoop )
                        outState.putSerializable( "simulation", simulation );
        }

        @Override
        public void onPause( )
        {
                super.onPause();
                if( screen != null )                    
                        screen.dispose();
        }
       
        @Override
        public void onResume( )
        {
                super.onResume();              
        }      
       
        @Override
        public void onDestroy( )
        {
                super.onDestroy();
        }
       
        public void setup(GameActivity activity, GL10 gl)
        {      
                if( simulation != null )
                {
                        screen = new GameLoop( gl, activity, simulation );
                }
                else
                {
                        screen = new StartScreen(gl, activity);
                }
        }

        long start = System.nanoTime();
        int frames = 0;

        public void mainLoopIteration(GameActivity activity, GL10 gl) {        
               
                screen.update( activity );
                screen.render( gl, activity);
               
                if( screen.isDone() )
                {
                        screen.dispose();

                        if( screen instanceof StartScreen ){
                                screen = new GameLoop( gl, activity, ((StartScreen)screen).difficulty );
                                simulation = ((GameLoop)screen).simulation;
                        }else if( screen instanceof GameLoop ) {
                        	if(simulation.gameWon){
                        		simulation.newGame();
                        		screen = new GameLoop(gl, activity, simulation);
                        		return;
                        	}
                        	screen = new GameOverScreen( gl, activity, ((GameLoop)screen).simulation.score, ((GameLoop)screen).simulation.difficulty.difficultyname);
                        
                        }else if( screen instanceof GameOverScreen ){
                                screen = new StartScreen(gl, activity);
                                simulation = null;
                        }
                }
               
                frames++;
                if( System.nanoTime() - start > 1000000000 )
                {
                        frames = 0;
                        start = System.nanoTime();
                }
        }
}



