(ns clj-hh.core
  (:require [appengine-magic.core :as ae]))


(defn clj-hh-app-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, Clojure Group!"})


(ae/def-appengine-app clj-hh-app #'clj-hh-app-handler)
