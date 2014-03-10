(ns rosalind.seto
  (require [clojure.set :as set]
           [clojure.string :as str]))

(defn parse-numbers [s] (map read-string (str/split (str/replace s #"\{|\}|\," "") #" ")))

(defn format [coll] (str \{ (str/join ", " coll) \}))

(def input (str/split (slurp "resources/seto.txt") #"\n"))

(def N (read-string (input 0)))
(def RANGE (set (map inc (range N))))
(def A (set (parse-numbers (input 1))))
(def B (set (parse-numbers (input 2))))

(def output-sets [(set/union A B)
                  (set/intersection A B)
                  (set/difference A B)
                  (set/difference B A)
                  (set/difference RANGE A)
                  (set/difference RANGE B)])

(def output-string (str/join "\n" (map format output-sets)))

(spit "resources/seto_out.txt" output-string)
