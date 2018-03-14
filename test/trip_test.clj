(ns trip-test
  (:require 
    [trip :as subject]
    [expectations :refer [expect]]
    [java-time :refer [local-date]]))

(def trip {:what "place" :start (local-date 2018 2 1) :end (local-date 2018 4 3)})

(expect [trip]
        (subject/add-trip [] ["place" "1/2" "3/4"]))

(expect "place     1 Feb - 3 Apr"
        (subject/format-trip trip)) 

;; Round trip to and from edn format
(expect trip
        (subject/->trip (subject/->edn trip)))
