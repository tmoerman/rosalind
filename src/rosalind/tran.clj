(ns rosalind.tran
  (require [clojure.java.io :as io]
           [rosalind.core :as ros]
           [rosalind.fasta :as fas]))

(defn purine? [n]
  (or (= \A n) (= \G n)))

(defn pyrimidine? [n]
  (or (= \C n) (= \T n)))

(defn tran-type [[a b]]
  (if (= a b)
    :equal
    (if (= (ros/base-types a) (ros/base-types b))
      :transition
      :transversion)))

(defn tran-ratio [freqs]
  (/ (:transition freqs) (:transversion freqs)))

(->> "rosalind_tran.txt"
  (io/resource)
  (io/reader)
  (line-seq)
  (fas/parse-fasta)
  (map :seq)
  (map (partial apply str))
  (apply (partial map vector))
  (map tran-type)
  (frequencies)
  (tran-ratio)
  (double)
  (prn))
