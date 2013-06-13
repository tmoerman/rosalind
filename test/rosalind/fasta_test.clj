(ns rosalind.fasta-test
  (:require [clojure.test :refer :all])
  (:require [rosalind.fasta :refer :all]))

(def test-data 
  '(">Rosalind_6404"
    "CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCC"
    "TCCCACTAATAATTCTGAGG"
    ">Rosalind_5959"
    "CCATCGGTAGCGCATCCTTAGTCCAATTAAGTCCCTATCCAGGCGCTCCGCCGAAGGTCT"
    "ATATCCATTTGTCAGCAGACACGC"
    ">Rosalind_0808"
    "CCACCCTCGTGGTATGGCTAGGCATTCAGGAACCGGAGAACGCTTCAGACCAGCCCGGAC"
    "TGGGAACCTGCGGGCAGTAGGTGGAAT"))

(deftest test-parse-fasta
  (testing "yields a lazy seq"
    (is ()))
    (is (not (realized? (parse-fasta test-data)))))