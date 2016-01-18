import scala.io.Source
import java.io.{File, PrintWriter}

import Latex._
import Tikz._
import LangCodes._

/** Example source, target and alignment:
  * e = "SAN FRANCISCO – It has never been easy to have a rational conversation about the value of gold ."
  * f = "SAN FRANCISCO – Nunca ha resultado fácil sostener una conversación racional sobre el valor del oro ."
  * a = "15-17 14-16 0-0 9-12 5-6 8-10 12-14 3-5 6-7 7-9 16-18 13-15 1-1 2-2 3-3 4-4 10-11 11-13"
  * */

object Viz {
  def main(args: Array[String]) {
    if (args.length < 3 || (args.length == 4 && !args(3).toLowerCase.equals("concise"))) {
      println("Usage:")
      println("java target/scala-2.11/align2tex-assembly-1.0.jar SOURCE_SENTS_FILE TARGET_SENTS_FILE ALIGNMENTS_FILE")
      println("Use 'concise' as additional argument if you desire TikZ-only output without LaTeX document boilerplate.")
      System.exit(1)
    }

    val eFile = args(0) // source
    val fFile = args(1) // target
    val aFile = args(2) // alignment
    val useConcise = args.length == 4 && args(3).equals("concise")
    val eLang = getLangCode(eFile)
    val fLang = getLangCode(fFile)
    val eSource = Source.fromFile(eFile).getLines()
    val fSource = Source.fromFile(fFile).getLines()
    val aSource = Source.fromFile(aFile).getLines()

    val outFile = aFile + ".tex"
    val out = new PrintWriter(new File(outFile))

    if (!useConcise) out.write(createHeader(eLang, fLang))

    while (eSource.hasNext && fSource.hasNext && aSource.hasNext) {
      val sent = new Sentence(eSource.next, fSource.next, aSource.next)
      out.write(createTikzHeader(sent.e.length, sent.f.length))
      getNodes(sent.eIndex, "e").foreach(x => out.write(x + "\n"))
      getNodes(sent.fIndex, "f").foreach(x => out.write(x + "\n"))
      getDraws(sent.alignments).foreach(x => out.write(x + "\n"))
      out.write(TikzFooter + "\n")
      out.write(SpaceBetweenTikz + "\n")
    }
    if (!useConcise) out.write(Footer)

    out.close()
    println("Output written to %s.".format(outFile))
  }

  private def getNodes(sIndex: Array[(String, String)], typ: String): Array[String] = sIndex.zipWithIndex map {
    case ((i, w), 0) if typ.equals("e") => """\node[block%s] (%s) {%s};""".format(typ, i, normalize(w))
    case ((i, w), 0) if typ.equals("f") => """\node[block%s, below=of 0e] (%s) {%s};""".format(typ, i, normalize(w))
    case ((i, w), _) =>
      val iPrev: Int = i.stripSuffix(typ).toInt - 1
      """\node[block%s, right=of %s] (%s) {%s};""".format(typ, iPrev.toString + typ, i, normalize(w))
  }

  private def normalize(w: String) = w match {
    case "%" | "$" => """\""" + w
    case "\u2011" => "-"
    case _ => w
  }

  private def getDraws(alignments: Array[Array[String]]): Array[String] = {
    for (align <- alignments) yield """\draw (%sf.north) to (%se.south);""".format(align(0), align(1))
  }

  def getLangCode(fileName: String): Option[String] = {
    val ending = fileName.slice(fileName.length - 3, fileName.length)
    if (ending.startsWith(".")) Codes.get(ending.drop(1))
    else None
  }
}
