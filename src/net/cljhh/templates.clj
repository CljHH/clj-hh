(ns net.cljhh.templates
  (:import [com.google.template.soy SoyFileSet$Builder]
           [java.io File FilenameFilter]))

(defonce *renderer* (atom nil))

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

(defn- add-files-to-builder
  "Adds all .soy files in war/templates to the supplied SoyFileSet$Builder and returns it"
  [builder]
  (let [file-filter (proxy [FilenameFilter] [] (accept [file name] (.endsWith name ".soy")))
        directory (or (file-if-exists "web/templates")
                      (file-if-exists "templates"))
        files (.listFiles directory file-filter)]
    (doseq [file files]
      (.add builder file))
    builder))

(defn init-templates
  "Initializes all .soy templates from the war/templates directory"
  []
  (reset! *renderer*
          (-> (SoyFileSet$Builder.)
              add-files-to-builder
              .build
              .compileToJavaObj
              (.forNamespace "net.cljhh"))))

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

(init-templates)
