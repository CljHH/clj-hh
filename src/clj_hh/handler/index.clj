(ns clj-hh.handler.index
  (:require
   [net.cgrand.enlive-html :as html]))

(html/deftemplate show-index "templates/index.html"
  [request])

