(ns rosalind.2e-cyclopeptide
  "Generating Theoretical Spectrum Problem
   http://rosalind.info/problems/2c/"
  (:use clojure.tools.trace)
  (:require [rosalind.core :refer :all :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [rosalind.2c-theoretical-spectrum :refer [integer-mass-table all-fragments mass]]))

(def standard-amino-acids (keys integer-mass-table))

(defn complies-with
  [spectrum cyclopeptide]
  (->> cyclopeptide
       mass
       (contains? spectrum)))

(defn parent-mass [spectrum] (reduce max spectrum))

(defn cyclospectrum
  [cyclopeptide]
  (->> cyclopeptide
       all-fragments
       (into #{} (map mass))))

(defn matches [spectrum cyclopeptide]
  (and (= (parent-mass spectrum) (mass cyclopeptide))
       (= spectrum (cyclospectrum cyclopeptide))))

(defn solve
  [spectrum]
  (letfn [(step [acc]
                (if (matches spectrum acc)
                  [acc]
                  (->> standard-amino-acids
                       (map #(conj acc %))
                       (filter #(complies-with spectrum %))
                       (mapcat step))))]
      (step [])))

(defn str->int [s] (Integer/parseInt s))

(defn read-spectrum []
  (->> (-> "rosalind_2e.txt"
           io/resource
           io/reader
           line-seq
           first
           (str/split #" "))
       (map str->int)
       (into #{})))

(defn stringify
  [cyclopeptides]
  (->> cyclopeptides
       (map (fn [cyclopeptide]
              (->> cyclopeptide
                   (map integer-mass-table)
                   (str/join "-"))))
       (into #{})
       (str/join " ")))

(defn execute []
  (->> (read-spectrum)
       (solve)
       (stringify)
       (spit "resources/rosalind_2e_out.txt")))

(time (execute))

;; examples

(def spectrum #{0 113 128 186 241 299 314 427})

(complies-with spectrum [\I \K \N])

(complies-with spectrum [\I])
(complies-with spectrum [\I \K])
(complies-with spectrum [\I \K \W])

(matches spectrum [\I])
(matches spectrum [\I \K])
(matches spectrum [\I \K \W])
(matches spectrum [\W \K \I])



