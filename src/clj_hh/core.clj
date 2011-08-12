(ns clj-hh.core
  (:require
   [appengine-magic.core :as ae]
   [appengine-magic.multipart-params :as multipart]
   [clj-hh.handler.login :as login-handler]
   [clj-hh.session :as session]
   [compojure.core :as compojure]
   [ring.middleware.params :as params]))


(compojure/defroutes clj-hh-routes
  (compojure/GET "/" _   {:status  200
                          :headers {"Content-Type" "text/plain"}
                          :body    "Hello, Clojure Group!"})
  (compojure/GET "/login" _ login-handler/handle-login)
  (compojure/GET "/test/login" _ (session/only-logged-in
                                  (fn [request]
                                    (let [user (session/current-user request)]
                                      {:status  200
                                       :headers {"Content-Type" "text/plain"}
                                       :body    (str "Hello " (:email user))})))))

(def clj-hh-app-handler
  (-> clj-hh-routes
      (params/wrap-params)
      (multipart/wrap-multipart-params)
      (session/wrap-session)))

(ae/def-appengine-app clj-hh-app #'clj-hh-app-handler)
