(ns clj-hh.configuration)

(defmacro ^{:added 0.1
            :doc   "Create a function that returns the value of the system property with the same name.
  The string value is deserialized by calling the given function. A default value is used
  if the property is not defined."}
  defsystemproperty
  [name deserialization-function default-serialized-value]
  `(defn ~name []
     (~deserialization-function (System/getProperty ~(str name) ~default-serialized-value))))

(defsystemproperty development-system? Boolean/valueOf "false")
(defsystemproperty use-https? Boolean/valueOf "true")

(defn ^{:added 0.1
        :doc   "Sets the development environment properties"}
  set-development-properties
  []
  (System/setProperty "development-system?" "true")
  (System/setProperty "use-https?" "false"))
