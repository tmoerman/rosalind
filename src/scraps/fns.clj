(ns scraps.fns)

;;
;; Applying a list of functions to an argument
;;

(defn translate [x] (+ x 1))
(defn square [x] (* x x))
(defn double [x] (+ x x))

;; (def fns [translate square double])

(def fns (list translate square double))

(map prn fns)

;; optie 1
(map #(% 2) fns)

;; optie 2
((apply juxt fns) 2)