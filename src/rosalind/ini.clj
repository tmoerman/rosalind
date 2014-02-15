(ns rosalind.ini
  (require [clojure.java.io :as io]
           [clojure.string :as str]))

;;
;; http://rosalind.info/problems/ini/
;;

(->> "rosalind_ini.txt"
  (io/resource)
  (slurp)
  (frequencies)
  (sort)
  (vals)
  (str/join " "))
