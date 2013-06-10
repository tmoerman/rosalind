(ns rosalind.worksheet1)
(use 'clojure.java.io)
(use '[clojure.string :only (join split)])

;;
;; utility methods
;;

(defn lines [resource-file-name] (line-seq (reader (resource resource-file-name))))



;;
;; http://rosalind.info/problems/dna/
;;

(def dna (first (lines "rosalind_dna.txt")))

(defn freqs [collection] (vals (sort (frequencies collection))))

(freqs dna)

(println (freqs dna))




;;
;; http://rosalind.info/problems/revc/
;;

(def revc (first (lines "rosalind_revc.txt")))

(def dna-complement {\A \T \C \G \G \C \T \A})

(defn reverse-complement [dna-string] (join (map dna-complement (reverse dna-string))))

(reverse-complement revc)

(println (reverse-complement revc))


;;
;; 
;;