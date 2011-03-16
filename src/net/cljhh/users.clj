(ns net.cljhh.users
  (:use [com.freiheit.gae.datastore.datastore-query-dsl :only [select where]]
        [com.freiheit.gae.datastore.datastore-access-dsl :only [store-or-update-entities!
                                                                delete-all!
                                                                defentity]]
        [com.freiheit.gae.datastore.keys :only [make-key make-web-key]]
        [com.freiheit.gae.datastore.datastore-types :only [to-sexpr-text from-sexpr-text]]))

(defentity User
  [:key :pre-save make-key :post-load make-web-key]
  [:lastname]
  [:firstname]
  [:email]
  [:password])

(defn get-all
  []
  (map #(dissoc % :parent-key) (select (where User []))))

