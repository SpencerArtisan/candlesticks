(ns candlestick-test
  (:require 
    [candlestick :as c]
    [expectations :refer [expect]]
    [java-time :refer [local-date]]))

(def trip {:what "place" :start (local-date 2018 2 1) :end (local-date 2018 4 3)})

(expect [trip]
        (c/add-trip [] ["place" "1/2" "3/4"]))

(expect "place     1 Feb - 3 Apr"
        (c/format-trip trip)) 

(expect trip
        (c/->trip (c/->edn trip)))
