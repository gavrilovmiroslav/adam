
import java.io.*;
import java.util.*;

public class DriverString_GetBytes {
	public static void main(String[] args) {
		int srcBegin;
		int srcEnd;
		byte[] dst;
		int dstBegin;
    		String test = new String("hello");
		test.getBytes(srcBegin, srcEnd, dst, dstBegin);
	}
}
