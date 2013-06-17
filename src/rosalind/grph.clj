(ns rosalind.graph
  (:use clojure.java.io)
  (:use rosalind.fasta)
  (:use [clojure.string :only [join]]))

;;
;; http://rosalind.info/problems/grph/
;;

(def k 3)

(defn prefix [k s] (take k s))
(defn suffix [k s] (reverse (take k (reverse s))))

(defn adjacent?
  [min-overlap s1 s2] 
  (= (suffix min-overlap s1) (prefix min-overlap s2)))

(defn adjacent-fasta-maps? [min-overlap m1 m2]
  (adjacent? min-overlap (apply str (:seq m1)) (apply str (:seq m2))))

(defn pairs [coll]
  (for [x coll
        y (filter (complement (partial = x)) coll)]
    [x y]))

(defn calculate-graph [fasta-maps]
  (filter (fn [[m1 m2]] (adjacent-fasta-maps? k m1 m2)) (pairs fasta-maps)))

;; two rendering strategies

(defn render-just-ids [[m1 m2]] 
  (format "%s %s" (:id m1) (:id m2)))

(defn render-pretty-labels [[m1 m2]] 
  (format "%s -> %s;" (pretty m1) (pretty m2)))

(defn pretty [m]
  (let [s     (apply str (:seq m))
        head  (apply str (prefix 3 s))
        tail  (apply str (suffix 3 s))]
    (format "%s_%s_%s" head (:id m) tail)))

;; write normal solution out to file

(with-open [rdr (reader (resource "rosalind_grph.txt"))]
  (spit "resources/rosalind_grph_out.txt" 
    (join "\n" 
      (map render-just-ids (calculate-graph (parse-fasta (line-seq rdr)))))))

;; write solution as .dot file consumable by graphviz

(with-open [rdr (reader (resource "rosalind_grph.txt"))]
  (spit "resources/rosalind_grph_out.dot" 
    (str 
      "digraph bla {\n" 
      (join "\n" (map render-pretty-labels (calculate-graph (parse-fasta (line-seq rdr)))))
     "\n}")))