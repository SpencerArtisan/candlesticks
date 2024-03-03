(ns candlesticks.chart
  (:require
    [candlesticks.date :as date]
    [candlesticks.util :refer [colour replace-at]]
    [candlesticks.trip :as trip]
    [clojure.string :refer [last-index-of]]))

(defn trip-row
  [width start end marks {what ::trip/what trip-start ::trip/start trip-end ::trip/end fixed ::trip/fixed}]
  (if (and trip-start trip-end)
      (let [dates (date/date-range width start end)
            ->char (fn [[date next-date]]
                     (cond (or (date/between? next-date trip-start trip-end) (date/between? trip-start date next-date)) 
                               (if fixed "█" "▓")
                           (> (date/day-of-month date) (date/day-of-month next-date)) "¦"
                           :else                                            " "))
            row (apply str (map ->char (partition 2 1 dates)))
            bar-end (or (last-index-of row "█") (last-index-of row "▓") 0)]
        (replace-at (+ 2 bar-end) row what))
      (str "< " what " >")))
;▓


(defn x-axis-row
  [width]
  (apply str (repeat (/ width 3)  "┬──")))

(defn date-row
  [width start end]
  (let [dates (date/date-range width start end)
        days  (map date/day-of-month dates)
        partitioned-days (partition 3 days)
        labels (map #(format "%-2d " (second %)) partitioned-days)]
    (apply str labels)))

(defn month-row
  [width start end]
  (let [dates (date/date-range width start end)
        month-names (map #(date/->str % ::date/month) dates) 
        month-groups (partition-by identity month-names)
        labels (map #(format (str "%-" (count %) "s") (first %)) month-groups)] 
    (apply str labels)))
    
(defn chart
  [width start end marks trips]
  (let [trip-rows (map (partial trip-row width start end marks) trips)
        axis [(x-axis-row width) (date-row width start end) (month-row width start end)]
        all-rows (flatten ["" trip-rows axis])]
    (clojure.string/join "\n " all-rows)))

(defn colour-chart
  [width start end marks trips]
  (let [chart (chart width start end marks trips)
        replacements (concat (map (fn [trip] [(::trip/what trip) 36]) trips)
                             [["█" 31] ["▓" 34] ["<" 34] [">" 34] ["¦" 37]])]
    (reduce (fn [ch [text col]] (clojure.string/replace ch text (colour col text))) chart replacements)))

