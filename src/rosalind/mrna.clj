(ns rosalind.mrna
  (require [rosalind.core :as ros]
           [clojure.java.io :as io]
           [clojure.string :as str]
           [clojure.set :as set]))

(def freqs (frequencies (vals ros/rna-codon-table)))

(def data-file "rosalind_mrna.txt")

(->>
  (str/trim (slurp (io/resource data-file)))
  ;;"MA"
  (seq)
  (map str)
  (map symbol)
  (#(conj %1 'Stop))
  (map freqs)
  (map bigint)
  (reduce *)
  (#(mod %1 1000000))
  (prn))
