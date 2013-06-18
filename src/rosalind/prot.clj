(ns rosalind.prot
  (:use clojure.java.io)
  (:use [clojure.string :only (join)]))

;;
;; http://rosalind.info/problems/prot/
;;

(def proteins 
  (apply hash-map '(
    UUU F      CUU L      AUU I      GUU V
    UUC F      CUC L      AUC I      GUC V
    UUA L      CUA L      AUA I      GUA V
    UUG L      CUG L      AUG M      GUG V
    UCU S      CCU P      ACU T      GCU A
    UCC S      CCC P      ACC T      GCC A
    UCA S      CCA P      ACA T      GCA A
    UCG S      CCG P      ACG T      GCG A
    UAU Y      CAU H      AAU N      GAU D
    UAC Y      CAC H      AAC N      GAC D
    UAA Stop   CAA Q      AAA K      GAA E
    UAG Stop   CAG Q      AAG K      GAG E
    UGU C      CGU R      AGU S      GGU G
    UGC C      CGC R      AGC S      GGC G
    UGA Stop   CGA R      AGA R      GGA G
    UGG W      CGG R      AGG R      GGG G )))

(defn to-codons [rna]
  (map (partial apply str) (partition 3 rna)))

(defn codon-to-protein [codon] 
  (proteins (symbol codon)))

(defn translate [rna]
  (->> rna
       (to-codons)
       (map codon-to-protein)
       (take-while (partial not= 'Stop))
       (apply str)))

(prn (translate (slurp (resource "rosalind_prot.txt"))))