(ns rosalind.1d-clump
  "Clump finding problem
   http://rosalind.info/problems/1d/"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn str->int [s] (Integer/parseInt s))

(defn solve
  [k L t genome]
  (->> genome
       (partition L 1)
       (map (fn [ptn] (->> ptn
                           (partition k 1)
                           (frequencies)
                           (reduce (partial max-key val)))))
       (filter (fn [[_ freq]] (= freq t)))
       (map first)
       (into #{})
       (map str/join)))

(defn execute []
  (let [[text params] (->> "rosalind_1d.txt"
                           io/resource
                           io/reader
                           line-seq)
        [k L t] (->> (str/split params #" ")
                     (map str->int))]
    (->> (solve k L t text)
         (str/join " ")
         (spit "resources/rosalind_1d_out.txt"))))

; (time (execute))
