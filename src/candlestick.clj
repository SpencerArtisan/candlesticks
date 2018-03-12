(ns candlestick
  (:require [clj-time.core :as t]
            [clj-time.format :as f]))

(defn day-str
  "Returns a string representation of a datetime in the local time zone."
  [dt]
  (f/unparse
    (f/with-zone (f/formatter "dd MMM") (t/default-time-zone))
    dt))


(defn today-str
  []
  (day-str (t/now)))


(def itinerary "
Townsville │█
Cornwall   │  ─██──
Sicily     │        ─███──
           ┼─────┬─────┬─────┬─
             Mar   Apr   May")
   

(defn -main []
  (println itinerary))
