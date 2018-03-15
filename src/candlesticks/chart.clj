(ns candlesticks.chart
  (:require
    [candlesticks.date :refer [duration divide add between? ->str day-of-month]]))

(defn colour
  [code text]
  (str "\033[" code "m" text "\033[0m"))

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
  (if (and trip-start trip-end)
    (let [dates (date-range width start end)
          in-trip? #(between? % trip-start trip-end)
          in-trip (map in-trip? dates)
          bar (map #(if % "█" " ") (drop 1 in-trip))
          trimmed-bar (clojure.string/trimr (apply str bar))] 
      (str " │" (colour 34 trimmed-bar) " " (colour 36 what)))
    (colour 36 (str "< " what " >"))))

(defn x-axis-row
  [width]
  (apply str " ┼──" (repeat (dec (/ width 3))  "┬──")))

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
        all-rows (flatten ["" trip-rows axis])]
    (clojure.string/join "\n" all-rows)))
  
