
import java.io.*;
import java.util.*;

public class DriverString_RegionMatches {
	public static void main(String[] args) {
		boolean ignoreCase;
		int toffset;
		String other;
		int ooffset;
		int len;
    		String test = new String("hello");
		test.regionMatches(ignoreCase, toffset, other, ooffset, len);
	}
}
