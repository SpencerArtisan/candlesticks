(ns candlesticks.trip-test
  (:require 
    [candlesticks.trip :as subject]
    [candlesticks.date :refer [->date]]
    [expectations :refer [expect]]))

(def trip {:what "place" :start (->date "1/2/2018") :end (->date "3/4/2018")})
(def jan-trip {:what "place" :start (->date "1/1") :end (->date "20/1")})
(def feb-trip {:what "place" :start (->date "1/2") :end (->date "20/2")})
(def undated-trip {:what "place"})

(expect []
        (subject/delete-trip [trip] ["place"]))

(expect []
        (subject/delete-trip [trip] ["pl"]))

(expect [trip]
        (subject/delete-trip [trip] ["wibble"]))

(expect [feb-trip]
        (subject/shift-trip [jan-trip] ["place" 31]))

(expect [jan-trip]
        (subject/shift-trip [feb-trip] ["place" -31]))

(expect [trip]
        (subject/shift-trip [trip] ["wibble" 31]))

(expect [undated-trip]
        (subject/add-trip [] ["place"]))

(expect [trip]
        (subject/add-trip [] ["place" "1/2" "3/4"]))

(expect "place"
        (subject/format-trip undated-trip)) 

(expect "place         1 Feb - 3 Apr"
        (subject/format-trip trip)) 

;; Round trip to and from edn format
(expect trip
        (subject/->trip (subject/->edn trip)))

(expect undated-trip
        (subject/->trip (subject/->edn undated-trip)))

(expect [jan-trip feb-trip]
        (subject/sort-trips [feb-trip jan-trip]))

(expect [undated-trip jan-trip feb-trip]
        (subject/sort-trips [feb-trip undated-trip jan-trip]))