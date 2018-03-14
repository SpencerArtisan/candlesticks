(ns chart
  (:require
    [java-time :as jt]))


(def itinerary "
Townsville │█
Cornwall   │  ─██──
Sicily     │        ─███──
           ┼─────┬─────┬─────┬─
             Mar   Apr   May")

(defn- date-range
  [quantity start end]
  (let [days-between (jt/time-between (jt/local-date-time start) (jt/local-date-time end) :hours)
        unit-days    (/ days-between quantity)
        build-range (fn [q date res]
                      (if (neg? q)
                        res
                        (recur (- q 1) (jt/plus date (jt/hours unit-days)) (conj res date))))]
    (build-range quantity (jt/local-date-time start) [])))

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
  (clojure.string/join "\n" ["" (x-axis-row width) (date-row width start end) (month-row width start end)]))
  
