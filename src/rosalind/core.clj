(ns rosalind.core)

(def dna-complement
  {\A \T
   \C \G
   \G \C
   \T \A})

(defn complement [dna]
  (map dna-complement dna))

(def rna-transcription
  {\A \A
   \C \C
   \G \G
   \T \U})

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

(defn partition-to-codons [rna]
  (map (partial apply str) (partition 3 rna)))

(defn lookup-in-rna-codon-table [codon]
  (rna-codon-table (symbol codon)))

(defn translate [rna]
  (->> rna
       (partition-to-codons)
       (map lookup-in-rna-codon-table)
       (take-while (partial not= 'Stop))))

(translate (transcribe "AACCGGTT"))

