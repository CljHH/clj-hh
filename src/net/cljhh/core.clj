(ns net.cljhh.core
  (:use [compojure.core :only [defroutes GET]])
  (:require [net.cljhh.templates :as templates]
            [net.cljhh.users :as users]
            [ring.middleware.cookies :as cookies]
            [ring.middleware.params :as params]))

(defroutes clj-hh-routes
  (GET "/users" _ (templates/render "user" "list" {:users (users/get-all)}))
  (GET "/*" _ (templates/render "welcome" {})))

(def app
  (-> clj-hh-routes
      (cookies/wrap-cookies)
      (params/wrap-params)))

