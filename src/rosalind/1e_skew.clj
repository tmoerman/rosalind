(ns rosalind.1e-skew
  "Minimum skew problem.
   http://rosalind.info/problems/1e/"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def code {\C dec
           \G inc
           \A identity
           \T identity})

(defn skew [g] (reduce (fn [acc x] (conj acc ((code x) (last acc)))) [0] g))

(defn find-minimum-skew
  [s]
  (let [skew-seq (-> s seq skew)
        min-skew (reduce min skew-seq)]
    (->> skew-seq
         (map-indexed vector)
         (filter (fn [[i v]] (= v min-skew)))
         (map first))))

(-> "rosalind_1e.txt"
    io/resource
    io/reader
    line-seq
    first
    find-minimum-skew)



