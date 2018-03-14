(ns chart
  (:require
    [java-time :refer [after? as minus plus multiply-by days hours time-between local-date-time]]))

(def itinerary "
Townsville │█
Cornwall   │  ─██──
Sicily     │        ─███──
           ┼─────┬─────┬─────┬─
             Mar   Apr   May")

(defn- date-range
  [quantity start end]
  (let [days-between (time-between (local-date-time start) (local-date-time end) :hours)
        unit-days    (/ days-between quantity)
        build-range (fn [q date res]
                      (if (neg? q)
                        res
                        (recur (- q 1) (plus date (hours unit-days)) (conj res date))))]
    (build-range quantity (local-date-time start) [])))

(defn x-axis-row
  [width]
  (str " ┼─" (apply str (repeat (dec (/ width 3))  "─┬─"))))

(defn date-row
  [width start end]
  (let [dates (date-range width start end)
        days  (map #(as % :day-of-month) dates)
        partitioned-days (partition 3 days)
        labels (map #(format " %-2d" (second %)) partitioned-days)]
    (apply str labels)))
