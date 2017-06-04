(ns net.ballpointcarrot.blog.views.index
  (require [net.ballpointcarrot.blog.core :as bpc])
  (use [hiccup.page :only (html5)]))

(defn render
  [{global-metadata :meta entries :entries current :entry}]
  (html5
   (bpc/header global-metadata)
   [:body.home-template
    [:header.site-head {:style (str "background-image: linear-gradient(rgba(133, 85, 61, 0.7), rgba(133, 85, 61, 0.7)), url(" (:cover-image global-metadata) ");")}
     [:div.vertical
      [:div {:class "site-head-content inner"}
       [:h1.blog-title (:site-title global-metadata)]
       [:h2.blog-description (:site-sub-title global-metadata)]]]]
    [:main.content {:role "main"}
     (for [post (take (:home-post-count global-metadata) entries)] 
       [:article.post
        [:header.post-header
         (bpc/post-header post)
         [:h2.post-title
          (bpc/link-to-post (:base-url global-metadata) post)]]
        [:section.post-excerpt
         [:p (bpc/post-excerpt post)]]])
     [:div.pagination
      [:a {:href "/archive"} "Archives"]
      [:a {:href "/tags"} "Tags"]]]
    (bpc/footer global-metadata)
    (bpc/js-includes)])) 
