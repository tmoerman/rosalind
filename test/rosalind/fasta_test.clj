(ns rosalind.fasta-test
  (:use clojure.test)
  (:use rosalind.fasta))

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
    (is (not (realized? (parse-fasta test-data)))))
  (testing "parses 3 fasta maps"
    (is (= 3 (count (parse-fasta test-data))))))
