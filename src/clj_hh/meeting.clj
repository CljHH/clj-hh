(ns clj-hh.meeting
  (:require
   [appengine-magic.services.datastore :as datastore-service]
   [clj-hh.return-value :as return-value]
   [clj-hh.utils.time :as time]
   [clj-time.core :as date]))

(datastore-service/defentity Meeting [location start topic description created])

(defn- meeting->db-meeting
  [m]
  (-> m
      (update-in [:start] time/date-time->timestamp)
      (update-in [:created] time/date-time->timestamp)))

(defn- db-meeting->meeting
  [d]
  (-> d
      (update-in [:start] time/timestamp->date-time)
      (update-in [:created] time/timestamp->date-time)))

(defn create-and-save-meeting!
  [location start topic description]
  (-> (Meeting. location start topic description (date/now))
      meeting->db-meeting
      datastore-service/save!
      return-value/success))

(defn upcoming-meetings
  []
  (->> (datastore-service/query :kind Meeting
                                :filter (> :start (time/make-timestamp))
                                :sort [:start])
       (map db-meeting->meeting)))

(defn next-meeting
  []
  (first (upcoming-meetings)))
