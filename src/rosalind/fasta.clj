(ns rosalind.fasta
  (:use [clojure.string :only (join trim)]))

;; 
;; fasta related functions
;;

(defn is-fasta-header-line [line] (.startsWith line ">"))

(def is-fasta-seq-line (complement is-fasta-header-line))

(defn join-trimmed [strings] (join (map trim strings)))

(defn parse-fasta
  "parses a fasta file to a seq of maps {:header \"header\" :seq \"AAACTGCCA\"}"
  [lines]
  (let [step (fn [c]
               (when-let [s (seq c)]
                 (let [fasta-header-line (.substring (first s) 1)
                       [fasta-seq-lines fasta-rest] (split-with is-fasta-seq-line (rest s))]
                   (cons {:header fasta-header-line
                          :seq    (join-trimmed fasta-seq-lines)} 
                         (parse-fasta fasta-rest)))))]
    (lazy-seq (step lines))))