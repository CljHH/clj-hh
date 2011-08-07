(ns clj-hh.app_servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use clj-hh.core)
  (:use [appengine-magic.servlet :only [make-servlet-service-method]]))


(defn -service [this request response]
  ((make-servlet-service-method clj-hh-app) this request response))
