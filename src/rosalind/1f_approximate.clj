(ns rosalind.1f-approximate
  "Approximate Pattern Matching Problem
   http://rosalind.info/problems/1f/"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn nr-mistakes
  [a b]
  (->> (map vector a b)
       (filter (fn [[a b]] (not= a b)))
       count))

(defn solve
  [pattern text n]
  (->> text
       (partition (count pattern) 1)
       (map-indexed vector)
       (filter (fn [[i s]] (<= (nr-mistakes s pattern) n)))
       (map first)))

(defn execute []
  (let [[pattern text nr] (-> "rosalind_1f.txt"
                              io/resource
                              io/reader
                              line-seq)]
    (->> (solve pattern text (Integer/parseInt nr))
         (str/join " ")
         (spit "resources/rosalind_1f_out.txt"))))
