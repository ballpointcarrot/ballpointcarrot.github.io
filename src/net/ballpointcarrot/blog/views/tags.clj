(ns net.ballpointcarrot.blog.views.tags
  (require [net.ballpointcarrot.blog.core :as bpc])
  (use [hiccup.page :refer (html5)]))

(defn render
  [{meta :meta posts :entries current :entry}]
  (html5
    (bpc/header meta)
    [:body.post-template
     [:main.content {:role "main"}
      [:header.post-header
       [:a.blog-logo {:href (:base-url meta)}
        [:span.blog-title (:site-title meta)]]
       [:div.blog-sub-title (str "Posts tagged with: " (:tag current))]]
      [:div.archive
       (for [post posts]
         [:li (bpc/link-to-post (:base-url meta) post)])]]]
    (bpc/footer meta)
    (bpc/js-includes)))
