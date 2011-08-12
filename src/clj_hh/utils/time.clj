(ns clj-hh.utils.time
  (:import [java.util Date]))

(defn ^{:added 0.1
        :doc   "Creates a timestamp for the current time."}
  make-timestamp
  [& _]
  (.getTime (java.util.Date.)))
