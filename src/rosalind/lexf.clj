(ns rosalind.lexf
  (require [clojure.string :as str]))

(defn enumerate [kmer n]
    (if (= 0 n)
      '(nil)
      (for [e kmer
            c (generate kmer (dec n))]
        (conj c e))))

(def string "RKTUNELZ")
(def n 3)

(def output-file "resources/rosalind_lexf_out.txt")

(->>
  (generate string 3)
  (map (partial apply str))
  (str/join "\n")
  (spit output-file))
