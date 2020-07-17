package net.ogalab.util.rand;

import java.util.Date;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;


public class RNG {
	
	private static  RNG  instance = null;
	private Date    seed1 = null;
	private RandomEngine engine = null;
	
	private RNG() { 
		seed1 = new Date();
		engine = new MersenneTwister(seed1);
	}
	
	private RNG(int seed) { 
		engine = new MersenneTwister(seed);
	}
	

	public static synchronized RNG getInstance() {
		if (instance == null) {
			instance  = new RNG();
		}	
		return instance;
	}
	
	public static synchronized RNG getInstance(int seed) {
		if (instance == null) {
			instance  = new RNG(seed);
		}	
		return instance;
	}
	
	public static synchronized RandomEngine getEngine() {
		return getInstance().engine;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("## RNG seed" + seed1.toString() + "\n");
		return buf.toString();
	}
	
}
