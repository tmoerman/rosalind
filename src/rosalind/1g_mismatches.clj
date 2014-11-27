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
       (pb/count-when (fn [[a b]] (not= a b)))))

(nr-mistakes "AAA" "ABC")

(def bases [\A \C \G \T])

(defn assoc-all
  [v ks vs]
  (->> (interleave ks vs)
       (apply assoc v)))

(defn assoc-all-2
  [v ks vs]
  (loop [result v
         [i & is] ks
         [b & bs] vs]
    (if (nil? i)
      result
      (recur (assoc result i b) is ks))))

(time
 (-> (apply vector "AAAAAAAA")
     (assoc-all [0 1 2] [\B \B \B])))

(time
 (-> (apply vector "AAAAAAAA")
     (assoc-all-2 [0 1 2] [\B \B \B])))


(defn mismatch-range
  [kmer k d]
  (let [index-tuples (-> (range 0 k)
                         (combo/combinations d))
        replacement-tuples (-> (range 0 4)
                               (combo/selections d))
        mismatches (for [i-tuple index-tuples
                         r-tuple replacement-tuples :let [b-tuple (map bases r-tuple)]]
                     (assoc-all kmer i-tuple b-tuple))]
    mismatches))

(defn count-matches
  [kmer k d kmers]
  (pb/count-when (fn [candidate] (<= (nr-mistakes candidate kmer) d)) kmers))

(defn solve
  [k d text]
  (let [kmers (->> text
                   (partition k 1)
                   (map (partial into [])))

        kmers-unique (into #{} kmers)

        search-space (time (->> kmers-unique
                                (mapcat #(mismatch-range % k d))
                                (into #{})))

        score-graph (time (->> search-space
                               (map (fn [kmer] [kmer (count-matches kmer k d kmers)])))) ;; redundancy ???

        max-score (time (transduce (map second) max 0 score-graph))

        _ (println "score-graph size " (count score-graph))

        result (time (->> score-graph
                          (filter (fn [tuple] (= (second tuple) max-score)))
                          (map first)))]
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
         (str/join " ")
         (spit "resources/rosalind_1g_out.txt"))))
