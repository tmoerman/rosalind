(ns scraps.rock
  (:require [clojure.core.async :refer [go timeout alts! chan <! >! put! <!!] :as async]
            [org.httpkit.client :as http]))

(def MOVES [:rock :paper :scissors])
(def BEATS {:rock :scissors, :paper :rock, :scissors :paper})

(defn player [name]
  (let [p-chan (chan)]
    (async/go-loop []
      (>! p-chan [name (rand-nth MOVES)])
      (recur))
    p-chan))

(def QUICK  (player "Quick"))
(def FLUPKE (player "Flupke"))

(defn winner [[name1 move1] [name2 move2]]
  (cond
   (= move1 move2) "no one"
   (= move2 (BEATS move1)) name1
   :else name2))

(defn decide [p1 p2]
  (let [out (chan)]
    (async/go-loop []
      (let [move1 (<! p1)
            move2 (<! p2)]
        (>! out (winner move1 move2)))
      (recur))
    out))

(def out (decide QUICK FLUPKE))

(async/go-loop []
  (if-let [w (<! out)]
    (prn w))
  (<! (timeout 500))
  (recur))
