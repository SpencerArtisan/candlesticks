(ns date
  (:require
    [java-time :as jt]))

(def formats
  {:short "d MMM"
   :long "dd/MM/yyyy"})

(defrecord Date [java-date-time])

(defn ->date
  [text]
  (let [is-year-missing (re-find #"^[\d]*/[\d]*$" text)
        text (if is-year-missing (str text "/2018") text)]
    (->Date (jt/local-date "d/M/yyyy" text)))) 

(defn ->str
  [date format]
  (let [format-string (format formats)]
    (jt/format format-string (:java-date-time date))))

