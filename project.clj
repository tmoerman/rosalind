(defproject rosalind "0.1.0-SNAPSHOT"
  :description "Project Rosalind problems in Clojure"
  :url "http://github.com/tmoerman/rosalind"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0-alpha4"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
                 [org.clojure/tools.trace "0.7.8"]
                 [http-kit "2.1.16"]
                 [incanter "1.5.5"]
                 [aysylu/loom "0.5.0"]
                 [clj-biosequence "0.1.4-SNAPSHOT"]
                 [prismatic/plumbing "0.3.5"]
                 [com.taoensso/timbre "3.3.1"]
                 [org.clojure/data.zip "0.1.1"]
                 [org.clojure/math.combinatorics "0.0.7"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [org.clojure/core.match "0.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}})
