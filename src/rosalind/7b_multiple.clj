(ns rosalind.7b-multiple
  "Multiple Pattern Matching Problem
   http://rosalind.info/problems/7b/"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn solve [text patterns]
  (->> patterns
       (reduce (fn [m pattern]
                 (update m (count pattern) #(conj (or % #{}) pattern))) {})
       (mapcat (fn [[k patterns]]
                 (->> text
                      (partition k 1)
                      (map-indexed (fn [i kmer] [i (str/join kmer)]))
                      (filter (fn [[i kmer]] (contains? patterns kmer)))
                      (map first))))))

(defn execute []
  (let [[text & patterns] (->> "rosalind_7b.txt"
                               io/resource
                               io/reader
                               line-seq)]
    (->> (solve text patterns)
         (str/join " ")
         (spit "resources/rosalind_7b_out.txt"))))

;(execute)
