(ns candlesticks.core
  (:require [candlesticks.date :refer [->date ->duration now add]]
            [candlesticks.chart :as chart]
            [candlesticks.trip :as trip]
            [clojure.java.io :as io]))

(defn draw-chart
  [trips args]
  (->> trips
       (chart/chart 117 (now) (add (now) (->duration 9 :month)))))

(defn add-trip
  [trips args]
  (-> trips
      (trip/add-trip args)
      (trip/save-trips)))

(defn delete-trip
  [trips args]
  (-> trips
      (trip/delete-trip args)
      (trip/save-trips)))

(defn shift-trip
  [trips [pattern days]]
  (-> trips
      (trip/shift-trip [pattern (read-string days)])
      (trip/save-trips)))

(defn list-trips
  [trips args]
  (->> trips
       (map trip/format-trip)
       (clojure.string/join "\n")))

(def actions
  {:draw   draw-chart
   :add    add-trip
   :delete delete-trip
   :shift  shift-trip
   :list   list-trips})

(defn -main [action & args]
  (let [trips (trip/load-trips)]
    (some-> action
            keyword
            actions
            (apply [trips args])
            println)))

