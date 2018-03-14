(ns trip-test
  (:require 
    [trip :as subject]
    [date :refer [->date]]
    [expectations :refer [expect]]))

(def trip {:what "place" :start (->date "1/2/2018") :end (->date "3/4/2018")})
(def undated-trip {:what "place"})

(expect []
        (subject/delete-trip [trip] ["place"]))

(expect []
        (subject/delete-trip [trip] ["pl"]))

(expect [trip]
        (subject/delete-trip [trip] ["wibble"]))

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
