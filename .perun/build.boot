(set-env!
 :source-paths #{"src"}
 :resource-paths #{"resources"}
 :dependencies '[[org.clojure/tools.nrepl "0.2.13" :exclusions [org.clojure/clojure]]
                 [hiccup "1.0.5" :exclusions [org.clojure/clojure]]
                 [perun "0.4.2-SNAPSHOT"]
                 [enlive "1.1.6" :exclusions [org.clojure/clojure]]
                 [deraen/boot-sass "0.3.1"]
                 [pandeiro/boot-http "0.8.3" :exclusions [org.clojure/clojure]]
                 [org.martinklepsch/boot-gzip "0.1.3"]])

(require '[clojure.string :as str]
         '[io.perun :as p]
         '[io.perun.core :as pc]
         '[io.perun.meta :as pm]
         '[deraen.boot-sass :refer [sass]]
         '[pandeiro.boot-http :refer [serve]])

(defn post?
  [{:keys [path]}]
  (.startsWith path "public/posts"))

(def default-excerpt-wordcount 100)

(defn- strip-tags
  [content]
  (let [matcher #"<.*?>"]
    (str/replace content matcher "")))

(defn- post-excerpt
  ([file]
   (post-excerpt file default-excerpt-wordcount))
  ([file wordcount]
   (let [content (slurp (get file :full-path))
         excerpt (fn [content-seq] (take wordcount content-seq))
         rejoin (fn [content-seq] (str/join " " content-seq))]
     (if (= "html" (get file :extension))
       (-> content
           (strip-tags)
           (str/split #"\s")
           (excerpt)
           (rejoin)
           (str "..."))))))

(deftask set-excerpts
  "Adds a text excerpt for a post into the metadata for that post."
  []
  (with-pre-wrap fileset
    (let [_meta (pm/get-meta fileset)
          with-excerpts (map (fn [post]
                               (assoc post :excerpt (post-excerpt post))) _meta)]
      (pm/set-meta
        fileset
        with-excerpts))))

(deftask build-dev
  "Build the blog."
  []
  (comp
   (p/global-metadata :filename "ballpointcarrot.net.edn")
   (p/markdown
    :md-exts {:smartypants true})
   (p/word-count)
   (set-excerpts)
   (p/render :renderer 'net.ballpointcarrot.blog.views.post/render-post
             :filterer post?
             :out-dir "public")))

(deftask build-collections
  "Build collection pages for the blog."
  []
  (comp
   (p/collection :renderer 'net.ballpointcarrot.blog.views.index/render
                 :sortby :date-created
                 :filterer post?
                 :page "index.html")
   (p/collection :renderer 'net.ballpointcarrot.blog.views.archive/render
                 :sortby :date-created
                 :filterer post?
                 :page "archive.html")
   (p/collection :renderer 'net.ballpointcarrot.blog.views.tagindex/render
                 :filterer post?
                 :page "tags.html")
   (p/tags :renderer 'net.ballpointcarrot.blog.views.tags/render
           :filterer post?
           :out-dir "public/tags")))

(deftask publish
  "Build the production version of the blog for publishing on Github."
  []
  (comp
   (build-dev)
   (p/draft)
   (p/atom-feed :page-size 100)
   (build-collections)
   (sift :include #{#"public/"}
         :move {#"public/" ""})
   (target :dir #{"build"})))

(deftask dev
  "Run a development live server"
  []
  (comp
   (watch)
   (build-dev)
   (p/draft)
   (p/atom-feed :page-size 100)
   (build-collections)
   (serve :resource-root "public")))
