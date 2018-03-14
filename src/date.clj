(ns date
  (:require
    [java-time :as jt]))

(def formats
  {:short "d MMM"
   :long "dd/MM/yyyy"})

(defrecord Date [java-date-time])

(defn date
  [text]
  (->Date (jt/local-date "d/M/yy" (str text "/18")))) 

(defn ->str
  [date format]
  (let [format-string (format formats)]
    (jt/format format-string (:java-date-time date))))

