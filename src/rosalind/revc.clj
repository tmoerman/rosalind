(ns rosalind.revc
  (require [clojure.java.io :as io]
           [rosalind.core :as ros]
           [clojure.string :as str]))

;;
;; http://rosalind.info/problems/revc/
;;

(->> "rosalind_revc.txt"
  (io/resource)
  (slurp)
  (str/trim)
  (ros/reverse-complement)
  (str/join)
  (prn))
