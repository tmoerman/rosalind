(ns rosalind.dna
  (require [clojure.java.io :as io]
           [clojure.string :as str]))

;;
;; http://rosalind.info/problems/dna/
;;

(->> "rosalind_dna.txt"
  (io/resource)
  (slurp)
  (str/trim)
  (frequencies)
  (sort)
  (vals))
