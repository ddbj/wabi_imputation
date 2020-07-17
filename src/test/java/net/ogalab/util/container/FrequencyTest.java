package net.ogalab.util.container;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FrequencyTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testGetFreq() {
		Frequency<String> freq = new Frequency<String>();
		freq.add("AA");
		freq.add("AA");
		freq.add("Aa");

		assertEquals(2, freq.getFreq("AA"));
		assertEquals(1, freq.getFreq("Aa"));
		assertEquals(0, freq.getFreq("aa"));
	}


}
