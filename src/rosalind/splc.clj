(ns rosalind.splc
  (:use clojure.java.io)
  (:use [clojure.string :as str])
  (:use rosalind.fasta))

;;
;; http://rosalind.info/problems/splc/
;;

(def input
  [{:id  "dna-string"
    :seq "ATGGTCTACATAGCTGACAAACAGCACGTAGCAATCGGTCGAATCTCGAGAGGCATATGGTCACATGATCGGTCGAGCGTGTTTCAAAGTTTGCGCCTAG"}
   {:id  "intron 1"
    :seq "ATCGGTCGAA"}
   {:id  "intron 2"
    :seq "ATCGGTCGAGCGTGT"}])

(def dna-string (:seq (first input)))

(def introns (map :seq (rest input)))

(prn dna-string)
(prn introns)

(str/replace "otis redding" "red" "")

(defn split-input-to-splice [fasta-lines]
  (let [dna-string (first fasta-lines)]))