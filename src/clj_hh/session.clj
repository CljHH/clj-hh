(ns clj-hh.session
  (:require
   [appengine-magic.services.user :as user-service]
   [clj-hh.user :as user]
   [clj-hh.utils.time :as time-utils]
   [clj-hh.utils.url :as url-utils]
   [ring.middleware.session :as ring-session]
   [ring.middleware.session.cookie :as ring-session-cookie]
   [ring.util.response :as response])
  (:use
   [clojure.contrib.core :only [-?>]]))

(def ^{:private true
       :added   0.1
       :doc     "The key used to encrypt the session cookie."}
  *session-encryption-key*
  "loVeic5oes3ohd2Ygeej0Fee")

(defn ^{:added 0.1
        :doc   "Gets data from the session.
  If no data-key is specified returns all data -
  otherwise just the data for the specified key."}
  get-data-from-session
  ([request]
     (get-in request [:session :data]))
  ([request data-key]
     (get-in request [:session :data data-key])))

(defn ^{:added 0.1
        :doc   "Stores data in the session for the response,
  meaning that you just get the merged session info from request
  and the supplied data but not the rest of the request as a return value."}
  store-data-in-session
  [request data]
  (let [old-data (get-in request [:session :data])]
    (assoc-in {} [:session :data] (merge old-data data))))

(defn ^{:added 0.1
        :doc   "Closes the session."}
  close-session!
  [request]
  (assoc request :session nil))

(defn ^{:added 0.1
        :doc   "Extends the session timeout to 30minutes from now."}
  extend-session-timeout
  [request]
  (let [new-timeout (+ (time-utils/make-timestamp)
                       1800000)]
    (store-data-in-session request {:valid-until new-timeout})))

(defn ^{:added 0.1
        :doc   "Checks if the session timeout was reached."}
  session-timeout-reached?
  [request]
  (let [valid-until (get-data-from-session request :valid-until)
        now         (time-utils/make-timestamp)]
    (> valid-until now)))

(defn ^{:added 0.1
        :doc   "Stores the user in the session and sets the session timeout"}
  create-session-for-user
  [request user]
  (let [data (extend-session-timeout request)]
    (store-data-in-session request (assoc data :user user))))

(defn ^{:added 0.1
        :doc   "Get's the current user that is logged in or nil if not logged in."}
  current-user
  [request]
  (-?> (get-data-from-session request :user)
       (user/get-by-email)))

(defn ^{:added 0.1
        :doc   "Check's if the user is logged in."}
  user-logged-in?
  [request]
  (let [valid-until (get-data-from-session request :valid-until)
        now         (time-utils/make-timestamp)]
    (and (not (nil? (current-user request)))
         ( > valid-until now))))

(defn ^{:added 0.1
        :doc   "If the user is not currently logged in, redirects to the login page,
  otherwise uses the supplied request hanlder and params"}
  only-logged-in
  [f & params]
  (fn [request]
    (if (user-logged-in? request)
      (apply f request params)
      (response/redirect (url-utils/with-continue-url "/login" (:uri request))))))

(defn ^{:added 0.1
        :doc   "Wraps the application with a session stored in an encrypted cookie."}
  wrap-session
  [handler]
  ;; TODO: read configuration and add secure if ssl is supported
  (let [cookie-attrs {}
        cookie-store (ring-session-cookie/cookie-store {:key *session-encryption-key*})]
    (ring-session/wrap-session handler {:store        cookie-store
                                        :cookie-attrs cookie-attrs})))
