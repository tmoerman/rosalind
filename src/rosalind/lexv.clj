(ns rosalind.lexv
  (:require [rosalind.core :refer [str->int]]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn generate
  ([s n] (generate s n [""]))
  ([s n acc]
   (if (= n 0)
     nil
     (->> acc
          (mapcat (fn [acc-el]
                    (->> s
                         (map (fn [c] (str acc-el c))))))
          (mapcat (fn [new-el]
                    (conj (generate s (dec n) [new-el]) new-el)))))))

(let [[line-1 line-2] (->> "rosalind_lexv.txt"
                           io/resource
                           io/reader
                           line-seq
                           vec)
      s (str/split line-1 #" ")
      n (str->int line-2)]
  (->> (generate s n)
       (str/join "\n")
       (spit "resources/rosalind_lexv_out.txt")))