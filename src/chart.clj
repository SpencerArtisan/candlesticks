(ns chart
  (:require
    [java-time :as jt]))

(defn- date-range
  [quantity start end]
  (let [days-between (jt/time-between (jt/local-date-time start) (jt/local-date-time end) :hours)
        unit-days    (/ days-between quantity)
        build-range (fn [q date res]
                      (if (neg? q)
                        res
                        (recur (- q 1) (jt/plus date (jt/hours unit-days)) (conj res date))))]
    (build-range quantity (jt/local-date-time start) [])))

(defn trip-row
  [width start end {what :what trip-start :start trip-end :end}]
  (let [dates (date-range width start end)
        in-trip (map #(not (or (jt/before? % (jt/local-date-time trip-start)) (jt/after? % (jt/local-date-time trip-end)))) dates)
        bars (map #(if % "█" " ") in-trip)]
    (apply str (format "%-14s" what) "│" bars)))

(defn x-axis-row
  [width]
  (str " ┼─" (apply str (repeat (dec (/ width 3))  "─┬─"))))

(defn date-row
  [width start end]
  (let [dates (date-range width start end)
        days  (map #(jt/as % :day-of-month) dates)
        partitioned-days (partition 3 days)
        labels (map #(format " %-2d" (second %)) partitioned-days)]
    (apply str labels)))

(defn month-row
  [width start end]
  (let [dates (date-range width start end)
        month-names (map #(jt/format "MMM" %) dates) 
        month-groups (partition-by identity month-names)
        labels (map #(format (str "%-" (count %) "s") (first %)) month-groups)] 
    (apply str " " labels)))
    
(defn chart
  [width start end trips]
  (let [trip-rows (map (partial trip-row width start end) trips)
        axis [(x-axis-row width) (date-row width start end) (month-row width start end)]
        indented-axis (map (partial str "             ") axis)
        all-rows (flatten ["" trip-rows indented-axis])]
    (clojure.string/join "\n" all-rows)))
  
