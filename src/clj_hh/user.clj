(ns clj-hh.user
  (:require
   [appengine-magic.services.datastore :as datastore-service]
   [clj-hh.return-value :as return-value]
   [clj-hh.utils.time :as time]))

(datastore-service/defentity User [^:key email name occupation description github-account last-active])

(def ^{:private true
       :added   0.1
       :doc     "Regex used for validating emails."}
  regex-email #"[a-zA-Z0-9-_.+]+@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]+")

(defn ^{:private true
        :added   0.1
        :doc     "Checks if the supplied email-address is valid."}
  invalid-email?
  [email]
  (nil? (re-matches regex-email email)))

(defn ^{:added 0.1
        :doc   "Validates user information"}
  valid-user-information?
  [email name]
  (return-value/error-when
   (or (empty? email)
       (invalid-email? email)) :invalid-email
   (empty? name) :invalid-name))

(defn ^{:added 0.1
        :doc   "Creates and saves an user."}
  create-and-save-user!
  ([email name occupation description github-account]
     (return-value/success (datastore-service/save! (User. email name occupation description github-account (time/make-timestamp))))))

(defn ^{:added 0.1
        :doc   "Fetches the user with the specified email from the datastore."}
  get-by-email
  [email]
  (if email
    (let [user (datastore-service/retrieve User email)]
      (if user
        (return-value/success user)
        (return-value/error :not-found email)))
    (return-value/error :empty-email)))
