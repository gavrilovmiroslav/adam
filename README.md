# adam
Driver generator for JPF/SPF projects

## TL;DR

```bash
java -jar adam.jar <Name of Class> [<Import>...]
```
## Usage

Basically, find a class in OpenJDK, get the raw link to it (example [BigInteger](http://hg.openjdk.java.net/jdk8/jdk8/jdk/raw-file/687fd7c7986d/src/share/classes/java/math/BigInteger.java) here), and save it as a .java.dump file. There are two included with the repository.

Then, run `adam` with the name of the class and any number of imports that you care about: 

`java -jar adam.jar "BigInteger" "java.math.*" "java.io.*" "java.util.*"`

A `drivers` directory will be created if not already there, and a number of driver java files will be made in it. The methods will be chosen so that they are non-trivial. These driver files will call only a single method, making the needed arguments _uninitialized_ local variables:

```java
import java.math.*;
import java.io.*;
import java.util.*;

public class DriverLucasLehmerSequence {
        public static void main(String[] args) {
                int z;
                BigInteger k;
                BigInteger n;
                BigInteger test;
                test.lucasLehmerSequence(z, k, n);
        }
}
```

Try running `adam` without any arguments to get the full usage explanation.
