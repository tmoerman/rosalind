(ns rosalind.pper
  (require [clojure.math.combinatorics :as combo]))

;;
;; http://rosalind.info/problems/pper/
;;

;; problem breakdown

(defn fac [n]
  (if (zero? n)
    1
    (* n (fac (dec n)))))

(defn count-combinations [n r]
  (/ (fac n)
    (* (fac r) (fac (- n r)))))

(defn partial-permutations [n r]
  (let [big-n (bigint n)
        big-r (bigint r)]
    (* (count-combinations big-n big-r)
       (fac big-r))))

;; simplified, removed (fac r) from numerator and denominator

(defn pper [n r]
  (/ (fac (bigint n))
     (fac (bigint (- n r)))))

(pper 21 7)

(mod (pper 100 9) 1000000)
