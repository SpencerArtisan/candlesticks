(ns trip
  (:require [java-time :as time]
            [clojure.java.io :as io]))

(defn add-trip
  [trips [what start end]]
  (letfn [(->date [text] (time/local-date "d/M/yy" (str text "/18")))] 
    (conj trips {:what what :start (->date start) :end (->date end)})))

(defn format-trip
  [{:keys [what start end]}]
  (letfn [(->str [date] (time/format "d MMM" date))]
    (str (format "%-10s" what) (->str start) " - " (->str end))))

(defn ->edn
  [{:keys [what start end]}]
  (letfn [(->str [date] (time/format "dd/MM/yyyy" date))]
    [what (->str start) (->str end)])) 

(defn ->trip
  [[what start end]]
  (letfn [(->date [text] (time/local-date "dd/MM/yyyy" text))]
    {:what what :start (->date start) :end (->date end)}))

(defn load-trips
  []
  (if (.exists (io/file "trips.edn"))
    (map ->trip (read-string (slurp "trips.edn")))
    []))
