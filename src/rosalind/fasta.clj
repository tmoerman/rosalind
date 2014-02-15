(ns rosalind.fasta
  (:require [clojure.string :as str]))

;;
;; fasta related functions
;;

(defn fasta-id-line? [line] (.startsWith line ">"))

(def fasta-seq-line? (complement fasta-id-line?))

(defn fasta-comment-line? [line] (.startsWith line ";"))

(def fasta-line? (complement fasta-comment-line?))

(defn parse-fasta
  "parses a fasta file to a seq of maps {:id 'header' :seq ('AAACTGCCA' 'GGGCCCTTTAA')}"
  [lines]
  (let [step (fn [c]
               (when-let [s (seq c)]
                 (let [fasta-id-line (str/replace-first (first s) ">" "")
                       [fasta-seq-lines fasta-rest] (split-with fasta-seq-line? (rest s))]
                   (cons {:id  fasta-id-line
                          :seq (str/join fasta-seq-lines)}
                         (parse-fasta fasta-rest)))))]
    (lazy-seq (step (filter fasta-line? lines)))))
