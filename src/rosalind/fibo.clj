(ns rosalind.fibo)

(defn fib [n]
  (->> (iterate (fn [[x y]] [y (+ x y)]) [0 1])
       (map second)
       (drop (dec n))
       first))
