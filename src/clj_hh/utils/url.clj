(ns clj-hh.utils.url
  (:import [java.net URLEncoder URLDecoder]))

(defn ^{:added 0.1
        :doc "URL encodes a string using utf-8"}
  url-encode
  [string]
  (java.net.URLEncoder/encode string "UTF-8"))

(defn ^{:added 0.1
        :doc "URL decodes a string using utf-8"}
  url-decode
  [string]
  (java.net.URLDecoder/decode string "UTF-8"))

(defn ^{:added 0.1
        :doc   "Generates an url that contains a parameter for redirection later on."}
  with-continue-url
  [url continue-url]
  (str url "?continue=" (url-encode continue-url)))

(defn ^{:added 0.1
        :doc   "Gets the continue parameter from an request"}
  continue-url-from-request
  [request]
  (or (get-in request [:params "continue"])
      "/"))
