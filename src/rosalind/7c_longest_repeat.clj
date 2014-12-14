(ns rosalind.7c-longest-repeat
  "Longest Repeat Problem
   http://rosalind.info/problems/7c/"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn binary-search
  [idx->dir+res L H]
  (loop [low-idx  L
         high-idx H
         results  {}]
    (let [mid-idx (quot (+ low-idx high-idx) 2)]
      (if-let [result (results mid-idx)]
        result
        (let [[dir res] (idx->dir+res mid-idx)
              results+ (assoc results mid-idx res)]
          (cond
           (= dir :up)   (recur (inc mid-idx) high-idx results+)
           (= dir :down) (recur low-idx (dec mid-idx) results+)))))))

(defn calculate-frequent-kmers
  [text k]
  (println "considering kmers" k)
  (let [frequent-kmers (->> (partition k 1 text)
                            (frequencies)
                            (filter (fn [[_ freq]] (> freq 1))))
        direction (if (empty? frequent-kmers) :down :up)]
    (println k frequent-kmers direction)
    [direction frequent-kmers]))

(defn solve-binary
  [text]
  (let [size (count text)
        dir-fn (partial calculate-frequent-kmers text)]
    (->> (binary-search dir-fn 0 size)
         ffirst
         str/join)))

(defn execute []
  (->> "rosalind_7c.txt"
       io/resource
       io/reader
       line-seq
       first
       solve-binary
       (spit "resources/rosalind_7c_out.txt")))

; (time (execute))
