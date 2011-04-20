(ns net.cljhh.local
  (:require [com.freiheit.gae.local-dev :as local-dev]
            [net.cljhh.core :as core]
	    [net.cljhh.templates :as templates]))

(defn run-local []
  (local-dev/init-app-engine)
  (local-dev/start-server #'core/app))

(defn stop-local []
  (local-dev/stop-server))
