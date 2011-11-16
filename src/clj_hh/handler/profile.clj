(ns clj-hh.handler.profile
  (:require
   [net.cgrand.enlive-html :as html]
   [clj-hh.user :as user]
   [clj-hh.utils.time :as time]
   [clj-hh.return-value :as return-value]
   [ring.util.response :as response]))

(html/deftemplate show-profile-template "templates/profile.html"
  [user]
  [:div.name :span.value] (html/content (:name user))
  [:div.email :span.value] (html/content (:email user))
  [:div.occupation :span.value] (html/content (:occupation user))
  [:div.description :span.value] (html/content (:description user))
  [:div.github :span.value] (html/content (:github user))
  [:div.last-active :span.value] (html/content (:last-active user)))

(html/deftemplate edit-profile-template "templates/profile_edit.html"
  [user]
  [:div.name :input] (html/set-attr "value" (:name user))
  [:div.email :input] (html/set-attr "value" (:email user))
  [:div.occupation :input] (html/set-attr "value" (:occupation user))
  [:div.description :input] (html/set-attr "value" (:description user))
  [:div.github :input] (html/set-attr "value" (:github user))
  [:div.last-active :span.value] (html/content (time/render-day (time/timestamp->date-time (:last-active user)))))

(defn show-own-profile
  [request user]
  (edit-profile-template user))

(defn show-profile
 [request user id]
 (let [person (user/get-by-name id)]
   (if (= (:email person) (:email user))
     (show-own-profile request user)
     (let [person-result (if (return-value/success? person)
                    (return-value/success-value person))]
       (show-profile-template person-result)))))

(defn save-profile
  [{params :params} user]
  (user/update-user! user (dissoc params "last-active"))
  (response/redirect "/profile"))
