(defproject rosalind "0.1.0-SNAPSHOT"
  :description "Project Rosalind problems in Clojure"
  :url "http://github.com/tmoerman/rosalind"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.1.16"]
                 [org.clojure/data.zip "0.1.1"]
                 [org.clojure/math.combinatorics "0.0.7"]
                 [org.clojure/core.match "0.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}})
