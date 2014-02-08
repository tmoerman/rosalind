(ns rosalind.fib)

(def n 30)
(def k 3)

(defn fib [a b] (cons a (lazy-seq (fib b (+ b (* k a))))))

(->>
  (fib 1 1)
  (take n)
  (last)
  (prn))
