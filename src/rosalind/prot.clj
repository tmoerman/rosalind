(ns rosalind.prot
  (require [rosalind.core :as ros]
           [clojure.java.io :as io]))

;;
;; http://rosalind.info/problems/prot/
;;

(->> "rosalind_prot.txt"
  (io/resource)
  (slurp)
  (ros/translate)
  (apply str)
  (prn))
