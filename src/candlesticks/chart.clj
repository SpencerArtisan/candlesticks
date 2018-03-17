(ns candlesticks.chart
  (:require
    [candlesticks.date :refer [between divide add between? ->str day-of-month date-range]]
    [candlesticks.util :refer [colour replace-at]]))

(defn trip-row
  [width start end {what :what trip-start :start trip-end :end}]
  (if (and trip-start trip-end)
      (let [dates (drop-last 2 (rest (date-range width start end)))
            last-day-of-month? (fn [i] (> (day-of-month (nth dates i)) (day-of-month (nth dates (inc i)))))
            ->char (fn [i _]
                     (cond (between? (nth dates i) trip-start trip-end)           "█"
                           (and (< i (dec (count dates))) (last-day-of-month? i)) "¦"
                           :else                                                  " "))
            bar (apply str "  " (map-indexed ->char dates))
            bar-end (or (clojure.string/last-index-of bar "█") 1)]
        (replace-at (+ 2 bar-end) bar what))
      (str "< " what " >")))

(defn x-axis-row
  [width]
  (apply str " " (repeat (/ width 3)  "┬──")))

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

(defn colour-chart
  [width start end trips]
  (let [chart (chart width start end trips)
        replacements (concat (map (fn [trip] [(:what trip) 36]) trips)
                             [["█" 34] ["<" 34] [">" 34] ["¦" 37]])]
    (reduce (fn [ch [text col]] (clojure.string/replace ch text (colour col text))) chart replacements)))

