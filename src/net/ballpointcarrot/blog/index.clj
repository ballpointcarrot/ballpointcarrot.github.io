(ns net.ballpointcarrot.blog.index
  (use [hiccup.page :only (html5)]))

(defn render
  [{global-metadata :meta entries :entries current :entry}]
  (html5
   [:body
    [:div#debug
     [:h3 "Metadata"]
     [:p (str global-metadata)]
     [:h3 "all pages"]
     [:p (str entries)]]])) 
