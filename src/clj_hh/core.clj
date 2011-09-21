(ns clj-hh.core
  (:require
   [appengine-magic.core :as ae]
   [appengine-magic.multipart-params :as multipart]
   [clj-hh.handler.broadcast :as broadcast-handler]
   [clj-hh.handler.index :as index-handler]
   [clj-hh.handler.login :as login-handler]
   [clj-hh.handler.profile :as profile-handler]
   [clj-hh.session :as session]
   [compojure.core :as compojure]
   [ring.middleware.params :as params]))


(compojure/defroutes clj-hh-routes
  (compojure/GET "/" _   index-handler/show-index)
  (compojure/GET "/profile" _ (session/only-logged-in (session/with-user profile-handler/show-own-profile)))
  (compojure/GET "/profile/:id" [id] (session/only-logged-in (session/with-user profile-handler/show-profile id)))
  (compojure/GET "/login" _ login-handler/handle-login)
  (compojure/GET "/logout" _ login-handler/handle-logout)
  (compojure/POST "/broadcast" _ (session/only-logged-in broadcast-handler/post-broadcast)))

(def clj-hh-app-handler
  (-> clj-hh-routes
      (params/wrap-params)
      (multipart/wrap-multipart-params)
      (session/wrap-session)))

(ae/def-appengine-app clj-hh-app #'clj-hh-app-handler)
