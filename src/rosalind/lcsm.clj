(ns rosalind.lcsm
  (:use clojure.java.io)
  (:use rosalind.fasta))

;;
;; http://rosalind.info/problems/lcsm/
;;

(defn only-fasta-seqs-as-strings 
  [fasta-map]
  (->> fasta-map
       (map :seq)
       (map (partial apply str))))

(defn partition-sliding [n s] 
  (->> s 
       (partition n 1)
       (map (partial apply str))))

(defn shortest [s1 s2] (if (< (count s1) (count s2)) s1 s2))

(defn contained-in-all? [all s] (every? #(.contains % s) all))

(defn find-shared-motif [dna-strings]
  (let [ref-dna  (reduce shortest dna-strings)
        rest-dna (filter (partial not= ref-dna) dna-strings)]
    
    (loop [length (count ref-dna)]
      (print '.) ;; remove me
      (if-let [result (first (filter (partial contained-in-all? rest-dna) (partition-sliding length ref-dna)))]
        result
        (recur (dec length))))))

(prn
  (->> "rosalind_lcsm.txt"
       (resource)
       (reader)
       (line-seq)
       (parse-fasta)
       (only-fasta-seqs-as-strings)
       (find-shared-motif)))

;(just-do-it)