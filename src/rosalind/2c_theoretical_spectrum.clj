(ns rosalind.2c-theoretical-spectrum
  "Generating Theoretical Spectrum Problem
   http://rosalind.info/problems/2c/"
  (:require [rosalind.core :refer :all :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as sets]
            [clojure.math.combinatorics :as combo]
            [clojure.math.numeric-tower :refer [abs] :as math]
            [clj-biosequence.alphabet :as cba]
            [clj-biosequence.core :as cbs]
            [plumbing.core :as pb]))

(def integer-mass-table
  (->> ros/monoisotopic-mass-table
       (pb/map-vals math/round)))

(defn fragments
  [k polypeptide]
  (let [size (count polypeptide)]
    (cond
     (= k 0)    [nil]
     (< k size) (->> polypeptide
                     (take (dec k))
                     (concat polypeptide)
                     (partition k 1))
     (= k size) [(seq polypeptide)])))

(defn all-fragments
  [polypeptide]
  (->> (range 0 (-> polypeptide count inc))
       (mapcat (fn [k] (fragments k polypeptide)))))

(defn mass [fragment]
  (transduce (map integer-mass-table) + fragment))

(defn solve [polypeptide]
  (->> polypeptide
       all-fragments
       (map mass)
       sort))

(defn execute []
  (->> "rosalind_2c.txt"
       io/resource
       io/reader
       line-seq
       first
       solve
       sort
       (str/join " ")
       (spit "resources/rosalind_2c_out.txt")))

; (execute)
