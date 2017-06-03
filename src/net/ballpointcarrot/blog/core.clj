(ns net.ballpointcarrot.blog.core
  (use [hiccup.page :refer (include-css include-js)]))

(defn header
  "Provides the HTML head tag attributes for all pages"
  [metadata]
  [:head
   ;; Meta information
   [:meta {:charset "utf-8"}]
   [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
   (if (:meta-description metadata)
     [:meta {:name "description" :content (:meta-description metadata)}])
   ;; Styling links
   (include-css "/assets/css/screen.css"
                "//fonts.googleapis.com/css?family=Noto+Serif:400,700,400italic%7COpen+Sans:700,400"
                "/assets/css/prism.css")
   
   ])

(defn date-string [date]
  (if (nil? date)
    nil
    (let [format (java.time.format.DateTimeFormatter/ofPattern "EE, d MMM yyyy")
          localdate (java.time.LocalDateTime/ofInstant (.toInstant date) (java.time.ZoneId/systemDefault))]
      (.format format localdate))))

(defn js-includes
  "Include common Javascript files"
  []
  (include-js "/assets/js/index.js"
              "/assets/js/prism.js"))
(defn footer
  "Provides final common page details."
  [metadata]
  )
