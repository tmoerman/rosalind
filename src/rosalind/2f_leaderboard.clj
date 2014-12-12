(ns rosalind.2f-leaderboard
  "Leaderboard Cyclopeptide Sequencing Problem
   http://rosalind.info/problems/2f/"
  (:use clojure.tools.trace)
  (:require [rosalind.core :refer :all :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [rosalind.2c-theoretical-spectrum :refer [integer-mass-table all-fragments mass]]
            [rosalind.2e-cyclopeptide :refer [cyclospectrum
                                              standard-amino-acids
                                              parent-mass
                                              format-masses
                                              parse-spectrum]]))

(defn score
  [spectrum polypeptide]
  (->> polypeptide
       (cyclospectrum)
       (map spectrum)
       (filter some?)
       (count)))

(def score-memo (memoize score))

(defn solve-tailrec
  "In contrast to the solution of problem 2e, this is solution implements
   with a tail-recursive approach. This approach is necessary to impose a
   leaderboard over the different 'branches'."
  [n spectrum]
  (loop [leaderboard [[]]
         leader-peptide [[] 0]]
    (if (empty? leaderboard)
      leader-peptide
      (let [expanded (->> leaderboard
                          (mapcat (fn [polypeptide]
                                    (->> standard-amino-acids
                                         (map #(conj polypeptide %))
                                         (filter #(<= (mass %) (parent-mass spectrum)))))))

            filtered-with-scores (->> expanded
                                      (filter #(<= (mass %) (parent-mass spectrum)))
                                      (map #(vector % (score-memo spectrum %)))
                                      (sort-by second >))

            new-leaderboard (->> filtered-with-scores
                                 (map first)
                                 (take n))

            new-leader-peptide (->> leader-peptide
                                    (conj filtered-with-scores)
                                    (reduce (partial max-key second)))]
        (recur new-leaderboard new-leader-peptide)))))

(defn execute []
  (let [[a b] (->> "rosalind_2f.txt"
                   io/resource
                   io/reader
                   line-seq)
        n (str->int a)
        spectrum (parse-spectrum b)]
    (->> (solve-tailrec n spectrum)
         first
         format-masses
         (spit "resources/rosalind_2f_out.txt"))))

; (time (execute))

