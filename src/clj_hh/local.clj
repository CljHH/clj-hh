(ns clj-hh.local
  (:require [appengine-magic.core :as ae]
            [clj-hh.core :as core]))

(defn run-local
  []
  (ae/serve core/clj-hh-app))
