(ns clj-hh.utils.time
  (:require [clj-time.core :as date]))

(defn ^{:added 0.1
        :doc   "Creates a timestamp for the current time."}
  make-timestamp
  [& _]
  (.getMillis (date/now)))
