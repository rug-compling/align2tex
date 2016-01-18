class Sentence(val e: String, val f: String, val a: String) {
  lazy val eIndex = index(e, "e")
  lazy val fIndex = index(f, "f")
  lazy val alignments: Array[Array[String]] = a.split(" ") map (_.split("-"))

  override def toString = s"$e\n$f\n$a"

  private def index(sent: String, typ: String): Array[(String, String)] = {
    for ((w, c) <- sent.split(" ").zipWithIndex) yield (s"$c$typ", w)
  }
}
