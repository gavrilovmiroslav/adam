
import java.io.*;
import java.util.*;
import java.math.*;

public class DriverBigInteger_ReadObject {
	public static void main(String[] args) {
		java.io.ObjectInputStream s;
    		BigInteger test = new BigInteger(0);
		test.readObject(s);
	}
}
