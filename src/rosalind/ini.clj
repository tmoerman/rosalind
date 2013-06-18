(ns rosalind.ini
  (:use clojure.java.io)
  (:use [clojure.string :only (join)]))

(def data (slurp (resource "rosalind_ini.txt")))

(->> data
     (frequencies)
     (sort)
     (vals)
     (join " "))