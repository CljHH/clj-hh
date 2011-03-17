(defproject clj-hh "0.1.0"
  :description      "Website of the clojure group hamburg"
  :repositories     {"snapshots" "http://maven.prettyrandom.net/"
                     "releases"  "http://maven.prettyrandom.net/"}
  :dependencies     [[org.clojure/clojure "1.2.0"]
                     [org.clojure/clojure-contrib "1.2.0"]
                     [clj-gae-datastore "0.3.0"]
                     [compojure "0.6.2"]
                     [ring/ring-core "0.3.7"]
                     [com.google/soy-clj "20100708"]]
  :dev-dependencies [[com.google.appengine/appengine-local-runtime "1.4.0"]
                     [marginalia "0.5.0"]
                     [swank-clojure "1.2.1"]
                     [lein-ring "0.3.3"]
                     [lein-gae "0.1.0"]
		     [midje "1.0.1"]]
  :ring             {:handler            net.cljhh.core/clj-hh-routes
                     :servlet-class      net.cljhh.servlet
                     :servlet-name       "CljHH"
                     :context-path       "/*"
                     :servlet-path-info? true}
  :gae              {:resources  ["web/WEB-INF" "web/templates" "web/css"]}
  :resources-path   "war")
