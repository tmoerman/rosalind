(ns rosalind.lexf
  (require [clojure.math.combinatorics :as combo]
           [clojure.string :as str]))

(defn enumerate [kmer n]
    (if (= 0 n)
      '(nil)
      (for [e kmer
            c (enumerate kmer (dec n))]
        (conj c e))))

(def output-file "resources/rosalind_lexf_out.txt")

(->>
  (enumerate "RKTUNELZ" 3)
  (map (partial apply str))
  (str/join "\n")
  (spit output-file))

 ;;
 ;; Alternative implementation with clojure.math.combinatorics
 ;;
 ;; As you can see, the problem is isomorphic with the concept of "selections".
 ;;

 (->>
   (combo/selections "RKTUNELZ" 3)
   (map (partial apply str))
   (str/join "\n")
   (prn))
