(ns clj-hh.handler.login
  (:require
   [appengine-magic.services.user :as user-service]
   [clj-hh.return-value :as return-value]
   [clj-hh.session :as session]
   [clj-hh.user :as user]
   [clj-hh.utils.time :as time-utils]
   [clj-hh.utils.url :as url-utils]
   [ring.util.response :as response]))

(defn ^{:private true
        :added   0.1
        :doc     "Creates an user."}
  create-user
  [request google-user]
  (let [email       (user-service/get-email google-user)
        name        (user-service/get-nickname google-user)
        user-result (user/create-and-save-user! email name nil nil nil)]
    (if (return-value/success? user-result)
      (response/redirect "/login")
      (response/redirect "/error"))))

(defn ^{:private true
        :added   0.1
        :doc     "Logs the user in."}
  login-user
  [request email]

  (merge (session/create-session-for-user request email)
	 (response/redirect (url-utils/continue-url-from-request request))))

(defn ^{:private true
        :added   0.1
        :doc     "Logs the user in if he already has an account, otherwise creates one."}
  login-or-create-user
  [request]
  (let [google-user (user-service/current-user)
        email       (user-service/get-email google-user)
        user-result (user/get-by-email email)]
    (if (return-value/success? user-result)
      (login-user request email)
      (create-user request google-user))))

(defn ^{:added 0.1
        :doc   "Request handler for logging an user in"}
  handle-login
  [request]
  (if (session/user-logged-in? request)
    ;;already logged into clj-hh
    (response/redirect (url-utils/continue-url-from-request request))
    (if (user-service/user-logged-in?)
      ;;logged into a google account but not into clj-hh
      (login-or-create-user request)
      ;;not logged into a google account
      (let [continue-url (url-utils/with-continue-url "/login" (url-utils/continue-url-from-request request))]
        (response/redirect (user-service/login-url :destination continue-url))))))

(defn ^{:added 0.1
        :doc   "Request handler for loggin an user out"}
  handle-logout
  [request]
  (merge (session/close-session request)
         (response/redirect "/")))

