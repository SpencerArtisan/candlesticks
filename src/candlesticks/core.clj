(ns candlesticks.core
  (:require [candlesticks.date :refer [->date ->duration now add]]
            [candlesticks.chart :as chart]
            [candlesticks.trip :as trip]
            [clojure.java.io :as io]))

(defn with-trips
  [trip-mutator]
  (some-> (trip/load-trips)
          (trip-mutator)
          (trip/save-trips)))

(defn draw-chart
  [args]
  (let [start (now)
        end   (add start (->duration 9 :month))]
    (with-trips (comp println (partial chart/colour-chart 117 start end)))))

(defn add-trip
  [args]
  (with-trips #(trip/add-trip % args)))

(defn delete-trip
  [args]
  (with-trips #(trip/delete-trip % args)))

(defn shift-trip
  [[pattern days]]
  (with-trips #(trip/shift-trip % [pattern (read-string days)])))

(defn list-trips
  [args]
  (with-trips (comp println trip/format-trips)))

(def actions
  {:draw   draw-chart
   :add    (juxt add-trip draw-chart)
   :delete (juxt delete-trip draw-chart)
   :shift  (juxt shift-trip draw-chart)
   :list   list-trips})

(defn -main [action & args]
  (-> action
      keyword
      actions
      (apply [args])))

