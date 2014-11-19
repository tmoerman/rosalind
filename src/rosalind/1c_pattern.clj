(ns rosalind.1c_pattern
  "Pattern Matching Problem
   http://rosalind.info/problems/1c/"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def pattern "ATAT")

(def text "GATATATGCATATACTT")

(defn solve [pattern text]
  (->> text
       (partition (count pattern) 1)
       (map str/join)
       (map-indexed vector)
       (filter (fn [[i s]] (= s pattern)))
       (map first)))

(defn execute []
  (let [[pattern text] (->> "rosalind_1c.txt"
                            io/resource
                            io/reader
                            line-seq)]
    (->> (solve pattern text)
         (str/join " "))))

255 241 228 143 67 10
