(ns clj-hh.utils.markdown
  (:import [com.petebevin.markdown MarkdownProcessor]))

(def mdprocessor (com.petebevin.markdown.MarkdownProcessor.))

(defn md
  "Convert Markdown to html. 

   \"# Markdown!\" -> `\"<h1>Markdown!</h1>\"`
  "
  [string-with-markdown-markup]
  (.markdown mdprocessor string-with-markdown-markup))

