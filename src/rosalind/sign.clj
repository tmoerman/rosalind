(ns rosalind.sign
  (require [clojure.math.combinatorics :as combo]
           [clojure.string :as str]))

;;
;; http://rosalind.info/problems/sign/
;;

(defn range++ [n] (map inc (range n)))

(defn signed-permutations [n]
  (for [selection   (combo/selections [-1 1] n)
        permutation (combo/permutations (range++ n))]
    (map vector selection permutation)))

(defn output [list]
  (str (count list) "\n" (str/join list)))

(->>
  (signed-permutations 4)
  (map (partial map (partial reduce *)))
  (map (partial str/join " "))
  (map #(str % "\n"))
  (output)
  (spit "resources/rosalind_sign_out.txt"))
