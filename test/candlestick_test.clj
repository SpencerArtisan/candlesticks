(ns candlestick-test
  (:require 
    [candlestick :as c]
    [expectations :refer [expect]]
    [java-time :refer [local-date]]))

(expect [{:what "place" :start (local-date 2018 2 1) :end (local-date 2018 4 3)}]
        (c/add-trip [] ["place" "1/2" "3/4"]))



