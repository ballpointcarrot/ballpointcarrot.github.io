(ns net.ballpointcarrot.blog.views.post
  (require [net.ballpointcarrot.blog.core :as bpc])
  (use [hiccup.page :refer (html5)]))

(defn render-post
  [{meta :meta posts :entries current :entry}]
  (html5 
   (bpc/header meta)
   [:body.post-template
    [:main.content {:role "main"}
     [:header.post-header
      [:a.blog-logo {:href (:base-url meta)}
       [:span.blog-title (:site-title meta)]]]
     [:article.post
      [:span.post-meta
       [:date {:datetime (bpc/date-string (:date-created current))} (bpc/date-string (:date-created current))]
       [:div.tags (for [tag (:tags current)]
                    [:a.tags {:href (str "/path/to/" tag)} tag]
                    )]]
      [:h1.post-title (:title current)]
      [:section.post-content (:content current)]
      [:footer.post-footer 
       [:section.author
        [:h4 (:author meta)]
        [:p (:author-bio meta)]]
       [:section.share
        [:span.share-twitter
         [:h4 "Share this post"]
         [:a.icon-twitter {:href (str "https://twitter.com/share?text=&url=" (:canonical-url current))
                           :onclick="window.open(this.href, 'twitter-share', 'width=550,height=235');return false;"}]
         [:span.hidden "Twitter"]]
        [:span.share-fb
         [:a.icon-facebook {:href (str "https://www.facebook.com/sharer/sharer.php?u=" (:canonical-url current)) 
                            :onclick "window.open(this.href, 'facebook-share','width=580,height=296');return false;"}]
         [:span.hidden "Facebook"]]
        [:span.share-reddit
         [:a.icon-reddit {:href "" :onclick ""}]
         [:span.hidden "Reddit"]]]]]]
    [:footer.site-footer
     [:a {:class "subscribe icon-feed" :href (str (:base-url meta) "/atom.xml")}
      [:span.tooltip "Subscribe!"]]
     [:div.inner
      [:section.copyright "All content copyright " [:a {:href (:base-url meta)} (:site-title meta)] (str " &copy; " (.getYear (java.time.LocalDateTime/now)) " &bull; All rights reserved.")]]]
    (bpc/js-includes)]))
