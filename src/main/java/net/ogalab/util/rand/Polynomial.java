package net.ogalab.util.rand;

import java.util.ArrayList;

import cern.jet.random.Uniform;

import net.ogalab.util.container.ListUtil;



public class Polynomial {
	  private  Uniform uniform = new Uniform(RNG.getEngine());
	  double   total   = 0.0;
	  double[] distribution = null;
	  
	  
	  public static void main(String[] args) {
		  double[] dist = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };
		  Polynomial poly = new Polynomial();
		  poly.setDistribution(dist);

		  
		  for (long i=0; i<100000; i++) {
			  System.out.println(poly.nextInt());
		  }
	  }
	  
	  public void init() {
		  calcTotal();
	  }
	  
	  public void setDistribution(double[] dist) {
		  distribution = dist;  
		  calcTotal();
	  }
	  
	  public void setDistribution(ArrayList<Double> dist) {
		  distribution = ListUtil.to_double_array(dist);
		  calcTotal();
	  }
	  
	  public void setTotal(double t) {
		  total = t;
	  }
	  
	  public void calcTotal() {
		  double t = 0.0;
		  for (int i=0; i<distribution.length; i++) {
			  t += distribution[i];
		  }
		  total = t;
	  }
	  
	  public int nextInt() {
		  double r = uniform.nextDoubleFromTo(0.0, total);
		  double subTotal = 0.0;
		  for (int i=0; i<distribution.length; i++) {
			  subTotal += distribution[i];
			  if (r < subTotal) {
				  return i;
			  }
		  }
		  return 0;
	  }
	  
}
