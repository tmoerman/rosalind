(ns rosalind.prtm
  (require [rosalind.core :as ros]
           [clojure.java.io :as io]
           [clojure.string :as str]))

(->>
  (str/trim (slurp (io/resource "rosalind_prtm.txt")))
  (map ros/monoisotopic-mass-table)
  (reduce +)
  (prn))
