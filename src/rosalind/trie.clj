(ns rosalind.trie
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn integrate
  "Accepts a trie and a string to merge into the trie.
  We start integrating into the tree from the :root node.
  The tail recursion maintains the growing trie, the path down the trie
  and the remainder of the string to merge into the trie at the position of the path."
  [trie string]
  (loop [acc        trie
         path       [:root]
         [c & rest] string]
    (let [path-next (conj path c)]
      (if (nil? rest)
        (assoc-in acc path-next {})
        (let [path-exists? (-> acc (get-in path) (find c))
              acc-next (if path-exists? acc (assoc-in acc path-next {}))]
          (recur acc-next path-next rest))))))

(defn integrate-all
  "Accepts a list of strings. Integrates all strings into a trie."
  [strings]
  (reduce integrate {:root {}} strings))

(defn gather-edges
  "Non-tail recursion."
  ([trie]
   (gather-edges 0 1 [] trie))
  ([from-n to-n acc trie]
   (if (empty? trie)
     acc ; trivial case
     (let [[node sub-entries] (first trie)
           zero-val  (conj acc [from-n to-n node])
           sub-tries (->> sub-entries
                          (sort-by first)
                          (map #(apply hash-map %)))]
       (reduce (fn [current-acc sub-trie]
                 (let [next-to-n (->> current-acc (map second) (reduce max) inc)]
                   (gather-edges to-n next-to-n current-acc sub-trie)))
               zero-val sub-tries)))))

(defn format-output
  "Format the adjacency list vectors to an output string."
  [adjacency-list]
  (->> adjacency-list
       (drop 1) ; drop the root
       (map #(str/join " " %))
       (str/join "\n")))

(defn trie-result [data] (->> data integrate-all gather-edges format-output))

#_(->> "rosalind_trie.txt"
     io/resource
     io/reader
     line-seq
     trie-result
     (spit "resources/rosalind_trie_out.txt"))

#_(-> ["ATAGA" "ATC" "GAT"] trie-result prn)