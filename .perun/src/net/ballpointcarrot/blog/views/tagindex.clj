(ns net.ballpointcarrot.blog.views.tagindex
 (require [net.ballpointcarrot.blog.core :as bpc])
  (use [hiccup.page :refer (html5)]))

(defn- tags-from-posts
  [posts]
  (->> posts
       (map #(get % :tags))
       (flatten)
       (distinct)))

(defn render
  [{_meta :meta posts :entries current :entry}]
  (html5
    (bpc/header _meta)
    [:body.post-template
     [:main.content {:role "main"}
      [:header.post-header
       [:a.blog-logo {:href (:base-url _meta)}
        [:span.blog-title (:site-title _meta)]]
       [:div.blog-sub-title "All Tags"]]
      [:div.archive
       [:ul
        (for [tag (tags-from-posts posts)]
          [:li [:a {:href (str "/tags/" tag ".html")} tag]])]]]]
    (bpc/footer _meta)
    (bpc/js-includes)))
