(ns candlesticks.chart
  (:require
    [candlesticks.date :refer [duration divide add between? ->str day-of-month]]))

(defn colour
  [code text]
  (str "\033[" code "m" text "\033[0m"))

(defn replace-at
  [index text replacement]
  (let [end-index (+ index (count replacement))
        text (if (< (count text) index) (str text "  ") text)]
    (str (subs text 0 index) replacement
      (if (< end-index (count text)) (subs text end-index)))))
      
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
      (let [dates (drop-last 4 (rest (date-range width start end)))
            ->char (fn 
                     [i _]
                     (cond (between? (nth dates i) trip-start trip-end) "█"
                           (and 
                                (< i (dec (count dates)))
                                (> (day-of-month (nth dates i)) (day-of-month (nth dates (inc i))))) "¦"
                           :else " "))
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

