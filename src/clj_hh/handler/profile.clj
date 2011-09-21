(ns clj-hh.handler.profile
  (:require
   [net.cgrand.enlive-html :as html]
   [clj-hh.user :as user]
   [clj-hh.return-value :as return-value]))

(html/deftemplate show-profile-template "templates/profile.html"
  [user]
  [:div.name :span.value] (html/content (:name user))
  [:div.email :span.value] (html/content (:email user))
  [:div.occupation :span.value] (html/content (:occupation user))
  [:div.description :span.value] (html/content (:description user))
  [:div.github :span.value] (html/content (:github user))
  [:div.last_active :span.value] (html/content (:last-active user)))

(defn show-profile
 [request user id]
  (if (= id (:name user))
    (show-own-profile request user)
    (let [person-result (user/get-by-name id)
          person (if (return-value/success? person-result)
                 (return-value/success-value person-result))]

      (show-profile-template person))))

(defn show-own-profile
  [request user]
  (show-profile-template user))

