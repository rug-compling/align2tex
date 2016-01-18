# align2tex
## Creates LaTeX source from word alignments

The plain word alignment files are [difficult](src/main/resources/sample.align) to read and interpret for a human. This simple tool lets you visualize the word alignments between parallel sentences by drawing edges between words. It outputs a LaTeX file (relying on TikZ) that can be compiled to PDF.

## Obtaining align2tex

A prebuilt version of align2tex will be available as a release.

## Building align2tex

The project can be built with sbt 0.13.5. Run `sbt assembly` to create a fat jar in `target/scala-2.11/`.

## Running align2tex <a name="run"></a>

```
java -jar align2tex.jar SOURCE_SENTS_FILE TARGET_SENTS_FILE ALIGNMENTS_FILE
```

## Example run

```
java -jar align2tex.jar src/main/resources/sample1.en src/main/resources/sample1.fr src/main/resources/sample1.align
```

This will produce `src/main/resources/sample1.align.tex` which can be compiled with `pdflatex` to give the following [result](sample1.align.pdf). Although some tweaking of TikZ spacing parameters is performed internally depending on the source- and target-sentence lengths, it might still be necessary to adjust the spacing manually.

## FAQ
### What languages are supported?

Any of the languages supported by the babel package for LaTeX. Currently, these are hard-coded in [LangCodes.scala](src/main/scala/LangCodes.scala). Simply using the ISO 639-1 language code (en, fr, ru, es, de etc.) as extension of the input sentence files is sufficient and should insert the correct language options for babel.

### Can I get the TikZ snippet without the LaTeX document boilerplate?
Yes, to get the `tikzpicture` block without the document and package definitions, [run](#run) align2tex by specifying `concise` as the last option.

(c) Simon Šuster, 2016
