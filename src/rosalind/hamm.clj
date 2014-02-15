(ns rosalind.hamm
  (require [clojure.java.io :as io]))

;;
;; http://rosalind.info/problems/hamm/
;;

(defn hamming-distance [coll1 coll2]
  (count (filter false? (map = coll1 coll2))))

(->> "rosalind_hamm.txt"
  (io/resource)
  (io/reader)
  (line-seq)
  (#(hamming-distance (first %) (second %))))
