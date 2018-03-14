(ns chart-test
  (:require 
    [chart :as subject]
    [expectations :refer [expect]]
    [java-time :refer [local-date]]))

(def jan1 (local-date 2018 1 1))
(def jan31 (local-date 2018 1 31))

(expect " ┼──┬─"
        (subject/x-axis-row 6))

(expect " ┼──┬──┬─"
        (subject/x-axis-row 9))

(expect " 4  14 24"
        (subject/date-row 9 jan1 jan31))


