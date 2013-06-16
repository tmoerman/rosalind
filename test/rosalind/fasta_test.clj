(ns rosalind.fasta-test
  (:use clojure.java.io)
  (:require [clojure.test :refer :all])
  (:require [rosalind.fasta :refer :all]))

(def test-data 
  '(">Rosalind_6404"
    "CCTGCGGAAGATCGGCACTAGAATAGCCAGAACCGTTTCTCTGAGGCTTCCGGCCTTCCC"
    "; comment here"
    "TCCCACTAATAATTCTGAGG"
    ">Rosalind_5959"
    "; another comment"
    "CCATCGGTAGCGCATCCTTAGTCCAATTAAGTCCCTATCCAGGCGCTCCGCCGAAGGTCT"
    "ATATCCATTTGTCAGCAGACACGC"
    ">Rosalind_0808"
    "CCACCCTCGTGGTATGGCTAGGCATTCAGGAACCGGAGAACGCTTCAGACCAGCCCGGAC"
    "TGGGAACCTGCGGGCAGTAGGTGGAAT"))

(deftest test-parse-fasta
  (testing "yields a lazy seq"
    (is (not (realized? (parse-fasta test-data))))))