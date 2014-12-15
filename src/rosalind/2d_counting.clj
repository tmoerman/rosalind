(ns rosalind.2d-counting
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [rosalind.2c-theoretical-spectrum :refer [integer-mass-table]]))

integer-mass-table

(defn solve
  [target-mass]

  (letfn [(step [mass]
                (let [masses (->> integer-mass-table
                                  seq
                                  (map (fn [[_ m]] (+ mass m))))

                      equal-to-target-mass (->> masses
                                                (filter #(= target-mass %)))

                      lesser-than-target-mass (->> masses
                                                   (filter #(> target-mass %)))]

                  (+ (count equal-to-target-mass)
                     (reduce + (->> lesser-than-target-mass
                                    (map step))))))]
    (step 0)))

(time (solve 550))

(defn solve-dyn
  [target-mass]

  )


;;1391
