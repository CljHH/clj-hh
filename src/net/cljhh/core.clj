(ns net.cljhh.core
  (:use [compojure.core :only [defroutes GET]])
  (:require [net.cljhh.templates :as templates]
            [ring.middleware.cookies :as cookies]
            [ring.middleware.params :as params]))

(defroutes clj-hh-routes
  (GET "/*" _ (templates/render "welcome" {})))

(def app
  (-> clj-hh-routes
      (cookies/wrap-cookies)
      (params/wrap-params)))

