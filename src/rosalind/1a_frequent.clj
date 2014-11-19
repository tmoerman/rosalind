(ns rosalind.1a_frequent
  "Frequent words problem
   http://rosalind.info/problems/1a/"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn frequent-words
  [k dna]
  (let [freqs    (->> dna (partition k 1) (map str/join) frequencies)
        max-freq (->> freqs vals (reduce max))]
    (->> freqs
         (filter (fn [[s v]] (= v max-freq)))
         (map key))))

(frequent-words 5 "ACAACTATGCATCACTATCGGGAACTATCCT")

(defn execute []
  (let [[dna k] (->> "rosalind_1a.txt"
                     io/resource
                     io/reader
                     line-seq)]
    (frequent-words (Integer/parseInt k) dna)))
