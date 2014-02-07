(ns rosalind.iprb
  (require [clojure.math.combinatorics :as combo]
           [clojure.set :as set]))

;;
;; http://rosalind.info/problems/iprb/
;;

(def k 18) ;; number of BB individuals homozygous dominant
(def m 27) ;; number of Bb individuals heterozygous
(def n 25) ;; number of bb individuals homozygous recessive

(def population (flatten [(repeat k :BB) (repeat m :Bb) (repeat n :bb)]))
(def combinations (combo/combinations population 2))
(def total (count combinations))
(def freqs (frequencies combinations))

(def vectorize {:BB [:B :B]
                :Bb [:B :b]
                :bb [:b :b]})

(defn punnet-square [p1 p2]
  (combo/cartesian-product (vectorize p1) (vectorize p2)))

(defn dominant? [x] (= x :B))
(defn has-dominant? [v] (some dominant? v))

(defn P-dominant-phenotype-offspring [punnet-square]
  (/ (count (filter has-dominant? punnet-square)) 4))

(def result
  (reduce +
    (for [[pair frequency] freqs]
      (* (/ frequency total) (P-dominant-phenotype-offspring (apply punnet-square pair))))))

(prn (double result))
