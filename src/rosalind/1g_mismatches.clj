(ns rosalind.1g-mismatches
  "Frequent Words with Mismatches Problem
   http://rosalind.info/problems/1g/"
  (:require [rosalind.core :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as sets]
            [clojure.math.combinatorics :as combo]
            [plumbing.core :as pb]))

(defn str->int [s] (Integer/parseInt s))

(defn nr-mistakes
  [a b]
  (->> (map vector a b)
       (filter (fn [[a b]] (not= a b)))
       count))

(def bases [\A \C \G \T])

(defn assoc-all
  [v ks vs]
  (->> (interleave ks vs)
       (apply assoc v)))

(defn mismatch-range
  [kmer d]
  (let [index-tuples (-> (range 0 (count kmer))
                         (combo/combinations d))
        replacement-tuples (-> (range 0 4)
                               (combo/selections d))
        mismatches (for [i-tuple index-tuples
                         r-tuple replacement-tuples :let [b-tuple (map bases r-tuple)]]
                     (assoc-all kmer i-tuple b-tuple))]
    (into #{} mismatches)))

(defn count-matches
  [kmer k d kmers]
  (pb/count-when (fn [candidate] (<= (nr-mistakes candidate kmer) d)) kmers))

(defn solve
  [k d text]
  (let [kmers (->> text
                   (partition k 1)
                   (map (partial into [])))

        kmers-unique (into #{} kmers)

        search-space (->> kmers-unique
                          (mapcat #(mismatch-range % d))
                          (into #{}))

        score-graph (->> search-space
                         (map (fn [kmer] [kmer (count-matches kmer k d kmers)])))

        max-score (transduce (map second) max 0 score-graph)

        result (->> score-graph
                    (filter (fn [[_ score]] (= score max-score)))
                    (map first))]
    result))

(defn execute []
  (let [[text params] (->> "rosalind_1g.txt"
                           io/resource
                           io/reader
                           line-seq)
        [k d] (->> (str/split params #" ")
                   (map str->int))]
    (->> (solve k d text)
         (map str/join)
         (str/join " "))))

;(time (execute))

;; test

(mismatch-range (apply vector "AAAA") 2)

(->> (solve 4 1 "AAATAAATCCCG")
     (map str/join)
     (str/join " "))
