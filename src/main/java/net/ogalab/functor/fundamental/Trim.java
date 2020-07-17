package net.ogalab.functor.fundamental;

import net.ogalab.functor.Functor;

public class Trim implements Functor<String> {

	public String func(String t) {
		return t.trim();
	}

}
