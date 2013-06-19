(ns rosalind.dbpr
  (:use clojure.data.zip.xml)
  (:require [clojure.string :as str]
            [clojure.xml :as xml]
            [clojure.zip :as zip]))

;;
;; http://rosalind.info/problems/dbpr/
;;

;; read from url

(def uniprot-base-url "http://www.uniprot.org/uniprot/")

(defn uniprot-xml-url [protein-id]
  (str uniprot-base-url protein-id ".xml"))

;; parse string to xml zip data structure

(defn parse-str-to-xml-zip [s]
  (->> s
       (new java.io.StringReader)
       (new org.xml.sax.InputSource)
       (xml/parse)
       (zip/xml-zip)))

;; walk the xml tree

(defn extract-db-references [xml-zip]
  (xml-> xml-zip
         :entry
           :dbReference [(attr= :type "GO")]
             :property [(attr= :type "term")]
               (attr :value)))

;; additional data manipulation

(defn only-protein-processes [db-reference]
  (.startsWith db-reference "P"))

(defn simple-name [db-reference]
  ((str/split db-reference #"[:]") 1))

;; combinator

(defn dbpr-result [protein-id]
  (->> protein-id
       (uniprot-xml-url)
       (slurp)
       (parse-str-to-xml-zip)
       (extract-db-references)
       (filter only-protein-processes)
       (map simple-name)
       (sort)))

;; 3 - 2 - 1 - launch

(spit "resources/rosalind_dbpr_out.txt"
      (str/join "\n" (dbpr-result "Q824M5")))