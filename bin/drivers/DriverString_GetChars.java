
import java.io.*;
import java.util.*;

public class DriverString_GetChars {
	public static void main(String[] args) {
		int srcBegin;
		int srcEnd;
		char[] dst;
		int dstBegin;
    		String test = new String("hello");
		test.getChars(srcBegin, srcEnd, dst, dstBegin);
	}
}
