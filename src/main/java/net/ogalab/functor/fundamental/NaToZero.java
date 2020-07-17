package net.ogalab.functor.fundamental;

import net.ogalab.functor.Functor;

public class NaToZero  implements Functor<String> {
	
	public String func(String t) {

		if (t.equals("\\N"))
			return "0";
		else
			return t;
		
	}
	

}
