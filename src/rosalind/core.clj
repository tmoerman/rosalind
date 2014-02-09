(ns rosalind.core)

(def dna-complements
  {\A \T
   \C \G
   \G \C
   \T \A})

(defn dna-complement [dna]
  (map dna-complements dna))

(defn reverse-complement [dna]
  (reverse (dna-complement dna)))

(def rna-transcription
  {\A \A
   \C \C
   \G \G
   \T \U})

(def rna-start-codon 'AUG)
(def rna-stop-codons ['UAA 'UAG 'UGA])

(def rna-codon-table
  (apply hash-map '(
    UUU F      CUU L      AUU I      GUU V
    UUC F      CUC L      AUC I      GUC V
    UUA L      CUA L      AUA I      GUA V
    UUG L      CUG L      AUG M      GUG V
    UCU S      CCU P      ACU T      GCU A
    UCC S      CCC P      ACC T      GCC A
    UCA S      CCA P      ACA T      GCA A
    UCG S      CCG P      ACG T      GCG A
    UAU Y      CAU H      AAU N      GAU D
    UAC Y      CAC H      AAC N      GAC D
    UAA Stop   CAA Q      AAA K      GAA E
    UAG Stop   CAG Q      AAG K      GAG E
    UGU C      CGU R      AGU S      GGU G
    UGC C      CGC R      AGC S      GGC G
    UGA Stop   CGA R      AGA R      GGA G
    UGG W      CGG R      AGG R      GGG G )))

(def monoisotopic-mass-table
  (apply hash-map '(
    \A   71.03711
    \C   103.00919
    \D   115.02694
    \E   129.04259
    \F   147.06841
    \G   57.02146
    \H   137.05891
    \I   113.08406
    \K   128.09496
    \L   113.08406
    \M   131.04049
    \N   114.04293
    \P   97.05276
    \Q   128.05858
    \R   156.10111
    \S   87.03203
    \T   101.04768
    \V   99.06841
    \W   186.07931
    \Y   163.06333 )))

;; transcribe

(defn transcribe [dna]
  (map rna-transcription dna))

;; translate

(defn to-codon-symbol [rna-seq]
  (symbol (apply str rna-seq)))

(defn partition-to-codons [rna]
  (partition 3 rna))

;; LAZY translate until stop codon encountered

(defn translate-raw [rna]
  "Translate the specified RNA sequence to protein without taking into account 'Stop codons"
  (->> rna
    (partition-to-codons)
    (map to-codon-symbol)
    (map rna-codon-table)))

(defn translate [rna]
  (->> rna
    (translate-raw)
    (trim-at-stop)))

(defn contains-stop? [polypeptide]
 (some (partial = 'Stop) polypeptide))

(defn trim-at-stop [polypeptide]
  (take-while (partial not= 'Stop) polypeptide))

(translate (transcribe "AACCGGTT"))
