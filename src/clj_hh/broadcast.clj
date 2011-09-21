(ns clj-hh.broadcast
  (:require
   [appengine-magic.services.datastore :as datastore-service]
   [clj-hh.utils.time :as time-util]
   [clj-hh.return-value :as return-value]))

(datastore-service/defentity Broadcast [text user timestamp])

(defn create-and-save-broadcast!
  [text user]
  (return-value/success (datastore-service/save! (Broadcast. text user (time-util/make-timestamp)))))


(defn get-broadcasts
  []
  (return-value/success (datastore-service/query :kind Broadcast :sort [[:timestamp :dsc]])))
