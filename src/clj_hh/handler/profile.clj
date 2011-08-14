(ns clj-hh.handler.profile
  (:require
   [net.cgrand.enlive-html :as html]))

(html/deftemplate show-profile "templates/profile.html"
  [request])
