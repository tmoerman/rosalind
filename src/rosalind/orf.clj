(ns rosalind.orf
  (require [clojure.java.io :as io]
           [rosalind.core :as ros]
           [rosalind.fasta :as fas]
           [clojure.string :as str]
           [clojure.java.io :as io]))

;;
;; http://rosalind.info/problems/orf/
;;

(def dna "AGCCATGTAGCTAACTCAGGTTACATGGGGATGACCCCGCGACTTGGATTAGAGTCTCTTTTGGAATAAGCCTGAATGATCCGAGTAGCATCTCAG")

(defn index-of [sub coll]
  (java.util.Collections/indexOfSubList coll sub))

(def stop-seq (seq "AUG"))

(defn find-orf-origins [rna]
  (loop [current rna
         result '()]
    (let [index (index-of stop-seq current)]
      (if (= index -1)
        result
        (recur (drop (+ 3 index) current)
               (conj result (drop index current)))))))

(defn find-orfs-on-strand [dna-seq]
  (->> dna-seq
    (ros/transcribe)
    (find-orf-origins)
    (map ros/translate-raw)
    (filter ros/contains-stop?)
    (map ros/trim-at-stop)
    (map str/join)))

(defn find-orfs [dna]
  (into #{}
    (flatten [(find-orfs-on-strand (seq dna))
              (find-orfs-on-strand (ros/reverse-complement dna))])))

(def input-file "rosalind_orf.txt")
(def output-file "resources/rosalind_orf_out.txt")

(->> input-file
  (io/resource)
  (io/reader)
  (line-seq)
  (fas/parse-fasta)
  (first)
  (:seq)
  (apply str)
  (find-orfs)
  (map #(str % "\n"))
  (str/join)
  (spit output-file))
