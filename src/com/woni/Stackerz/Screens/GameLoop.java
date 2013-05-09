package com.woni.Stackerz.Screens;

import javax.microedition.khronos.opengles.GL10;

import woni.Stackerz.Difficulty.IDifficulty;

import com.woni.Stackerz.Rendering.Renderer;
import com.woni.Stackerz.Simulation.Simulation;
import com.woni.Stackerz.Simulation.SimulationListener;
import com.woni.utils.GameActivity;

public class GameLoop implements GameScreen, SimulationListener
{
        public Simulation simulation;
        Renderer renderer;      

        public GameLoop( GL10 gl, GameActivity activity, IDifficulty difficulty )
        {
                simulation = new Simulation(activity, 0.3f, difficulty);
                simulation.listener = this;
                renderer = new Renderer( gl, activity );
                activity.listenerLoop = this;
        }
       
        public GameLoop(GL10 gl, GameActivity activity, Simulation simulation)
        {
                this.simulation = simulation;
                this.simulation.listener = this;
                renderer = new Renderer( gl, activity );
                activity.listenerLoop = this;
        }

        public void render(GL10 gl, GameActivity activity)
        {      
                renderer.render( gl, activity, simulation);
        }

        public void update(GameActivity activity)
        {      
                simulation.update( activity.getDeltaTime() );
        }
       
        private void processInput( GameActivity activity )
        {              
        }

        public boolean isDone( )
        {
        	return simulation.isDone;
        }
        
        public void OnTouch(float x, float y){
        	simulation.touch();
        }
        
        public void OnUp(){
        	simulation.up();
        }
       
        public void dispose( )
        {
        	renderer.dispose();
        }
}

