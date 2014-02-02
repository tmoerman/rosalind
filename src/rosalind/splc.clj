(ns rosalind.splc
  (:use clojure.java.io)
  (:require [rosalind.core]
            [clojure.string :as str]
            [rosalind.core :as ros]
            [rosalind.fasta :as fas]))

(let [[dna & introns] (->> "rosalind_splc.txt"
                        (resource)
                        (reader)
                        (line-seq)
                        (fas/parse-fasta)
                        (map :seq)
                        (map (partial apply str)))]
  (->>
    (reduce #(str/replace %1 %2 "") dna introns) ;; develop intuition for when to use reduce
    (ros/transcribe)
    (ros/translate)
    (apply str) ; concatenate amino acids
    (prn)))
