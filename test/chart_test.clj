(ns chart-test
  (:require 
    [chart :as subject]
    [expectations :refer [expect]]
    [java-time :refer [local-date]]))

(def jan1 (local-date 2018 1 1))
(def jan31 (local-date 2018 1 31))
(def feb28 (local-date 2018 2 28))
(def jan-townsville {:what "Townsville" :start jan1 :end jan31})

(expect " ┼──┬─"
        (subject/x-axis-row 6))

(expect " ┼──┬──┬─"
        (subject/x-axis-row 9))

(expect " 4  14 24"
        (subject/date-row 9 jan1 jan31))

(expect " 7  27 15"
        (subject/date-row 9 jan1 feb28))

(expect " Jan       "
        (subject/month-row 9 jan1 jan31))

(expect " Jan  Feb  "
        (subject/month-row 9 jan1 feb28))

(expect "Townsville    │█████     "
        (subject/trip-row 9 jan1 feb28 jan-townsville))

(expect "
              ┼──┬──┬─
              7  27 15
              Jan  Feb  "
        (subject/chart 9 jan1 feb28 []))

(expect "
Townsville    │█████     
              ┼──┬──┬─
              7  27 15
              Jan  Feb  "
        (subject/chart 9 jan1 feb28 [jan-townsville]))


