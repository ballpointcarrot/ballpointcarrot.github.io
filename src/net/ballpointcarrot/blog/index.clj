(ns net.ballpointcarrot.blog.views.index
  (require [net.ballpointcarrot.blog.core :as bpc])
  (use [hiccup.page :only (html5)]))

(defn render
  [{global-metadata :meta entries :entries current :entry}]
  (html5
   (bpc/header global-metadata)
   [:body
    [:div#debug
     [:h3 "Metadata"]
     [:p (str global-metadata)]
     [:h3 "all pages"]
     [:p (str entries)]]
    (bpc/js-includes)])) 
