
import java.io._

import com.github.javaparser._
import com.github.javaparser.ast.visitor._
import com.github.javaparser.ast.body._
import com.github.javaparser.ast.stmt._

import scala.collection.JavaConverters._

object Adam {
  def showUsage = {
    import scala.io.Source

    for(line <- Source.fromResource("driver.ansi.txt").getLines) println(line)
    println("\t\t\tI think it's good to live an artful life. -- Adam Driver")
  }

  def main(args: Array[String]) = 
    ArgParser.parser.parse(args, Config()) match {
      case Some(config) =>
        var candidates = Set[MethodDeclaration]()
        try {
          val compilationUnit = JavaParser.parse(config.input)
          compilationUnit.findAll(classOf[IfStmt]).forEach(ifStatement => {
            val declaration = ifStatement.findParent(classOf[MethodDeclaration])
            if(declaration.isPresent) {
              val approximateLength = ifStatement.getChildNodes.asScala.toList match {
                case guard :: then :: other => then.toString.length + other.toString.length
                case guard :: then          => then.toString.length
                case _                      => 0
              }

              if(approximateLength > config.candidateLength)
                candidates += declaration.get
            }
          })

          new File("./drivers/").mkdir

          candidates.foreach { method =>
            val name = method.getName.toString
            val isStatic = method.isStatic
            val capitalName = name.capitalize
            val initializer = method.getParameters.asScala
                                    .map { _.toString + ";" }
                                    .mkString("\n\t\t")
            val arguments = method.getParameters.asScala
                                  .map { _.getName.toString }
                                  .mkString(", ")
            val imports = config.imports.map { imported => s"import ${imported};" }
                                        .mkString("\n")
            val printWriter = new PrintWriter(
              new File(s"./drivers/Driver${config.inputName}_${capitalName}.java"))

            val constructed = if(!isStatic) {
s"""\t\t${config.inputName} test = new ${config.inputName}(${config.ctorargs});
\t\ttest.${name}(${arguments});"""
            } else {
s"""\t\t${config.inputName}.${name}(${arguments});"""
            }

            printWriter.write(
s"""
${imports}

public class Driver${config.inputName}_${capitalName} {
	public static void main(String[] args) {
		${initializer}
    ${constructed}
	}
}
""")
            printWriter.close
          }
        } catch {
          case err: Throwable => println(err)
        }
      case None =>
        showUsage
        System.exit(0)
    }
}
