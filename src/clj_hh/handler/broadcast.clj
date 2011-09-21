(ns clj-hh.handler.broadcast
  (:require
   [clj-hh.broadcast :as broadcast]
   [clj-hh.session :as session]
   [ring.util.response :as response]))

(defn post-broadcast
  [request]
  (let [text      (get-in request [:params "text"])
        user      (session/current-user request)
        broadcast (broadcast/create-and-save-broadcast! text (get user :email))]
    (response/redirect "/")))
