(ns rosalind.cons
  (require [clojure.string :as str]
           [rosalind.fasta :as fas]
           [clojure.java.io :as io]))

(def nucleotides "ACGT")

(defn profile-matrix [columns]
  (for [c columns
        n nucleotides]
    {:nuc n
     :cnt (->> c
            (filter (partial = n))
            (count)) }))

(defn max-col [column]
  (reduce
    (fn
      [p1 p2]
      (if (> (:cnt p1) (:cnt p2)) p1 p2))
    column))

(->> "rosalind_cons.txt"
  (io/resource)
  (io/reader)
  (line-seq)
  (fas/parse-fasta)
  (map :seq)
  (map (partial apply str))
  (apply (partial map vector))
  (profile-matrix)
  (partition 4)
  (map max-col)
  (map :nuc)
  (str/join)
  (prn))
