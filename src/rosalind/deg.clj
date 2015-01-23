(ns rosalind.deg
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [rosalind.core :refer [str->int]]))

(defn read-tuples [file]
  (->> (-> file
           (io/resource)
           (slurp)
           (str/split #"\n"))
       (map #(str/split % #" "))
       (map (fn [[a b]] [(str->int a) (str->int b)]))))

(defn solve []
  (->> "rosalind_deg.txt"
       (read-tuples)
       (drop 1)
       (mapcat identity)
       frequencies
       sort
       (map second)
       (str/join " ")
       (spit "resources/rosalind_deg_out.txt")))

; (solve)




