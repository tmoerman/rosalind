(ns rosalind.grph-test
  (:use clojure.test)
  (:use rosalind.grph))

(deftest test-adjacent?
  (testing "poly-A sequence"
    (is (= (adjacent? 3 "xxxAAA" "AAAxxx"))))
  (testing "mixed sequence"
    (is (= (adjacent? 3 "xxxCTG" "CTGyyy")))))

(deftest test-pairs 
  (testing "correct output size"
    (is (= 12 (count (pairs '(:a :b :c :d)))))))