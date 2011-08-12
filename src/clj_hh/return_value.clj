(ns clj-hh.return-value)

(defstruct return-value :result :success :errors)

(defprotocol ReturnValueProtocol
  (error? [this] [this error-key])
  (success? [this])
  (error-map [this])
  (error-keys [this])
  (error-context [this error-key])
  (success-value [this]))

(defrecord ReturnValue [result success errors]
  ReturnValueProtocol
  (error? [this]
    (not (empty? (error-map this))))
  (error? [this error-key]
    (contains? (set (keys (error-map this))) error-key))
  (success? [this]
    (not (error? this)))
  (error-map [this]
    (:errors this))
  (error-keys [this]
    (keys (error-map this)))
  (error-context [this error-key]
    (-> (error-map return-value)
        (get error-key)))
  (success-value [this]
    (:result this)))

(defn ^{:added 0.1
        :doc   "Creates an erroneous result having either a single error reason
                or an error reason and context."}
  error
  ([error-key]
     (error error-key nil))
  ([error-key error-context]
     (ReturnValue. nil false {error-key error-context})))

(defn ^{:added 0.1
        :doc   "Creates an erroneous result having multiple errors."}
  errors
  [error-map]
  (ReturnValue. nil false error-map))

(defn ^{:added 0.1
        :doc   "Creates a successful result."}
  success
  [result]
  (ReturnValue. result true nil))

(defn- ^{:private true
         :added   0.1
         :doc     "Creates an error-map based on the expression supplied."}
  generate-error-expr
  [expr]
  (cond
   (list? expr) {(first expr) (second expr)}
   :else {expr true}))

(defmacro error-when
  [& clauses]
  (assert (= (mod (count clauses) 2) 0))
  `(let [ret# (reduce (fn [all-errs# new-err#] (merge all-errs# new-err#)) {}
                      (vector ~@(for [[predicate err-expr] (partition 2 clauses)]
                                  `(when ~predicate ~(generate-error-expr err-expr)))))]
     (if (empty? ret#)
       (success true)
       (errors ret#))))


