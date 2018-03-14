(ns trip
  (:require [date :refer [->date ->str]]
            [clojure.java.io :as io]))

(defn add-trip
  [trips [what start end]]
  (conj trips {:what what :start (->date start) :end (->date end)}))

(defn format-trip
  [{:keys [what start end]}]
  (format "%-18s%s - %s" what (->str start :short) (->str end :short)))

(defn ->edn
  [{:keys [what start end]}]
  [what (->str start :long) (->str end :long)]) 

(defn ->trip
  [[what start end]]
  {:what what :start (->date start) :end (->date end)})

(defn load-trips
  []
  (if (.exists (io/file "trips.edn"))
    (map ->trip (read-string (slurp "trips.edn")))
    []))

(defn save-trips
  [trips]
  (spit "trips.edn" (pr-str (map ->edn trips))))
