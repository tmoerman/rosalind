(ns rosalind.2a-protein
  "Protein translation problem.
   http://rosalind.info/problems/2a/"
  (:require [rosalind.core :as ros]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(->> "rosalind_2a.txt"
     io/resource
     io/reader
     line-seq
     first
     ros/translate
     str/join
     (spit "resources/rosalind_2a_out.txt"))



