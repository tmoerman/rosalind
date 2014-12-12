(ns rosalind.2g-convolution
  "Spectral Convolution Problem
   http://rosalind.info/problems/2g/"
  (:require [rosalind.core :refer :all :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]
            [clojure.math.numeric-tower :refer [abs] :as math]
            [rosalind.2e-cyclopeptide :refer [parse-spectrum]]))

(defn solve [spectrum]
  (->> (combo/combinations spectrum 2)
       (map (fn [[a b ]] (math/abs (- a b))))
       (filter (complement zero?)) ;; -> not in the problem description !!!
       (frequencies)
       (sort-by val >)
       (mapcat (fn [[a b]] (repeat b a)))
       (str/join " ")))

(defn split-ints [line] (->> (str/split line #" ")
                             (map str->int)))

(defn read-spectrum []
  (->> "rosalind_2g.txt"
       io/resource
       io/reader
       line-seq
       first
       split-ints))

(defn execute []
  (->> (read-spectrum)
       solve
       (spit "resources/rosalind_2g_out.txt")))

; (time (execute))
