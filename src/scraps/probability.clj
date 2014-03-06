(ns probability
  (require [clojure.math.combinatorics :as combo]))

;; sampling with replacement
;; N = nˆr

(->>
  (combo/selections [1 2 3 4 5] 2)
  (count))

;; sampling without replacement
;; N = n!

;; where r == n

(->>
  (combo/permutations [1 2 3 4 5])
  (count))

;; subpopulations of size r

(->>
 (combo/combinations [1 2 3 4 5] 2)
 (count))

(prn (combo/combinations [1 2 3 4 5] 2))

(C 5 2)

;; factorial

(defn ! [n]
  (apply * (map inc (range n))))

;; binomial coefficient

(defn C [n r]
  (/ (! n)
     (* (! r) (! (- n r)))))

(C 5 2)

(C 3 2)

(C 5 1)
