(ns clj-hh.local
  (:require [appengine-magic.core :as ae]
            [clj-hh.core :as core]))

(defn run-local
  [ & {:keys [port] :or {port 8080}}]
  (ae/serve core/clj-hh-app :port port))
