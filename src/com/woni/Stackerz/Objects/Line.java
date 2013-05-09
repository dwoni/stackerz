package com.woni.Stackerz.Objects;

import com.woni.Stackerz.Simulation.Simulation;
import com.woni.utils.Vector;

public class Line {
	public int direction = 1;
	public int blockCount;
	public float blockSize = 0;
	public int level;
	private boolean set;
	public int removeBlocks;
	private float overallupdatetime;
	private float removeupdatetime;
	
	private float freeFields;

	private float baseSpeed;
	
	public int distance;
	public Vector position = new Vector();
	
	public Line(Vector position, int blocks, int level, float blockSize, float baseSpeed){
		blockCount = blocks;
		position.set(position);
		position.y = position.y * level;
		freeFields = Simulation.PLAYFIELD_MAX_X - blockCount;
		this.level = level;
		this.blockSize = blockSize;
		this.baseSpeed = baseSpeed;
	}
	
	
	public void set(boolean s){
		if(s)
			set = true;
		else
			baseSpeed *= 2;
	}
	
	public void removeBlock(int offset){
		if(offset > blockCount)
			offset = blockCount;
		if(offset < blockCount * -1)
			offset = blockCount *-1;
		int absOffset = Math.abs(offset);
		blockCount -= absOffset;
		removeBlocks = offset;
		
		if(offset < 0){
			distance += absOffset;
			position.x += blockSize * absOffset;
		}
	}
	
	public void update(float delta){
		if(removeBlocks != 0){
			removeupdatetime += delta;
			if(removeupdatetime > 1){
				removeBlocks = 0;
			}
		}
		
		
		if(set)
			return;
		
		overallupdatetime += delta;
		
		if(overallupdatetime > baseSpeed - level * 0.004){
			overallupdatetime = 0;
			distance += direction;

			position.x += blockSize * direction;
			
			if( distance >= freeFields || distance <= 0)
			{
				direction *= -1;
			}			
		}
	}
}
