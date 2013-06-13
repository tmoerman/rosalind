(ns rosalind.subs
  (:use clojure.java.io))

;;
;; http://rosalind.info/problems/subs/
;;

(defn subs-result [file]
  (with-open [rdr (reader (resource file))]
    (let [[line1 line2] (line-seq rdr)]
      (reverse (find-motifs-2 line1 line2)))))

;; Kind of lucky solution, but not elegant from algorithmic point of view.
;; By coincidence, the desired output (base 1) is used by the algorithm,
;; this is not a repeatable design finesse, but a fortunate property of this
;; particular implementation.
;; In short, there is too much going on at the same time = complected.

(defn find-motifs 
  "find indices of occurrences of t in s"
  [s t]
  (loop [curr   s
         result nil]
    (let [index (.indexOf curr t)
          last (if (empty? result) 0 (first result))]
      (if (neg? index)
        result
        (recur 
          (.substring curr (inc index))     
          (cons (+ index last 1) result))))))

;; Although longer, this implementation is better. 
;; The different ideas of the algorithm are decomplected.

(defn find-local-motif-indices
  ""
  [s t]
  (loop [curr   s
         result nil]
    (let [index (.indexOf curr t)]
      (if (neg? index)
        result
        (recur 
          (.substring curr (inc index))     
          (cons index result))))))


(defn pile [indices] 
  (loop [curr   indices
         result nil]
    (if (empty? curr)
      result
      (recur (rest curr) 
             (cons
               (+ (first curr) 
                  (if (empty? result) 0 (inc (first result)))) result)))))

(defn find-motifs-2 [s t]
  (reverse (map inc (pile (reverse (find-local-motif-indices s t))))))

(subs-result "rosalind_subs.txt")