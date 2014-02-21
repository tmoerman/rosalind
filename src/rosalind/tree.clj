(ns rosalind.tree
  (require [clojure.set :as set]
           [clojure.string :as str]
           [clojure.java.io :as io]))

;;
;; http://rosalind.info/problems/tree/
;;

;; Good effort, soldier!

(defn contains-pred [n] (partial some (partial = n)))

(defn make-sets [n] (map hash-set (map inc (range n))))

(defn gather-subgraphs [n edges]
  (loop [sets           (make-sets n)
         [[a b] & tail] edges
         cnt            (count edges)]
     (if (= 0 cnt)
       sets
       (let [sets-with-ab    (apply set/union (filter (some-fn (contains-pred a) (contains-pred b)) sets))
             sets-without-ab (filter (complement (some-fn (contains-pred a) (contains-pred b))) sets)
             new-sets        (conj sets-without-ab sets-with-ab)]
       (recur new-sets tail (dec cnt))))))

(defn ->edge [line] (map read-string (str/split line #" ")))

(defn parse-input [[cnt & lines]]
  [(read-string cnt) (map ->edge lines)])

(let [[n edges] (->> "rosalind_tree.txt"
                  (io/resource)
                  (io/reader)
                  (line-seq)
                  (parse-input))]
  (->>
    (gather-subgraphs n edges)
    (count)
    (dec)))

;; Also correct... TROLOLOLOOO ^_^

(let [[n edges] (->> "rosalind_tree.txt"
                  (io/resource)
                  (io/reader)
                  (line-seq)
                  (parse-input))]
  (dec (- n (count edges))))
