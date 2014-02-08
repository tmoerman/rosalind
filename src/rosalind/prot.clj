(ns rosalind.prot
  (require [rosalind.core :as ros]
           [clojure.java.io :as io]))

;;
;; http://rosalind.info/problems/prot/
;;

(->>
  (io/resource "rosalind_prot.txt")
  (slurp)
  (ros/translate)
  (apply str)
  (prn))
