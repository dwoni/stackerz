package com.woni.Stackerz.Objects;

import com.woni.Stackerz.Simulation.Simulation;
import com.woni.utils.Vector;

public class Block {
	   public final static float BLOCK_RADIUS = 0.5f; 	
		public static float INVADER_VELOCITY = 1;
		public float movedDistance = 0;
		public final static int STATE_MOVE_LEFT = 0;
		public final static int STATE_MOVE_RIGHT = 1;
		
		private boolean set;
		private int blocksize = 20;
		
		public int state = STATE_MOVE_RIGHT;
		public Vector position = new Vector( );
		public boolean wasLastStateLeft = true;
		private int distance = 0;
		
	   public Block(Vector position) 
	   {
	      this.position.set( position );
	   }
	   
	   public Block(Vector position, int level) 
	   {
	      this.position.set( position );
	   }
	   
	   public void set(){
		   set = true;
	   }
	   
		public void update(float delta, float speedMultiplier) 
		{	
			if(set)
				return;
			
			movedDistance += delta;
			
			if(movedDistance > 0.5){
				movedDistance = 0;
				distance++;

				position.x += blocksize;
				
				if( distance == Simulation.PLAYFIELD_MAX_X )
				{
					movedDistance = 0;
					distance = 0;
					blocksize *= -1;
				}			
			}
		}
}
