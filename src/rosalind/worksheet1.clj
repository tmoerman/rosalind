(ns rosalind.worksheet1)
(use 'clojure.java.io)

;;
;; http://rosalind.info/problems/dna/
;;

(def dna-file (resource "rosalind_dna.txt"))

(def dna-lines (line-seq (reader dna-file)))

(def dna (first dna-lines))

(defn freqs [coll] (vals (sort (frequencies coll))))

(freqs dna)



;; table 

(def proteins (apply hash-map '(a b c d)))

(print proteins)

;;
;; http://rosalind.info/problems/revc/
;;


(def dna-complement {\A \T \C \G \G \C \T \A})

(def revc-temp "AAAACCCGGT")



