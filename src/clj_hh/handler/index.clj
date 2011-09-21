(ns clj-hh.handler.index
  (:require
   [clj-hh.broadcast :as broadcast]
   [clj-hh.meeting :as meeting]
   [clj-hh.return-value :as return-value]
   [clj-hh.session :as session]
   [clj-hh.utils.time :as time]
   [net.cgrand.enlive-html :as html]))

(html/defsnippet broadcast-snippet "templates/index.html" [:ul.broadcasts :li.broadcast]
  [broadcast]
  [:div.text] (html/content (:text broadcast))
  [:div.user] (html/content (str (:user broadcast) " broadcasted"))
  [:div.time] (html/content (:time broadcast)))

(html/deftemplate render-index "templates/index.html"
  [broadcasts next-meeting logged-in?]
  [:ul.broadcasts] (html/content (map broadcast-snippet broadcasts))
  [:form#post-broadcast] (fn [match] (if-not logged-in?
                                       ((html/content "") match)
                                       match))
  [:h4#nextmeeting] (html/content (str "next meeting: " (time/render-day (:start next-meeting))))
  [:td#nextmeeting_location] (html/content (:location next-meeting))
  [:td#nextmeeting_time] (html/content (time/render-time (:start next-meeting)))
  [:td#nextmeeting_topics] (html/content (:topic next-meeting)))

(defn show-index
  [request]
  (let [broadcasts (return-value/success-value (broadcast/get-broadcasts))
        next-meeting (meeting/next-meeting)
        logged-in? (session/user-logged-in? request)]
    (render-index broadcasts next-meeting logged-in?)))

