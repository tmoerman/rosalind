(ns rosalind.revc
  (:use clojure.java.io)
  (:use [clojure.string :only (trim join)]))

;;
;; http://rosalind.info/problems/revc/
;;

(def revc-data (trim (slurp (resource "rosalind_revc.txt"))))

(def dna-complement 
  {\A \T
   \C \G
   \G \C
   \T \A})

(defn reverse-complement [dna-string] (join (map dna-complement (reverse dna-string))))

(reverse-complement revc-data)