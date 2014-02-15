(ns rosalind.kmer
  (require [clojure.java.io :as io]
           [rosalind.fasta :as fas]
           [rosalind.lexf :as lex]
           [clojure.string :as str]))

(defn enumerate-freqs [k freqs]
  (let [lex-enum (lex/enumerate "ACGT" k)]
    (map freqs lex-enum)))

(defn clean [x]
  (if (nil? x) 0 x))

(def k 4)

(->> "rosalind_kmer.txt"
  (io/resource)
  (io/reader)
  (line-seq)
  (fas/parse-fasta)
  (first)
  (:seq)
  (partition k 1)
  (frequencies)
  (enumerate-freqs k)
  (map clean)
  (prn))
