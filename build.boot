(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/tools.nrepl "0.2.13" :exclusions [org.clojure/clojure]]
                 [hiccup "1.0.5" :exclusions [org.clojure/clojure]]
                 [perun "0.3.0"]
                 [enlive "1.1.6" :exclusions [org.clojure/clojure]]
                 [deraen/boot-sass "0.3.1"]
                 [pandeiro/boot-http "0.8.3"]
                 [org.martinklepsch/boot-gzip "0.1.3"]])

(require '[io.perun :refer :all]
         '[deraen.boot-sass :refer [sass]]
         '[pandeiro.boot-http :refer [serve]])

(deftask build-dev
  "Build the blog."
  []
  (comp
   (global-metadata :filename "ballpointcarrot.net.edn")
   (markdown)
   (word-count)
   (print-meta)
   (render :renderer 'net.ballpointcarrot.blog.index/render)
   ))

(deftask publish
  "Build the production version of the blog for publishing on Github."
  []
  (comp
   (build-dev)
   (sift :include #{#"public/"}
         :move {#"public/" ""})
   (target :dir #{"build"})))

(deftask dev
  "Run a development live server"
  []
  (comp
   (watch)
   (build-dev)
   (draft)
   (serve :resource-root "public")))
