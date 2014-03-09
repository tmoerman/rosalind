(ns rosalind.sset
  (require [clojure.math.combinatorics :as combo]
           [clojure.contrib.generic.math-functions :as math]))

;; Explore properties of subsets

(count (combo/subsets (range 2)))
(count (combo/subsets (range 3)))
(count (combo/subsets (range 4)))
(count (combo/subsets (range 5)))

;; Calculate with bigints

(defn solve [n] (mod (reduce * (repeat n (bigint 2))) 1000000))

(solve 836)
