(ns clj-hh.handler.index
  (:require
   [clj-hh.broadcast :as broadcast]
   [clj-hh.return-value :as return-value]
   [clj-hh.session :as session]
   [net.cgrand.enlive-html :as html]))

(html/defsnippet broadcast-snippet "templates/index.html" [:ul.broadcasts]
  [broadcast]
  [:div.text] (html/content (:text broadcast))
  [:div.user] (html/content (:user broadcast)))

(html/deftemplate render-index "templates/index.html"
  [broadcasts logged-in?]
  [:ul.broadcasts] (html/content (map broadcast-snippet broadcasts))
  [:form#post-broadcast] (fn [match] (if-not logged-in?
                                       ((html/content "") match)
                                       match)))

(defn show-index
  [request]
  (let [broadcasts (return-value/success-value (broadcast/get-broadcasts))
        logged-in? (session/user-logged-in? request)]
    (render-index broadcasts logged-in?)))

