(ns rosalind.gc
  (require [clojure.java.io :as io]
           [rosalind.fasta :as fas]))

;;
;; http://rosalind.info/problems/gc/
;;

(defn count-gc [dna-string] (reduce + (map (frequencies dna-string) '(\C \G))))

(defn gc-ratio [dna-string]
  (float (* 100 (/ (count-gc dna-string)
                   (count    dna-string)))))

(defn find-max-gc [fasta-maps]
  (apply max-key :gc (map #(assoc % :gc (gc-ratio (:seq %))) fasta-maps)))

(defn pretty [fasta-map]
  (format "%s\n%s" (:id fasta-map) (:gc fasta-map)))

(->> "rosalind_gc.txt"
  (io/resource)
  (io/reader)
  (line-seq)
  (fas/parse-fasta)
  (find-max-gc)
  (pretty)
  (prn))
