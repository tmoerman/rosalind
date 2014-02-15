(ns rosalind.subs
  (require [clojure.java.io :as io]))

;;
;; http://rosalind.info/problems/subs/
;;

;; Kind of lucky solution, but not elegant from algorithmic point of view.
;; By coincidence, the desired output (base 1) is used by the algorithm,
;; this is not a repeatable design finesse, but a fortunate property of this
;; particular implementation.
;; In short, there is too much going on at the same time = complected.

(defn find-motifs
  "find indices of occurrences of t in s"
  [s t]
  (loop [curr   s
         result nil]
    (let [index (.indexOf curr t)
          last (if (empty? result) 0 (first result))]
      (if (neg? index)
        result
        (recur
          (.substring curr (inc index))
          (cons (+ index last 1) result))))))

;; Although longer, this implementation is better.
;; The different ideas of the algorithm are decomplected.

(defn find-local-motif-indices
  [s t]
  (loop [curr   s
         result nil]
    (let [index (.indexOf curr t)]
      (if (neg? index)
        result
        (recur
          (.substring curr (inc index))
          (cons index result))))))


(defn pile [indices]
  (loop [curr   indices
         result nil]
    (if (empty? curr)
      result
      (recur (rest curr)
             (cons
               (+ (first curr)
                  (if (empty? result) 0 (inc (first result)))) result)))))

(defn find-motifs-2 [s t]
  (reverse (map inc (pile (reverse (find-local-motif-indices s t))))))

;;
;; Different approach with partitioning the string into a lazy seq of parts with length
;; equal to the motif we want to find. Arguably more elegant because than previous solutions
;; because it does not depend on the String#indexOf method.
;;

(defn find-motifs-3 [s t]
  (reverse
    (map inc
      (let [parts (partition (count t) 1 s)]
        (loop [curr   parts
               index  0
               result nil]
          (if (empty? curr)
            result
            (recur
              (rest curr)
              (inc index)
              (if (= (first curr) (seq t)) (cons index result) result))))))))

(->> "rosalind_subs.txt"
  (io/resource)
  (io/reader)
  (line-seq)
  (#(reverse (find-motifs-2 (first %) (second %)))))
