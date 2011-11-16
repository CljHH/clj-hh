(ns net.cljhh.test.core
  (:use clojure.test
        midje.sweet
        ring.mock.request)
  (:require [compojure.route :as route]))

(def *undefined-response* ((route/not-found "foo") (request :get "/")))

;; converted compojure not-found-route-test to midje
(fact "test undefined routes"
      (:status *undefined-response*) => 404
      (:body   *undefined-response*) => "foo")
