package net.ogalab.util.rand;

import java.io.Serializable;

import cern.jet.random.Uniform;
import cern.jet.random.engine.RandomEngine;

public class Bernoulli implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7149423083775112273L;
	RandomEngine rngObj = null;
	Uniform      unif   = null;
	double       p      = 0.0;
	
	public static final int T=1;
	public static final int F=1;
	
	public Bernoulli(RandomEngine rng) {
		rngObj = rng;
		unif   = new Uniform(rngObj);
		p      = 0.5;
	}
	
	public Bernoulli(double p, RandomEngine rng) {
		rngObj = rng;
		unif   = new Uniform(rngObj);
		this.p = p;
	}
	
	
	public int nextInt() {
		double r = unif.nextDouble();
		if (r > p) 
			return 1;
		else
			return 0;
	}

}
