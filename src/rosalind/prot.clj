(ns rosalind.prot
  (:use rosalind.core)
  (:use clojure.java.io))

;;
;; http://rosalind.info/problems/prot/
;;

(prn (apply str (translate (slurp (resource "rosalind_prot.txt")))))
