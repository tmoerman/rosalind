(ns rosalind.splc
  (:use clojure.java.io)
  (:require [rosalind.core]
            [clojure.string :as str]
            [rosalind.core :as ros]
            [rosalind.fasta :as fas]))

(defn remove-introns
  [dna introns]
  (loop [curr-dna       dna
         [intron & rest :as all] introns]
    (if (empty? all)
      curr-dna
      (let [index         (.indexOf curr-dna intron)
            intron-exists (not (= index -1))]
          (recur
            (if intron-exists
              (str/replace curr-dna intron "")
              curr-dna)
            rest)))))

(let [[dna & introns] (->> "rosalind_splc.txt"
                        (resource)
                        (reader)
                        (line-seq)
                        (fas/parse-fasta)
                        (map :seq)
                        (map (partial apply str)))]
  (->>
    (remove-introns dna introns)
    (ros/transcribe)
    (ros/translate)
    (apply str) ; concatenate amino acids
    (prn)))
