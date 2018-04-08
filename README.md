# adam
Driver generator for JPF/SPF projects. It generates a driver for any method that contains 1) a branch condition in it, and 2) the branch contains at least an expression of a given length.

## Usage

Basically, find a class in OpenJDK, get the raw link to it and save it as a .java.dump file. There are two included with the repository, in the `/bin` directory.

Then, run `adam` with the name of the class and any number of imports that you care about: 

`adam String -i 'java.io.*,java.util.*' -c '"Hello"' -l 120`

A `drivers` directory will be created if not already there, and a number of driver java files will be made in it. The methods will be chosen so that they are non-trivial. These driver files will call only a single method, making the needed arguments _uninitialized_ local variables:

```java
import java.io.*;
import java.util.*;

public class DriverString_ToUpperCase {
        public static void main(String[] args) {
                Locale locale;
                String test = new String("Hello");
                test.toUpperCase(locale);
        }
}
```

## Full Command-line Options

`<driver class name>`
  The class we're running the driver-generator for.
        
`-l, --candidate-length <value>`
  (Optional) The length of the candidate branch. Defaults to 100.
  
`-c, --ctor <value>`
  (Optional) The constructor arguments sent to the object.
  
`-i, --imports <value>`
  (Optional) Imports needed for driver to work.

Try running `adam` without any arguments to get this usage help.
