(ns clj-hh.handler.profile
  (:require
   [net.cgrand.enlive-html :as html]
   [clj-hh.session :as session]))

(html/deftemplate show-profile "templates/profile.html"
   [request user]
   [:div.name :span.value] (html/content (:name user))
   [:div.email :span.value] (html/content (:email user))
   [:div.occupation :span.value] (html/content (:occupation user))
   [:div.description :span.value] (html/content (:description user))
   [:div.github :span.value] (html/content (:github user))
   [:div.last_active :span.value] (html/content (:last_active user)))

