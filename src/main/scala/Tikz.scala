object Tikz {
  val TikzFooter: String = """\end{tikzpicture}"""

  def createTikzHeader(eLength: Double, fLength: Double): String = {
    val horizDist = 0.2
    val scale = fLength / eLength + 0.8
    val tikzHeader =
      """
        |\tikzstyle{blocke} = [node distance=1cm and %scm,inner sep=0,outer sep=0]
        |\tikzstyle{blockf} = [node distance=1cm and %scm,inner sep=0,outer sep=0]
        |\begin{tikzpicture}[align=left,text depth=3,text height=10pt]
        | """.stripMargin.format("%.2f".format(scale * horizDist), horizDist)
    tikzHeader
  }
}
