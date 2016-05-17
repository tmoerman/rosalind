(ns rosalind.pdst
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [rosalind.core :as r]
            [rosalind.fasta :as fasta]))

(defn p-distance
  [s1 s2]
  (let [length (count s1)
        nr-eq  (->> (map vector s1 s2)
                    (remove (fn [[a b]] (= a b)))
                    (count))]
    (/ nr-eq length)))

(defn distance-matrix
  [strings]
  (->> (for [s1 strings]
         (for [s2 strings]
           (p-distance s1 s2)))
       (map (fn [line] (->> line
                            (map #(->> % (float) (format "%.5f"))))))))

(defn format-matrix
  [matrix]
  (->> matrix
       (map (partial str/join " "))
       (str/join "\n")))

#_(->> "rosalind_pdst.txt"
     io/resource
     io/reader
     line-seq
     fasta/parse-fasta
     (map :seq)
     distance-matrix
     format-matrix
     (spit "resources/rosalind_pdst_out.txt"))
