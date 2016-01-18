object Latex {
  val Footer: String =
    """\end{document}
    """
  val SpaceBetweenTikz: String = """\vspace{0.5cm}"""

  // see unicode intricacies in Scala: https://issues.scala-lang.org/browse/SI-4706
  def createHeader(eLang: Option[String], fLang: Option[String]): String = {
    val langs: String = (eLang, fLang) match {
      case (Some(e), Some(f)) => e + "," + f
      case (None, None) => ""
      case (Some(e), None) => e
      case (None, Some(f)) => f
    }
    val header: String =
      """\documentclass[10pt]{article}
        |\""".stripMargin +
        """usepackage[utf8]{inputenc}
          |\""".stripMargin +
        """usepackage[T1,T2A]{fontenc}
          |\""".stripMargin +
        """usepackage[%s]{babel}
          |\""".stripMargin +
        """usepackage{tikz}
          |\""".stripMargin +
        """usetikzlibrary{positioning}
          |
          |\begin{document}""".stripMargin
    header.format(langs)
    //|\""".stripMargin + """usepackage[paperwidth=80cm, paperheight=30cm, margin=3pt]{geometry}
  }

}
