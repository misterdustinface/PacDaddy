package Engine;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Utilities_StringToIntArray_UT {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_nullArgument_expectEmpty0x0Array() {
		int[][] expected = new int[0][0];
		int[][] result   = Utilities.StringToIntArray(null);
		equals(expected, result);
	}
	
	@Test
	public void test_singleCharacterInputIsDigit_expectOneDigitIn1x1Array() {
		String  input    = "0";
		int[][] expected = new int[1][1];
		expected[0][0]   = 0;
		int[][] result   = Utilities.StringToIntArray(input);
		equals(expected, result);
	}
	
	@Test
	public void test_singleCharacterInputIsChar_expectEmpty1x1Array() {
		String  input    = "A";
		int[][] expected = new int[1][1];
		int[][] result   = Utilities.StringToIntArray(input);
		equals(expected, result);
	}
	
	@Test
	public void test_3LineString_expect3x3Array() {
		String  input    = "111" + System.lineSeparator() 
						 + "101" + System.lineSeparator() 
						 + "111";
		int[][] expected = new int[][]{	{1,1,1},
										{1,0,1},
										{1,1,1}};
		int[][] result   = Utilities.StringToIntArray(input);
		equals(expected, result);
	}
	
	private void equals(int[][] A, int[][] B) {
		assertEquals(A.length, B.length);
		for (int row = 0; row < A.length; row++) {
			assertTrue(Arrays.equals(A[row], B[row]));
		}
	}

}
