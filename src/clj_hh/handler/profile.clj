(ns clj-hh.handler.profile
  (:require
   [net.cgrand.enlive-html :as html]
   [clj-hh.session :as session]))

(html/deftemplate show-profile "templates/profile.html"
   [request user]
   [:span.name] (html/content (:name user))
   [:span.email] (html/content (:email user)))

