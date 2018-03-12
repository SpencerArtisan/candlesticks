(ns candlestick-test
  (:require 
    [candlestick :as c]
    [expectations :refer :all]))

(expect "12 Mar" (c/today-str))
