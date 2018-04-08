
import java.io.File
import scopt._

case class Config(
	input: File = null, 
	inputName: String = "", 
	ctorargs: String = "", 
	imports: Seq[String] = Seq(),
	candidateLength: Int = 100)

object ArgParser {
  val parser = new OptionParser[Config]("adam") {
    head("adam", "0.9")

    arg[String]("<driver class name>")
    	.action((x, c) => 
    		c.copy(input = new File(s"${x}.java.dump"), inputName = x))

    opt[Int]('l', "candidate-length")
    	.optional
    	.withFallback(() => 100)
    	.action((x, c) => c.copy(candidateLength = x))

    opt[String]('c', "ctor")
    	.optional
    	.action((x, c) => c.copy(ctorargs = x))

    opt[Seq[String]]('i', "imports")
    	.optional
    	.action((x, c) => c.copy(imports = x))
  }
}

