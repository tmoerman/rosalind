(ns rosalind.1g-mismatches-2
  "Frequent Words with Mismatches Problem
   http://rosalind.info/problems/1g/"
  (:require [rosalind.core :refer [str->int] :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as sets]
            [clojure.math.combinatorics :as combo]
            [plumbing.core :as pb]))

(def others {\A [\C \G \T]
             \C [\A \G \T]
             \G [\A \C \T]
             \T [\A \C \G]})

(defn make-variant
  ([kmer i r]
   (assoc kmer i (-> kmer (nth i) others (nth r))))
  ([kmer i-r-seq]
   (reduce (fn [kmer' [i r]] (make-variant kmer' i r)) kmer i-r-seq)))

(defn i-r-seqs
  [k d]
  (for [d' (range 1 (inc d))
        is (-> (range 0 k)
               (combo/combinations d'))
        rs (-> (range 0 3)
               (combo/selections d'))]
    (map vector is rs)))

(defn make-variants
  [k d kmer]
  (->> (i-r-seqs k d)
       (map (partial make-variant kmer))))

(defn +' [x y] (+ (or x 0) y))

(defn solve
  [k d text]
  (let [kmers (->> text
                   (partition k 1)
                   (map (partial apply vector)))

        freqs (frequencies kmers)

        updates (->> kmers
                     (into #{})
                     (mapcat (fn [kmer]
                               (let [freq (freqs kmer)]
                                 (->> (make-variants k d kmer)
                                      (map (fn [v] [v freq])))))))

        variant-freqs (->> updates
                           (reduce (fn [m [v freq]] (update m v +' freq)) {}))

        max-freq (->> variant-freqs
                      vals
                      (reduce max))

        result (->> variant-freqs
                    (filter (fn [tuple] (= (second tuple) max-freq)))
                    (map first)
                    (map str/join))]
    result))

(defn execute []
  (let [[text params] (->> "rosalind_1g_2.txt"
                           io/resource
                           io/reader
                           line-seq)
        [k d] (->> (str/split params #" ")
                   (map str->int))]
    (->> (solve k d text)
         (str/join " ")
         (spit "resources/rosalind_1g_2_out.txt"))))

;(time (execute))
