(ns candlesticks.trip
  (:require [candlesticks.date :refer [date ->str add add-days duration]]
            [clojure.java.io :as io]))

(defn add-trip
  [trips [what start end]]
  (let [trip (if (and start end)
               {:what what :start (date start) :end (date end)}
               {:what what})]
    (conj trips trip)))

(defn starts-with
  [trip pattern]
  (clojure.string/starts-with? (:what trip) pattern))

(defn delete-trip
  [trips [pattern]]
  (filter (complement #(starts-with % pattern)) trips))

(defn extend-trip
  [trips [pattern days]]
  (let [extend (fn [trip] (-> trip
                              (update :end #(add-days % days))))] 
    (map #(if (starts-with % pattern) (extend %) %) trips)))

(defn shift-trip
  [trips [pattern days]]
  (let [shift (fn [trip] (-> trip
                             (update :start #(add-days % days)) 
                             (update :end #(add-days % days))))] 
    (map #(if (starts-with % pattern) (shift %) %) trips)))

(defn format-trip
  [{:keys [what start end]}]
  (if (and start end) 
    (format "%-14s%s - %s" what (->str start :short) (->str end :short))
    what))

(defn format-trips
  [trips]
  (clojure.string/join "\n" (map format-trip trips)))

(defn ->edn
  [{:keys [what start end]}]
  (if (and start end) 
    [what (->str start :long) (->str end :long)] 
    [what]))

(defn ->trip
  [[what start end]]
  (if (and start end) 
    {:what what :start (date start) :end (date end)}
    {:what what}))

(defn sort-trips
  [trips]
  (sort-by :start trips))

(defn load-trips
  []
  (if (.exists (io/file "trips.edn"))
    (->> "trips.edn"
         slurp
         read-string
         (map ->trip)
         sort-trips)
    []))

(defn save-trips
  [trips]
  (spit "trips.edn" (pr-str (map ->edn trips))))
