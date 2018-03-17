(ns candlesticks.date
  (:require
    [java-time :as jt]))

(def formats
  {:short "d MMM"
   :long "dd/MM/yyyy"
   :month "MMM"})

(def hours-per-unit
   {:month (* 24 (/ 365 12))
    :day 24})

(defn now
  []
  (jt/local-date-time))

(defn date
  [text]
  (let [is-year-missing? (re-find #"^[\d]*/[\d]*$" text)
        year (jt/as (now) :year)
        text (if is-year-missing? (str text "/" year) text)
        text (str text " 00:00:00")]
    (try
      (jt/local-date-time "d/M/yyyy HH:mm:ss" text)
      (catch Exception e (throw (Exception. "Invalid date format. Use dd/MM or dd/MM/yyyy."))))))

(defn ->str
  [date format]
  (let [format-string (format formats)]
    (jt/format format-string date)))

(defn duration
  [n unit]
  (jt/hours (* n (unit hours-per-unit))))

(defn between
  [start end]
  (jt/hours (jt/time-between start end :hours)))

(defn add
  [start period]
  (jt/plus start period))

(defn add-days
  [date days]
  (add date (duration days :day)))

(defn divide
  [period divisor]
  (jt/hours (/ (jt/as period :hours) divisor)))

(defn between?
  [date start end]
  (not (or (jt/before? date start) (jt/after? date end))))

(defn day-of-month
  [date]
  (jt/as date :day-of-month))

(defn date-range
  [quantity start end]
  (let [unit-duration (divide (between start end) quantity)]
    (loop [q quantity date start res []]
      (if (neg? q)
        res
        (recur (- q 1) (add date unit-duration) (conj res date))))))
