(ns rosalind.revp
  (require [clojure.java.io :as io]
           [rosalind.core :as ros]
           [rosalind.fasta :as fas]
           [clojure.string :as str]
           [clojure.java.io :as io]))

;;
;; http://rosalind.info/problems/revp/
;;

(defn reverse-palindrome? [string]
  (= string (reverse (ros/complement string))))

(defn triplet [index string]
  {:idx (+ 1 index)
   :dna string
   :len (count string)})

(defn partition-indexed [string n]
  (map-indexed triplet (partition n 1 string)))

(defn output-line [triplet]
  (str (:idx triplet) " " (:len triplet) "\n"))

(defn revp [dna]
  (->>
     (range 4 13)
     (filter even?)
     (mapcat (partial partition-indexed dna))
     (filter (comp reverse-palindrome? :dna))
     (map output-line)
     (str/join)))

(def input-file "rosalind_revp.txt")
(def output-file "resources/rosalind_revp_out.txt")

(let [dna (->> input-file
             (io/resource)
             (io/reader)
             (line-seq)
             (fas/parse-fasta)
             (first)
             (:seq)
             (apply str))]
    (->> dna
      (revp)
      (spit output-file)))
