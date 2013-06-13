(ns rosalind.gc
  (:use clojure.java.io)
  (:use rosalind.fasta))

;;
;; http://rosalind.info/problems/gc/
;;

(defn nucleotide-count [dna-string & nucleotides]
  (let [freqs (frequencies dna-string)]
    (reduce + (map freqs nucleotides))))

(defn nucleotide-ratio [dna-string & nucleotides]
  (/ (apply nucleotide-count dna-string nucleotides) (count dna-string)))

(defn gc-ratio [dna-string] (nucleotide-ratio dna-string \C \G))

(defn gc-pct [dna-string] (float (* 100 (gc-ratio dna-string))))

(defn assoc-gc-val [f fasta-map] 
  (assoc fasta-map :gc (f (:seq fasta-map)))) 

(defn assoc-gc-vals [f fasta-maps] 
  (map (partial assoc-gc-val f) fasta-maps))

(defn max-gc [fasta-maps-with-gc] 
    (apply max-key :gc fasta-maps-with-gc))

(defn gc-result [file] 
  (with-open [rdr (reader (resource file))]
    (max-gc (assoc-gc-vals gc-pct (parse-fasta (line-seq rdr))))))

(defn pretty-gc [fasta-map] 
  (format "%s\n%s" (:header fasta-map) (:gc fasta-map)))

(pretty-gc (gc-result "rosalind_gc.txt"))