(ns net.ballpointcarrot.blog.views.archive
  (require [net.ballpointcarrot.blog.core :as bpc])
  (use [hiccup.page :refer (html5)]))

(defn- split-by-month [posts]
  (let [format (java.time.format.DateTimeFormatter/ofPattern "MMM yyyy")
        createdate (fn [date] (java.time.LocalDateTime/ofInstant
                               (.toInstant date)
                               (java.time.ZoneId/systemDefault)))]
    (group-by
     (fn [post]
       (if (:date-created post)
         (.format format (createdate (:date-created post)))))
     posts)
    ))

(defn post-filter [item]
  true)

(defn render
  [{meta :meta posts :entries current :entry}]
  (html5 
   (bpc/header meta)
   [:body.post-template
    [:main.content {:role "main"}
     [:header.post-header
      [:a.blog-logo {:href (:base-url meta)}
       [:span.blog-title (:site-title meta)]]
      [:div.blog-sub-title "Archives"]]
     [:div.testdiv
      ;; TODO: Render Month: List of Post Titles w/ links.
      (str (split-by-month posts))]]
    (bpc/footer meta)
    (bpc/js-includes)]))
