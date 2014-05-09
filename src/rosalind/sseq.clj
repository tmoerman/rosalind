(ns rosalind.sseq
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [rosalind.fasta :as fas]))

;;
;; http://rosalind.info/problems/sseq/
;;

;; Too slow, computes all possibilities, unnecessary for the solution.

(comment
  (defn indices [string ch]
    (keep-indexed (fn [i c] (if (= ch c) i)) string))

  (defn head>? [n [head _]] (> head n))

  (defn collect [i-seqs]
    (let [[head & tail] i-seqs]
      (if (nil? tail)
        (->> head
             (map vector))
        (let [ys (collect tail)]
          (->> head
               (mapcat (fn [x]
                         (->> ys
                              (filter (partial head>? x))
                              (map (fn [y] (cons x y)))))))))))

  (let [[dna sub] (->> "rosalind_sseq.txt"
                       (io/resource)
                       (io/reader)
                       (line-seq)
                       (fas/parse-fasta)
                       (map :seq))]
    (->> sub
         (map (fn [c] (indices dna c)))
         (collect)
         (map (partial map inc))
         (rand-nth)
         )))

;; Solution yielding one result

(defn solve [dna sub]
  (loop
    [curr-dna dna
     [x & xs] sub
     carrier  0
     acc      []]
    (if (nil? x)
      acc
      (let [i (.indexOf curr-dna (str x))
            rest-dna (subs curr-dna (inc i))
            next-carrier (+ carrier (inc i))]
          (recur rest-dna
                 xs
                 next-carrier
                 (conj acc next-carrier))))))

(let [[dna sub] (->> "rosalind_sseq.txt"
                       (io/resource)
                       (io/reader)
                       (line-seq)
                       (fas/parse-fasta)
                       (map :seq))]
  (->> (solve dna sub)
       (str/join " ")))
