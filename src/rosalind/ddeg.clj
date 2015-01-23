(ns rosalind.ddeg
  "Double-Degree Array
   http://rosalind.info/problems/ddeg/"
  (:require [rosalind.deg :refer [read-tuples]]
            [clojure.string :as str]))

(defn solve [[[nr-nodes nr-edges] & edges]]
  (let [nodes (->> nr-nodes range (map inc))

        neighbours (->> edges
                        (reduce (fn [m [a b]]
                                  (-> m
                                      (update a #(conj % b))
                                      (update b #(conj % a)))) {}))]
    (->> nodes
         (map #(->> %
                    neighbours
                    (mapcat neighbours)
                    (count))))))

(defn execute []
  (->> "rosalind_ddeg.txt"
       read-tuples
       solve
       (str/join " ")
       (spit "resources/rosalind_ddeg_out.txt")))

; (execute)
