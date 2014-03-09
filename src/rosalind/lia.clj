(ns rosalind.lia
  (require [clojure.core.match :refer [match]]
           [clojure.string :as str]
           [clojure.math.combinatorics :as combo]
           [clojure.contrib.generic.functor :refer [fmap]]
           [clojure.contrib.generic.math-functions :as math]))

;; figure out some clues about the probabilities of the offspring zygosity.

(defn zyg-probs [rec het dom] {:rec rec :het het :dom dom})

(def punnet-squares { #{:rec} (zyg-probs 4/4  0/4  0/4)
                      #{:het} (zyg-probs 1/4  2/4  1/4)
                      #{:dom} (zyg-probs 0/4  0/4  4/4)
                      #{:rec :het} (zyg-probs 2/4  2/4  0/4)
                      #{:rec :dom} (zyg-probs 0/4  4/4  0/4)
                      #{:het :dom} (zyg-probs 0/4  2/4  2/4) })

(defn calculate-zyg-probs [father mother allele]
  { allele
    (->>
      (for [[f-type f-prob] (seq (allele father))
            [m-type m-prob] (seq (allele mother))
            :let [zygosity     (hash-set f-type m-type)]
            :let [apply-factors (fn [[_ prob]] (* prob f-prob m-prob))]]
        (map apply-factors (punnet-squares zygosity)))
        (apply (partial map vector))
        (map (partial reduce +))
        (apply zyg-probs))})

(def Jon {:a (zyg-probs 0 1 0)
          :b (zyg-probs 0 1 0)})

(def Ygritte {:a (zyg-probs 0 1 0)
              :b (zyg-probs 0 1 0)})

(calculate-zyg-probs Jon Ygritte :a)

(def alleles [:a :b])

(defn make-child [father mother]
  (->> alleles
    (map (partial calculate-zyg-probs father mother))
    (reduce conj)))

;; conclusion: all offspring are of the same zygosity as the child of Jon and Ygritte ... yay

(def Finn (make-child Jon Ygritte))

(make-child Finn Ygritte)

;; okay, with this conclusion we proceed strictly mathematically

;; factorial

(defn ! [n]
  (apply * (map bigint (map inc (range n)))))

;; binomial coefficient

(defn C [n r]
  (/ (! n)
     (* (! r) (! (- n r)))))

(defn prob [cnt n]
  (* (math/pow 3/4 (- cnt n))
     (math/pow 1/4 n)
     (C cnt n)))

(defn solve [k N]
  (->>
   (range N)
   (map (partial prob (math/pow 2 k)))
   (reduce +)
   (- 1)))

(solve 2 1)

(solve 7 38)

(prn (solve 7 38))

