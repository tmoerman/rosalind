(ns rosalind.mprt
  (:require [clojure.string :as str]
            [clojure.core.async :refer [go chan <! >! put! <!!] :as async]
            [org.httpkit.client :as http]
            [rosalind.fasta :as fasta]))

;;
;; http://rosalind.info/problems/mprt/
;;

;; slurp input

(def ids (str/split (slurp "resources/rosalind_mprt.txt") #"\n"))

;; async http requests

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
       (map :seq)
       (first)))

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

;; request and process logic

(defn request-and-process [id]
  (go
    (let [pos (->> id
                   (fasta-url)
                   (request)
                   <!
                   (fasta-seq)
                   (positions))]
      [id pos])))

(defn request-and-process-all [ids]
  (go (->> ids
           (map request-and-process)
           (async/map vector)
           <!
           (filter (fn [[_ positions]] (not (str/blank? positions)))))))

(defn output-format [tuples]
  (->> tuples
       (flatten)
       (str/join \newline)))

;; launch solution

(->> ids
     (request-and-process-all)
     <!!
     (output-format)
     (spit "resources/rosalind_mprt_out.txt"))

(<!! (request-and-process-all ids))
