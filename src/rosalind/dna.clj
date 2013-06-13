(ns rosalind.dna
  (:use clojure.java.io)
  (:use [clojure.string :only (trim)]))

;;
;; http://rosalind.info/problems/dna/
;;

(def dna-data (trim (slurp (resource "rosalind_dna.txt"))))

(defn freqs [collection] (vals (sort (frequencies collection))))

(freqs dna-data)