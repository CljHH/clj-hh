(ns clj-hh.broadcast
  (:require
   [appengine-magic.services.datastore :as datastore-service]
   [clj-hh.user :as user]
   [clj-hh.utils.time :as time-util]
   [clj-hh.return-value :as return-value]))

(datastore-service/defentity Broadcast [text user timestamp])

(defn create-and-save-broadcast!
  [text user]
  (return-value/success (datastore-service/save! (Broadcast. text user (time-util/make-timestamp)))))

(defn add-user-to-broadcast!
  [broadcast]
  (let [user-name (:name (user/get-by-email (:user broadcast)))
        time      (time-util/render-time (time-util/timestamp->date-time (:timestamp broadcast)))]
    (assoc broadcast :user user-name :time time)))


(defn get-broadcasts
  []
  (->> (datastore-service/query :kind Broadcast :sort [[:timestamp :dsc]])
       (take 10)
       (map add-user-to-broadcast!)
       (return-value/success)))
