(ns net.cljhh.templates
  (:import [com.google.template.soy SoyFileSet$Builder]
           [java.io File FilenameFilter]))

(defonce *renderer* (atom nil))
(defonce *template-compile-time* (atom 0))

(defn- add-development-info
  [data]
  (if (= "true" (System/getProperty "development"))
    (merge data {:development true})
    data))

(defn- file-if-exists
  [path]
  (let [file (File. path)]
    (if (.exists file)
      file
      nil)))

(defn- find-newest
  [files]
  (reduce (fn [max-time file] (max max-time (.lastModified file))) 1 files))

(defn- get-files
  []
  (let [file-filter (reify FilenameFilter (accept [_ file name] (.endsWith name ".soy")))
	directory (or (file-if-exists "web/templates")
		      (file-if-exists "templates"))]
    (.listFiles directory file-filter)))

(defn- add-files-to-builder
  "Adds all .soy files in war/templates to the supplied SoyFileSet$Builder and returns it"
  [builder files]
  (do
    (doseq [file files]
      (.add builder file))
    builder))

(defn init-templates
  "Initializes all .soy templates from the war/templates directory"
  [namespace]
  (let [files (get-files)]
    (if (> (find-newest files) (deref *template-compile-time*))
      (do
	(reset! *renderer*
		(-> (SoyFileSet$Builder.)
		   (add-files-to-builder files)
		   .build
		   .compileToJavaObj
		   (.forNamespace namespace)))
	(reset! *template-compile-time* (System/currentTimeMillis))))))

(defn render
  "Renders a template based on the compiled soy template.
  Parameters:
    package -> Name of the package, defaults to \"common\"
    template -> Name of the template
    data -> Data to render in the template, defaults to nil"
  ([template] (render "common" template nil))
  ([template data] (render "common" template data))
  ([package template data]
     (.render @*renderer* (str "." package "." template) (add-development-info data) nil)))

(defn wrap-templates
  [app namespace]
  (fn [req]
    (do
      (init-templates namespace)
      (app req))))
