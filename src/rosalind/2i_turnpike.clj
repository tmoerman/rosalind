(ns rosalind.2i-turnpike
  "Partial digest a.k.a. Turnpike problem.
   http://rosalind.info/problems/2i/"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.numeric-tower :refer [abs] :as math]))

;; Note: in Clojure there is no 'Bag' collection type.
;; Instead I use a frequency map, denoted as L in the code.

(defn contains-all? [L xs]
  (every? (partial contains? L) xs))

(defn minus [L x] (if (and (> (or (L x) 0) 1)) (update-in L [x] dec) (dissoc L x)))

(defn minus-all [L xs] (reduce minus L xs))

(defn max-of [L] (->> L keys (reduce max)))

(defn delta [y X] (->> X (map #(abs (- y %)))))

(defn skiena-partial-digest
  "Accepts a sequence representing the partial digest."
  [s]
  (let [L (->> s
               (drop-while (complement pos?))
               (frequencies))
        width (max-of L)]
    (letfn [(step [L' X] ;; => Seq[Vector]
                   (if (empty? L')
                     [(sort X)] ;; as per the return type of the step function
                     (let [m (max-of L')]
                       (->> [m (- width m)] ;; TODO figure out why a for comprehension does not work here
                            (mapcat (fn [x]
                                        (let [d (delta x X)]
                                          (if (contains-all? L' d)
                                            (step (minus-all L' d) (conj X x))))))))))]
      (step (minus L width) [0 width]))))

(defn str->int [s] (Integer/parseInt s))

(defn solve []
  (->> (-> "rosalind_2i.txt"
           io/resource
           io/reader
           line-seq
           first
           (str/split #" "))
       (map str->int)
       (skiena-partial-digest)
       (first)
       (str/join " ")
       (spit "resources/rosalind_2i_out.txt")))

;; test

(skiena-partial-digest [2 2 3 3 4 5 6 7 8 10])
