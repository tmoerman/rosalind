(ns rosalind.mprt
  (:require [clojure.string :as str]
            [clojure.core.async :refer [go chan <! >! put! <!!] :as async]
            [org.httpkit.client :as http]
            [rosalind.fasta :as fasta]))

;; input

(def ids (str/split (slurp "resources/rosalind_mprt.txt") #"\n"))

;; http requests

(defn fasta-url [id] (str "http://www.uniprot.org/uniprot/" id ".fasta"))

(defn http-get [url]
  (let [c (chan)]
    (http/get url (fn [r] (put! c r)))
    c))

(defn request [url]
  (go
    (->> url
         (http-get)
         <!
         :body)))

;; parse fasta

(defn fasta-seq [body]
  (->> (str/split body #"\n")
       (fasta/parse-fasta)
       (first)
       :seq))

;; locating motif positions

(defn re-pos [re s]
  (let [m (re-matcher re s)]
    (loop [res nil]
      (if (.find m)
        (recur (conj res (inc (.start m))))
        (reverse res)))))

(def motif #"(?=([N][^P][ST][^P]))")

(defn positions [s]
  (->> s
      (re-pos motif)
      (str/join " ")))

;; launch solution

(defn get-all-async [ids]
  (go (->> ids
           (map fasta-url)
           (map request)
           (async/map vector)
           <!)))

(->>
  (get-all-async ids)
  <!!
  (map fasta-seq)
  (map positions)
  (map vector ids)
  (filter #((complement str/blank?) (% 1)))
  (flatten)
  (str/join "\n")
  (spit "resources/rosalind_mprt_out.txt"))
