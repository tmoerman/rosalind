(ns rosalind.perm
  (:use [clojure.string :only (join)]))

;;
;; http://rosalind.info/problems/perm/
;;

(defn permutate [coll]
  (if (empty? coll)
    '(nil) ;; remember List(Nil) from the Scala Coursera course
    (for [e  coll
          :let [tail (filter (partial not= e) coll)]
          c (permutate tail)]
      (cons e c))))

(defn make-range [n]
  (range 1 (inc n)))

(permutate (make-range 3))

(defn perm-result [n]
  (let [perms (permutate (make-range n))]
    (spit "resources/rosalind_perm_out.txt"
      (str (count perms) "\n"
        (join "\n" (map #(join " " %) perms))))))

(def n 3)

(perm-result n)
