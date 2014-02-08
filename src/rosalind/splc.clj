(ns rosalind.splc
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [rosalind.core :as ros]
            [rosalind.fasta :as fas]))

(let [[dna & introns] (->> "rosalind_splc.txt"
                        (io/resource)
                        (io/reader)
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
