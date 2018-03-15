(ns candlesticks.chart-test
  (:require 
    [candlesticks.chart :as subject]
    [candlesticks.date :refer [->date]]
    [expectations :refer [expect]]))

(def jan1 (->date "1/1/2018"))
(def jan31 (->date "31/1/2018"))
(def feb28 (->date "28/2/2018"))
(def jan-townsville {:what "Townsville" :start jan1 :end jan31})
(def undated-usa {:what "USA"})

(expect " ┼──┬──"
        (subject/x-axis-row 6))

(expect " ┼──┬──┬──"
        (subject/x-axis-row 9))

(expect " 4  14 24"
        (subject/date-row 9 jan1 jan31))

(expect " 7  26 14"
        (subject/date-row 9 jan1 feb28))

(expect " Jan       "
        (subject/month-row 9 jan1 jan31))

(expect " Jan  Feb  "
        (subject/month-row 9 jan1 feb28))

(expect (str " │" (subject/colour 34 "████") " " (subject/colour 36 "Townsville"))
        (subject/trip-row 9 jan1 feb28 jan-townsville))

(expect (subject/colour 36 "< USA >")
        (subject/trip-row 9 jan1 feb28 undated-usa))

(expect "
 ┼──┬──┬──
 7  26 14
 Jan  Feb  "
        (subject/chart 9 jan1 feb28 []))

(expect 
  (str "\n │" (subject/colour 34 "████") " " (subject/colour 36 "Townsville")
    "
 ┼──┬──┬──
 7  26 14
 Jan  Feb  ")
  (subject/chart 9 jan1 feb28 [jan-townsville]))


