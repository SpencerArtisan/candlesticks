(ns date
  (:require
    [java-time :as jt]))

(def formats
  {:short "d MMM"
   :long "dd/MM/yyyy"})

(defrecord Date [java-date-time])

(defrecord Duration [hours])

(defn ->date
  [text]
  (let [is-year-missing (re-find #"^[\d]*/[\d]*$" text)
        text (if is-year-missing (str text "/2018") text)
        text (str text " 00:00:00")]
    (->Date (jt/local-date-time "d/M/yyyy HH:mm:ss" text)))) 

(defn ->str
  [date format]
  (let [format-string (format formats)]
    (jt/format format-string (:java-date-time date))))

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

(defn between?
  [date start end]
  (not (or (jt/before? (:java-date-time date) (:java-date-time start))
           (jt/after? (:java-date-time date) (:java-date-time end)))))
