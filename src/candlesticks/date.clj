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

(defrecord Date [java-date-time])

(defrecord Duration [hours])

(defn ->date
  [text]
  (let [is-year-missing (re-find #"^[\d]*/[\d]*$" text)
        text (if is-year-missing (str text "/2018") text)
        text (str text " 00:00:00")]
    (try
      (->Date (jt/local-date-time "d/M/yyyy HH:mm:ss" text))
      (catch Exception e (throw (Exception. "Invalid date format. Use dd/MM or dd/MM/yyyy."))))))

(defn now
  []
  (->Date (jt/local-date-time)))

(defn ->str
  [date format]
  (let [format-string (format formats)]
    (jt/format format-string (:java-date-time date))))

(defn ->duration
  [n unit]
  (->Duration (* n (unit hours-per-unit))))

(defn duration
  [start end]
  (->Duration 
    (jt/time-between (:java-date-time start) (:java-date-time end) :hours)))

(defn add
  [start period]
  (->Date (jt/plus (:java-date-time start) (jt/hours (:hours period)))))

(defn divide
  [period divisor]
  (->Duration (/ (:hours period) divisor)))

(defn before?
  [date other]
  (jt/before? (:java-date-time date) (:java-date-time other)))

(defn after?
  [date other]
  (jt/after? (:java-date-time date) (:java-date-time other)))

(defn between?
  [date start end]
  (not (or (before? date start) (after? date end))))

(defn day-of-month
  [date]
  (jt/as (:java-date-time date) :day-of-month))

