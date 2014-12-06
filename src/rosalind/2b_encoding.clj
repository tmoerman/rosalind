(ns rosalind.2b-encoding
  "Peptide encoding problem.
   http://rosalind.info/problems/2b/"
  (:require [rosalind.core :refer :all :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(ros/transcribe "TCC")

(defn encodes? [peptide dna]
  (or
   (= peptide (-> dna transcribe translate-raw str/join))
   (= peptide (-> dna reverse-complement transcribe translate-raw str/join))))

(defn solve
  [genome peptide]
  (let [k (* 3 (count peptide))]
    (->> genome
         (partition k 1)
         (map str/join)
         (filter (fn [kmer] (encodes? peptide kmer)))
         (map str/join))))

(defn execute []
  (let [[genome peptide] (->> "rosalind_2b.txt"
                              io/resource
                              io/reader
                              line-seq)]
    (->> (solve genome peptide)
         (str/join " ")
         (spit "resources/rosalind_2b_out.txt"))))

; (time (execute))
