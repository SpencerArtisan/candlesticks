(ns date-test
  (:require 
    [date :as subject]
    [expectations :refer [expect]]))

(def jan31 (subject/->date "31/01/2018"))
(def feb1 (subject/->date "1/2"))
(def feb2 (subject/->date "2/2"))
(def a-day (subject/duration feb1 feb2))

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

(expect (subject/add feb2 a-day)
        (subject/add (subject/add feb1 a-day) a-day))
