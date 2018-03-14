(ns chart
  (:require
    [date :refer [duration divide add between? ->str day-of-month]]))

(defn- date-range
  [quantity start end]
  (let [unit-duration (divide (duration start end) quantity)
        build-range (fn [q date res]
                      (if (neg? q)
                        res
                        (recur (- q 1) (add date unit-duration) (conj res date))))]
    (build-range quantity start [])))

(defn trip-row
  [width start end {what :what trip-start :start trip-end :end}]
  (let [dates (date-range width start end)
        in-trip (map #(between? % trip-start trip-end) dates)
        bars (map #(if % "█" " ") in-trip)]
    (apply str (format "%-14s" what) "│" bars)))

(defn x-axis-row
  [width]
  (str " ┼─" (apply str (repeat (dec (/ width 3))  "─┬─"))))

(defn date-row
  [width start end]
  (let [dates (date-range width start end)
        days  (map day-of-month dates)
        partitioned-days (partition 3 days)
        labels (map #(format " %-2d" (second %)) partitioned-days)]
    (apply str labels)))

(defn month-row
  [width start end]
  (let [dates (date-range width start end)
        month-names (map #(->str % :month) dates) 
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
  
