(ns candlesticks.date-test
  (:require 
    [candlesticks.date :as subject]
    [expectations :refer [expect]]))

(def jan31 (subject/create "31/01/2018"))
(def feb1 (subject/create "1/2"))
(def feb2 (subject/create "2/2"))
(def feb3 (subject/create "3/2"))
(def a-day (subject/duration 1 ::subject/day))
(def two-days (subject/duration 2 ::subject/day))
(def third-of-a-day (subject/divide a-day 3))

(expect "Jan"
        (subject/->str jan31 ::subject/month))

(expect "Feb"
        (subject/->str feb1 ::subject/month))

(expect "31 Jan"
        (subject/->str jan31 ::subject/short))

(expect "1 Feb"
        (subject/->str feb1 ::subject/short))

(expect "31/01/2018"
        (subject/->str jan31 ::subject/long))

(expect "01/02/2018"
        (subject/->str feb1 ::subject/long))

(expect feb2
        (subject/add feb1 a-day))

(expect feb3
        (subject/add (subject/add feb1 a-day) a-day))

(expect a-day
        (subject/divide two-days 2))

(expect true
        (subject/between? feb1 feb1 feb3))

(expect true
        (subject/between? feb3 feb1 feb3))

(expect true
        (subject/between? feb2 feb1 feb3))

(expect false
        (subject/between? feb1 feb2 feb3))

(expect false
        (subject/between? feb3 feb1 feb2))

(expect 31
        (subject/day-of-month jan31))

(expect 1
        (subject/day-of-month feb1))
