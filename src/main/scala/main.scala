
import java.io._

import com.github.javaparser._
import com.github.javaparser.ast.visitor._
import com.github.javaparser.ast.body._
import com.github.javaparser.ast.stmt._

import scala.collection.JavaConverters._

object Main {
	def main(args: Array[String]) = {
		var worthy = Set[MethodDeclaration]()

		if(args.length == 0) {
			import scala.io.Source

			for(line <- Source.fromResource("driver.ansi.txt").getLines) {
				println(line)
			}

			println("Usage:")
			println("	adam <driver class> [<imports...>]")

			System.exit(0);
		}

		val className = args(0)

		try {
			val cu = JavaParser.parse(new FileInputStream(s"${className}.java.dump"))
			cu.findAll(classOf[IfStmt]).forEach(f => {
				val om = f.findParent(classOf[MethodDeclaration])
				if(om.isPresent) {
					val approximateLength = f.getChildNodes.asScala.toList match {
						case guard :: then :: other =>
							then.toString.length + other.toString.length
						case guard :: then =>
							then.toString.length
						case _ => 
							0
					}

					if(approximateLength >= 100)
						worthy += om.get
				}
			})

			new File("./drivers/").mkdir();

			worthy.foreach { m => 
				val name = m.getName.toString
				val capitalName = name.capitalize
				val initializer = m.getParameters.asScala.map { p =>
					p.toString + ";"
				}.mkString("\n\t\t")

				val arguments = m.getParameters.asScala.map { p =>
					p.getName.toString
				}.mkString(", ")
				
				val imports = args.tail.map { i => s"import ${i};" }.mkString("\n")

				import java.io._
				val pw = new PrintWriter(new File(s"./drivers/Driver${capitalName}.java"))
				pw.write(
s"""
$imports
public class Driver$capitalName {
	public static void main(String[] args) {
		$initializer
		$className test;
		test.$name($arguments);
	}
}
""")					
				pw.close
			}
		} catch {
			case x: ParseException =>
		}
	}
}