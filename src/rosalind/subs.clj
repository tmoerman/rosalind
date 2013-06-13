(ns rosalind.subs
  (:use clojure.java.io))

;;
;; http://rosalind.info/problems/subs/
;;

(defn subs-result [file]
  (with-open [rdr (reader (resource file))]
    (let [[line1 line2] (line-seq rdr)]
      (reverse (find-motifs line1 line2)))))

(defn find-motifs 
  "find indices of occurrences of t in s"
  [s t]
  (loop [curr s
         result nil]
    (let [index (.indexOf curr t)
          last (if (empty? result) 0 (first result))]
      (if (neg? index)
        result
        (recur 
          (.substring curr (inc index))     
          (cons (+ index (inc last)) result))))))

(subs-result "rosalind_subs.txt")