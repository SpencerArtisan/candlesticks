(ns date-test
  (:require 
    [date :as subject]
    [expectations :refer [expect]]))

(def jan31 (subject/->date "31/01/2018"))
(def feb1 (subject/->date "1/2"))
(def feb2 (subject/->date "2/2"))
(def feb3 (subject/->date "3/2"))
(def a-day (subject/duration feb1 feb2))
(def two-days (subject/duration feb1 feb3))
(def third-of-a-day (subject/divide (subject/duration feb1 feb2) 3))

(expect "Jan"
        (subject/->str jan31 :month))

(expect "Feb"
        (subject/->str feb1 :month))

(expect "31 Jan"
        (subject/->str jan31 :short))

(expect "1 Feb"
        (subject/->str feb1 :short))

(expect "31/01/2018"
        (subject/->str jan31 :long))

(expect "01/02/2018"
        (subject/->str feb1 :long))

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
