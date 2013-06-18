(ns rosalind.ini
  (:use clojure.java.io)
  (:use [clojure.string :only (join)]))

;;
;; http://rosalind.info/problems/ini/
;; 

(def data (slurp (resource "rosalind_ini.txt")))

(->> data
     (frequencies)
     (sort)
     (vals)
     (join " "))