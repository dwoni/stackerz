package com.woni.Stackerz.Simulation;

import java.io.Serializable;
import java.util.ArrayList;

import woni.Stackerz.Difficulty.IDifficulty;

import com.woni.Stackerz.Objects.Block;
import com.woni.Stackerz.Objects.Line;
import com.woni.utils.GameActivity;
import com.woni.utils.Vector;

public class Simulation implements Serializable
{              
	private static final long serialVersionUID = -2477663152643651512L;
        public final static float PLAYFIELD_MAX_X = 15;
        public final static float PLAYFIELD_MAX_Z = 20;
        private float blockSize = 20;
        
        public ArrayList<Line> lines = new ArrayList<Line>();
        public transient SimulationListener listener;
        public int multiplier = 1;
        public int score;
        public boolean isDone;
        public boolean gameWon = false;
        
        private boolean waitforUp = false;
        private boolean init = true;
        private boolean gameOver = false;
        private float gameOverTimer;
        public float baseSpeed;
        
        private Line lastLine;
        private Line currentLine;
        
        public IDifficulty difficulty;
       
        public Simulation(GameActivity activity, float baseSpeed, IDifficulty difficulty)
        {
        	this.difficulty = difficulty;
        	blockSize = activity.getViewportWidth() / PLAYFIELD_MAX_X;
            this.baseSpeed = baseSpeed * difficulty.speedmodifier;
            populate(7);
        }
       
        public void newGame(){
        	lines.clear();
        	isDone = false;
        	gameOver = false;
        	gameWon = false;
        	gameOverTimer = 0;
        	baseSpeed = baseSpeed * 0.7f;
        	multiplier++;
        	populate(currentLine.blockCount + 2);
        }
        
        
        private void populate(int blockCount)
        {
        	lastLine = new Line(new Vector(0,0),blockCount, 0, blockSize, baseSpeed);
        		lastLine.set(difficulty.set);
        	lastLine.distance = (int)(PLAYFIELD_MAX_X - lastLine.blockCount) /2;
        	lastLine.position.x = lastLine.blockSize * lastLine.distance;
        	lines.add(lastLine);

			currentLine = new Line(new Vector(0,0),blockCount,1, blockSize, baseSpeed);
        	lines.add(currentLine);
        	
        	init = false;
        }
       
        public void update( float delta )
        {   
        	if(gameOver){
        		gameOverTimer += delta;
        		if(gameOverTimer > 1)
        			isDone = true;
        	} else {
	        	for(int b = 0; b < lines.size(); b++){
	        		lines.get(b).update(delta);
	        	}
        	}
        }
        
        public void touch(){    
        	if(gameOver || waitforUp || init)
        		return;
        		currentLine.set(difficulty.set);
        	
        	if(lastLine != null){
        		currentLine.removeBlock(currentLine.distance - lastLine.distance);
        	}
        	
        	lastLine = currentLine;
        	waitforUp = true;
        }
        
        public void up(){
        	if(waitforUp || init){
        	Line line = currentLine;
			score += line.level * line.blockCount * multiplier * difficulty.pointmodifier;
        	if(line.blockCount > 0 && line.level < PLAYFIELD_MAX_Z){
        		currentLine = new Line(new Vector(0,21 * (lines.size())), line.blockCount, line.level +1, blockSize, baseSpeed);
        		lines.add(currentLine);
        	}else if (line.blockCount > 0){
        		gameWon = true;
        		gameOver = true;
        	} else{
        		gameOver = true;
        	}
        	waitforUp = false;
        	}
        }
}

