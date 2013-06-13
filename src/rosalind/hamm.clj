(ns rosalind.hamm
  (:use clojure.java.io))

;;
;; http://rosalind.info/problems/hamm/
;;

(defn hamm-result [file]
  (with-open [rdr (reader (resource file))]
    (let [[line1 line2] (line-seq rdr)]
      (hamming-distance line1 line2))))

(defn hamming-distance [coll1 coll2] 
  (count (filter false? (map = coll1 coll2))))

(hamm-result "rosalind_hamm.txt")