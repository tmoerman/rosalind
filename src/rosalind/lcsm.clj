(ns rosalind.lcsm
  (require [clojure.java.io :as io]
           [rosalind.fasta :as fasta]))

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

;; slow iterative version

(defn find-shared-motif [dna-strings]
  (let [ref-dna  (reduce shortest dna-strings)
        rest-dna (filter (partial not= ref-dna) dna-strings)]

    (loop [length (count ref-dna)]

      (if-let [result (first (filter (partial contained-in-all? rest-dna) (partition-sliding length ref-dna)))]
        result
        (recur (dec length))))))

;; faster binary search

(defn binary-find-longest-common-substring-length [dna-strings]
  (let [ref-dna  (reduce shortest dna-strings)
        rest-dna (filter (partial not= ref-dna) dna-strings)]

    (loop [l 0
           r (count ref-dna)]
      (if (= (inc l) r)
        l
        (let [mid (int (/ (+ l r) 2))
              go-bigger (some (partial contained-in-all? rest-dna) (partition-sliding mid ref-dna))
              next-l (if go-bigger mid l)
              next-r (if go-bigger r mid)]
          (recur next-l next-r))))))

(defn find-shared-motif-fast [dna-strings]
  (let [length (binary-find-longest-common-substring-length dna-strings)]
    (first (filter (partial contained-in-all? dna-strings) (partition-sliding length (first dna-strings))))))

;; combinator

(->> "rosalind_lcsm.txt"
     (io/resource)
     (io/reader)
     (line-seq)
     (fasta/parse-fasta)
     (only-fasta-seqs-as-strings)
     (find-shared-motif-fast)
     (prn))
