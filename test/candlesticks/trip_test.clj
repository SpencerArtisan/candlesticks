(ns candlesticks.trip-test
  (:require 
    [candlesticks.trip :as subject]
    [candlesticks.date :as date]
    [expectations :refer [expect]]))

(def fixed-trip {::subject/what "place" ::subject/start (date/create "1/2") ::subject/end (date/create "3/4") ::subject/fixed true})
(def trip {::subject/what "place" ::subject/start (date/create "1/2") ::subject/end (date/create "3/4") ::subject/fixed false})
(def day-trip {::subject/what "place" ::subject/start (date/create "1/2") ::subject/end (date/create "1/2") ::subject/fixed false})
(def jan-trip {::subject/what "place" ::subject/start (date/create "1/1") ::subject/end (date/create "20/1")})
(def feb-trip {::subject/what "place" ::subject/start (date/create "1/2") ::subject/end (date/create "20/2")})
(def jan-feb-trip {::subject/what "place" ::subject/start (date/create "1/1") ::subject/end (date/create "17/2")})
(def undated-trip {::subject/what "place"})

(expect []
        (subject/delete-trip [trip] ["place"]))

(expect []
        (subject/delete-trip [trip] ["pl"]))

(expect [trip]
        (subject/delete-trip [trip] ["wibble"]))

(expect [jan-feb-trip]
        (subject/extend-trip [jan-trip] ["place" 28]))

(expect [feb-trip]
        (subject/shift-trip [jan-trip] ["place" 31]))

(expect [jan-trip]
        (subject/shift-trip [feb-trip] ["place" -31]))

(expect [trip]
        (subject/shift-trip [trip] ["wibble" 31]))

(expect [fixed-trip]
        (subject/fix-trip [trip] ["place"]))

(expect [undated-trip]
        (subject/add-trip [] ["place"]))

(expect [trip]
        (subject/add-trip [] ["place" "1/2" "3/4"]))

(expect [day-trip]
        (subject/add-trip [] ["place" "1/2"]))

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
