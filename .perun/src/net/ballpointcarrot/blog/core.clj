(ns net.ballpointcarrot.blog.core
  (require [clojure.string :as str])
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
                "/assets/css/prism.css")])

(defn date-string [date]
  (if (nil? date)
    nil
    (let [format (java.time.format.DateTimeFormatter/ofPattern "EE, d MMM yyyy")
          localdate (java.time.LocalDateTime/ofInstant (.toInstant date) (java.time.ZoneId/systemDefault))]
      (.format format localdate))))

(defn post-header [post]
  [:span.post-meta
   [:time.dt-published {:datetime (date-string (:date-created post))} [:a {:href (:canonical-url post)}(date-string (:date-created post)) ]]
   [:div.tags (for [tag (:tags post)]
                [:a.p-category{:href (str "/tags/" tag ".html")
                               :style "padding: 0 0.5rem 0 0;"} tag])]
   (if (:draft post)
     [:div.unpublished "DRAFT"])])

(defn link-to-post [base-url post]
  [:a {:href (:canonical-url post)}
   (:title post)])

(defn js-includes
  "Include common Javascript files"
  []
  (include-js "/assets/js/index.js"
              "/assets/js/prism.js"))

(defn footer
  "Provides final common page details."
  [metadata]
  [:footer.site-footer
   [:a {:class "subscribe icon-feed" :href (str (:base-url metadata) "atom.xml")}
    [:span.tooltip "Subscribe!"]]
   [:div.inner.h-card
    [:div "I'm " [:span.p-name "Christopher Kruse"] "."]
    [:ul.web-identity
     [:li [:a.u-url {:rel "me" :href "https://twitter.com/ballpointcarrot" :target "_blank"} "Twitter"]]
     [:li [:a.u-url {:rel "me" :href "https://social.ballpointcarrot.net/@ballpointcarrot" :target "_blank"} "Mastodon"]]
     [:li [:a.u-url {:rel "me" :href "https://github.com/ballpointcarrot" :target "_blank"} "Github"]]
     [:li [:a {:class "u-email" :href "mailto:ckruse@ballpointcarrot.net" :target "_blank"} "email"]]]]
   [:div.inner
    [:section.copyright "All content copyright " [:a {:href (:base-url metadata)} (:site-title metadata)] (str " &copy; " (.getYear (java.time.LocalDateTime/now)) " &bull; All rights reserved.")]]])
