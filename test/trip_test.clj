(ns trip-test
  (:require 
    [trip :as subject]
    [date :refer [->date]]
    [expectations :refer [expect]]))

(def trip {:what "place" :start (->date "1/2/2018") :end (->date "3/4/2018")})

(expect [trip]
        (subject/add-trip [] ["place" "1/2" "3/4"]))

(expect "place     1 Feb - 3 Apr"
        (subject/format-trip trip)) 

;; Round trip to and from edn format
(expect trip
        (subject/->trip (subject/->edn trip)))
