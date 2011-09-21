(ns clj-hh.utils.time
  (:require
   [clj-time.core :as date]
   [clj-time.format :as format])
  (:import
   [org.joda.time DateTime]))

(def hamburg-zone (date/time-zone-for-id "Europe/Berlin"))

(defn timestamp->date-time
  [ts]
  (DateTime. ts))

(defn date-time->timestamp
  [d]
  (.getMillis d))

(defn ^{:added 0.1
        :doc   "Creates a timestamp for the current time."}
  make-timestamp
  [& _]
  (date-time->timestamp (date/now)))

(defn render-day
  [d]
  (-> (:date (format/with-zone format/formatters hamburg-zone))
      (format/unparse d)))

(defn render-time
  [d]
  (-> (:hour-minute (format/with-zone format/formatters hamburg-zone))
      (format/unparse d)))
