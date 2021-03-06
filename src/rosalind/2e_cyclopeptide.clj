(ns rosalind.2e-cyclopeptide
  "Cyclopeptide Sequencing Problem
   http://rosalind.info/problems/2e/"
  (:use clojure.tools.trace)
  (:require [rosalind.core :refer :all :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [rosalind.2c-theoretical-spectrum :refer [integer-mass-table all-fragments mass]]))

(def standard-amino-acids (keys integer-mass-table))

(defn complies-with
  [spectrum polypeptide]
  (->> polypeptide
       mass
       (contains? spectrum)))

(def parent-mass (memoize (fn [spectrum] (reduce max spectrum))))

(defn cyclospectrum
  [polypeptide]
  (->> polypeptide
       all-fragments
       (into #{} (map mass))))

(defn matches
  [spectrum polypeptide]
  (and (= (parent-mass spectrum) (mass polypeptide))
       (= spectrum (cyclospectrum polypeptide))))

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

(defn parse-spectrum
  [line]
  (->> (str/split line #" ")
       (map str->int)
       (into #{})))

(defn read-spectrum []
  (-> "rosalind_2e.txt"
      io/resource
      io/reader
      line-seq
      first
      parse-spectrum))

(defn format-masses
  [polypeptide]
  (->> polypeptide
       (map integer-mass-table)
       (str/join "-")))

(defn stringify
  [cyclopeptides]
  (->> cyclopeptides
       (map format-masses)
       (into #{})
       (str/join " ")))

(defn execute []
  (->> (read-spectrum)
       solve
       stringify
       (spit "resources/rosalind_2e_out.txt")))

; (time (execute))

;; examples

(def S #{0 113 128 186 241 299 314 427})

(complies-with S [\I \K \N])

(complies-with S [\I])
(complies-with S [\I \K])
(complies-with S [\I \K \W])

(matches S [\I])
(matches S [\I \K])
(matches S [\I \K \W])
(matches S [\W \K \I])



