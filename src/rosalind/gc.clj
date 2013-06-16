(ns rosalind.gc
  (:use clojure.java.io)
  (:use rosalind.fasta))

;;
;; http://rosalind.info/problems/gc/
;;

(defn count-gc [dna-string] (reduce + (map (frequencies dna-string) '(\C \G))))

(defn gc-ratio [dna-strings]
  (float (* 100 (/ (reduce + (map count-gc dna-strings))
                   (reduce + (map count    dna-strings))))))

(defn find-max-gc [fasta-maps]
  (apply max-key :gc (map #(assoc % :gc (gc-ratio (:seq %))) fasta-maps)))

(defn pretty [fasta-map] 
  (format "%s\n%s" (:id fasta-map) (:gc fasta-map)))

(defn gc-result [file-name] 
  (with-open [rdr (reader (resource file-name))]
    (pretty (find-max-gc (parse-fasta (line-seq rdr))))))

(gc-result "rosalind_gc.txt")