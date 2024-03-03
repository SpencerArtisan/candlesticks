(ns candlesticks.chart-test
  (:require 
    [candlesticks.chart :as subject]
    [candlesticks.date :as date]
    [candlesticks.trip :as trip]
    [candlesticks.mark :as mark]
    [expectations :refer [expect]]))

(def jan1 (date/create "1/1/2018"))
(def jan31 (date/create "31/1/2018"))
(def feb28 (date/create "28/2/2018"))
(def mar31 (date/create "31/3/2018"))
(def may31 (date/create "31/5/2018"))
(def jun30 (date/create "30/6/2018"))
(def jan-townsville {::trip/what "Townsville" ::trip/start jan1 ::trip/end jan31 ::trip/fixed true})
(def jan-movable {::trip/what "Movable" ::trip/start jan1 ::trip/end jan31 ::trip/fixed false})
(def undated-usa {::trip/what "USA"})
(def day-trip {::trip/what "Day Trip" ::trip/start jan31 ::trip/end jan31 ::trip/fixed true})
(def jun-mark {::mark/start may31 ::mark/end jun30})

(expect "┬──┬──"
        (subject/x-axis-row 6))

(expect "┬──┬──┬──"
        (subject/x-axis-row 9))

(expect "4  14 24 "
        (subject/date-row 9 jan1 jan31))

(expect "7  26 14 "
        (subject/date-row 9 jan1 feb28))

(expect "Jan       "
        (subject/month-row 9 jan1 jan31))

(expect "Jan  Feb  "
        (subject/month-row 9 jan1 feb28))

(expect (str "¦ Townsville ¦             ¦            ")
        (subject/trip-row 40 feb28 may31 [] jan-townsville))

(expect (str "▓▓▓▓¦Movable")
        (subject/trip-row 9 jan1 feb28 [] jan-movable))

(expect (str "████¦Townsville")
        (subject/trip-row 9 jan1 feb28 [] jan-townsville))

(expect (str "    █ Day Trip")
        (subject/trip-row 9 jan1 feb28 [] day-trip))

(expect "< USA >"
        (subject/trip-row 9 jan1 feb28 [] undated-usa))

(expect (str "¦ Townsville ¦             ¦░░░░░░░░░░░░")
        (subject/trip-row 40 mar31 jun30 [jun-mark] jan-townsville))

(expect "
 ┬──┬──┬──
 7  26 14 
 Jan  Feb  "
        (subject/chart 9 jan1 feb28 [] []))

(expect "\n ████¦Townsville
 ┬──┬──┬──
 7  26 14 
 Jan  Feb  "
  (subject/chart 9 jan1 feb28 [] [jan-townsville]))
