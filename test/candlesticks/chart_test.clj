(ns candlesticks.chart-test
  (:require 
    [candlesticks.chart :as subject]
    [candlesticks.date :refer [->date]]
    [expectations :refer [expect]]))

(def jan1 (->date "1/1/2018"))
(def jan31 (->date "31/1/2018"))
(def feb28 (->date "28/2/2018"))
(def may31 (->date "31/5/2018"))
(def jan-townsville {:what "Townsville" :start jan1 :end jan31})
(def undated-usa {:what "USA"})

(expect " ┬──┬──"
        (subject/x-axis-row 6))

(expect " ┬──┬──┬──"
        (subject/x-axis-row 9))

(expect " 4  14 24"
        (subject/date-row 9 jan1 jan31))

(expect " 7  26 14"
        (subject/date-row 9 jan1 feb28))

(expect " Jan       "
        (subject/month-row 9 jan1 jan31))

(expect " Jan  Feb  "
        (subject/month-row 9 jan1 feb28))

(expect (str "   Townsville ¦             ¦         ")
        (subject/trip-row 40 feb28 may31 jan-townsville))

(expect (str "  ████ Townsville")
        (subject/trip-row 9 jan1 feb28 jan-townsville))

(expect "< USA >"
        (subject/trip-row 9 jan1 feb28 undated-usa))

(expect "
 ┬──┬──┬──
 7  26 14
 Jan  Feb  "
        (subject/chart 9 jan1 feb28 []))

(expect "\n  ████ Townsville
 ┬──┬──┬──
 7  26 14
 Jan  Feb  "
  (subject/chart 9 jan1 feb28 [jan-townsville]))

(expect "ABClo world"
        (subject/replace-at 0 "hello world" "ABC"))

(expect "hellABCorld"
        (subject/replace-at 4 "hello world" "ABC"))

(expect "hello woABC"
        (subject/replace-at 8 "hello world" "ABC"))

(expect "hello worlABC"
        (subject/replace-at 10 "hello world" "ABC"))

(expect "hello world ABC"
        (subject/replace-at 12 "hello world" "ABC"))
