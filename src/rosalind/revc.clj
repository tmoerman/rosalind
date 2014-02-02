(ns rosalind.revc
  (:use clojure.java.io)
  (:use [clojure.string :only (trim join)])
  (:require [rosalind.core]))

;;
;; http://rosalind.info/problems/revc/
;;

(def revc-data (trim (slurp (resource "rosalind_revc.txt"))))

(defn reverse-complement [dna-string] (join (map dna-complement (reverse dna-string))))

(reverse-complement revc-data)
