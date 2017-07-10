(ns net.ballpointcarrot.blog.views.archive
  (require [net.ballpointcarrot.blog.core :as bpc])
  (use [hiccup.page :refer (html5)]))

(defn- split-by-time-period [format-str posts]
  (let [format (java.time.format.DateTimeFormatter/ofPattern format-str)
        createdate (fn [date] (java.time.LocalDateTime/ofInstant
                               (.toInstant date)
                               (java.time.ZoneId/systemDefault)))]
    (group-by
     (fn [post]
       (if (:date-created post)
         (.format format (createdate (:date-created post)))))
     posts)
    ))

(defn- split-by-month [posts]
  (split-by-time-period "MMM yyyy" posts))

(defn- split-by-year [posts]
  (split-by-time-period "yyyy" posts))

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
     [:div.archive
      (let [posts-interval (split-by-year posts)
            months (keys posts-interval)]
        (str months)
        (for [month months]
          [:div.month
           [:h4 month]
           [:ul
            (for [post (get posts-interval month)]
              [:li (bpc/link-to-post (:base-url meta) post)])
            ]]))
      ]]
    (bpc/footer meta)
    (bpc/js-includes)]))
