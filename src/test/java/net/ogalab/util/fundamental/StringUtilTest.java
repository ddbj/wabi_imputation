package net.ogalab.util.fundamental;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testSplitByChar() {
		String str = "abcdefgabcdefgabcdefg";

		ArrayList<String> result = null;

		// When a delimiting character is placed at the head of a string,
		// the first element of the returned list should be an empty string.
		result = StringUtil.splitByChar(str, 'a');
		assertEquals(4, result.size());
		assertEquals("", result.get(0));
		for (int i=1; i<=3; i++) {
			assertEquals("bcdefg", result.get(i));			
		}
		
		// When a delimiting character is at the last of a string,
		// the last element of the returned list should be an empty string.
		result = StringUtil.splitByChar(str, 'g');
		assertEquals(4, result.size());
		for (int i=0; i<3; i++) {
			assertEquals("abcdef", result.get(i));			
		}
		assertEquals("", result.get(3));
		
		// -----------
		
		str = "abcdefgaabcdefg";
		result = StringUtil.splitByChar(str, 'a');
		assertEquals(4, result.size());
		assertEquals("", result.get(0));
		assertEquals("bcdefg", result.get(1));
		assertEquals("", result.get(2));
		assertEquals("bcdefg", result.get(3));
		
		str = "abcdefgaaabcdefg";
		result = StringUtil.splitByChar(str, 'a');
		assertEquals(5, result.size());
		assertEquals("", result.get(0));
		assertEquals("bcdefg", result.get(1));
		assertEquals("", result.get(2));
		assertEquals("", result.get(3));
		assertEquals("bcdefg", result.get(4));
		
		// --------
		str = "";
		result = StringUtil.splitByChar(str, 'a');
		assertEquals(1, result.size());
		assertEquals("", result.get(0));
		
		// --------
		str = "abcdefg";
		result = StringUtil.splitByChar(str, 'x');
		assertEquals(1, result.size());
		assertEquals("abcdefg", result.get(0));
		
	}
	
	@Test
	public void testSplitByTab() {
		ArrayList<String> result = null;

		String str = "\tbcdefg\t\tbcdefg\t";
		
		result = StringUtil.splitByTab(str);
		assertEquals("", result.get(0));
		assertEquals("bcdefg", result.get(1));
		assertEquals("", result.get(2));
		assertEquals("bcdefg", result.get(3));
		
	}
	
	@Test
	public void testSplitByRegex() {
		ArrayList<String> result = null;

		String str = "abcdefgaabcdefga";
		
		result = StringUtil.splitByRegex(str, Pattern.compile("a"));
		assertEquals(5, result.size());
		assertEquals("", result.get(0));
		assertEquals("bcdefg", result.get(1));
		assertEquals("", result.get(2));
		assertEquals("bcdefg", result.get(3));
		assertEquals("", result.get(4));
		
	}
	
	
}
