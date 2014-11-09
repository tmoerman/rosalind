(ns rosalind.lgis
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [taoensso.timbre :as log]
            [taoensso.timbre.profiling :as pro]
            [clojure.math.combinatorics :as combo]))

(def n 5)
(def p [5 1 4 2 3])

;.
;; Parse input
;'

(defn str->int [s] (Integer/parseInt s))

(defn parse-input []
  (let [lines (-> "rosalind_lgis.txt"
                  io/resource
                  io/reader
                  line-seq)
        n (->> lines first str->int)
        p (->> (-> lines second (str/split #" "))
               (map str->int))]
    [n p]))

;.
;; Brute force
;'

(defn inverse-range [n] (->> 5 range (map inc) reverse))

(defn combos [n p]
  (for [i      (inverse-range n)
        combos (combo/combinations p i)]
    combos))

(defn monotonous? [f s]
  (log/info "checking " s)
  (loop [[x & xs] s
         last     nil]
    (if (nil? x)
      true
      (if (or (nil? last)
              (f x last))
        (recur xs x)
        false))))

(def increasing? (partial monotonous? >))
(def decreasing? (partial monotonous? <))

(defn xf*1 [pred] (comp
                   (filter pred)
                   (take 1)))

(defn solve-brute-force [n p pred]
  (->> (combos n p)
       (transduce (xf*1 pred) conj [])
       (first)
       (str/join " ")))

(solve-brute-force n p increasing?)

;.
;; Pivots algorithm
;'

(def p2 [3 1 2 4 8 5 6 7])

(defn pivots
  [p]
  (loop [[x & xs] p
         last     -1
         acc      [x]]
    (if (nil? x)
      acc
      (recur xs
             x
             (if (> x last)
               acc
               (conj acc x))))))

(defn tail-from [x p] (drop-while (complement #(= % x)) p))

(defn solve-branching [p]
  (for [pivot (pivots p)
        :let [right-sweep (->> (tail-from pivot p)
                               (filter #(> % pivot)))]
        subsolution (if (empty? right-sweep)
                      [nil]
                      (solve-branching right-sweep))]
    (cons pivot subsolution)))

(->> p2
     (solve-branching)
     (sort-by count)
     (last))


