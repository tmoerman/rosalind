(ns rosalind.worksheet1)
(use 'clojure.java.io)
(use '[clojure.string :only (join split trim)])

;;
;; utility methods
;;

(defn map-vals
  "apply the function f to the values of map m"
  [f m]
  (into {} (for [[k v] m] [k (f v)])))

(defn lines [resource-file-name] (line-seq (reader (resource resource-file-name))))

(defn is-fasta-header-line [line] (.startsWith line ">"))

(def is-fasta-seq-line (complement is-fasta-header-line))

(defn join-trimmed [strings] (join (map trim strings)))

(defn parse-fasta
  "parses a fasta file to a seq of maps {:header \"header\" :seq \"AAACTGCCA\"}" 
  [lines]
  (let [step (fn [c]
               (when-let [s (seq c)]
                 (let [fasta-header-line (.substring (first s) 1)
                       [fasta-seq-lines fasta-rest] (split-with is-fasta-seq-line (rest s))]
                   (cons {:header fasta-header-line
                          :seq    (join-trimmed fasta-seq-lines)} 
                         (parse-fasta fasta-rest)))))]
    (lazy-seq (step lines))))


(parse-fasta (lines "rosalind_gc.txt"))

(take 2 (parse-fasta (lines "rosalind_gc.txt")))


;;
;; http://rosalind.info/problems/dna/
;;

(def dna (first (lines "rosalind_dna.txt")))

(defn freqs [collection] (vals (sort (frequencies collection))))

(freqs dna)

; (println (freqs dna))



;;
;; http://rosalind.info/problems/revc/
;;

(def revc (first (lines "rosalind_revc.txt")))

(def dna-complement 
  {\A \T
   \C \G
   \G \C
   \T \A})

(defn reverse-complement [dna-string] (join (map dna-complement (reverse dna-string))))

(reverse-complement revc)

; (println (reverse-complement revc))



;;
;; http://rosalind.info/problems/gc/
;;

(defn nucleotide-count [dna-string & nucleotides]
  (let [freqs (frequencies dna-string)]
    (reduce + (map freqs nucleotides))))

(defn nucleotide-ratio [dna-string & nucleotides]
  (/ (apply nucleotide-count dna-string nucleotides) (count dna-string)))

(defn gc-ratio [dna-string] (nucleotide-ratio dna-string \C \G))

(defn gc-pct [dna-string] (float (* 100 (gc-ratio dna-string))))

(defn assoc-gc-val [f fasta-map] 
  (assoc fasta-map :gc (f (:seq fasta-map)))) 

(defn assoc-gc-vals [f fasta-maps] 
  (map (partial assoc-gc-val f) fasta-maps))

(defn max-gc [fasta-maps-with-gc] 
    (apply max-key :gc fasta-maps-with-gc))

(defn gc-result [file] 
  (with-open [rdr (reader (resource file))]
    (max-gc (assoc-gc-vals gc-pct (parse-fasta (line-seq rdr))))))

(defn pretty-gc [fasta-map] 
  (format "%s\n%s" (:header fasta-map) (:gc fasta-map)))

(pretty-gc (gc-result "rosalind_gc.txt"))





