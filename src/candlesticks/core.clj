(ns candlesticks.core
  (:require [candlesticks.date :refer [->date ->duration now add]]
            [candlesticks.chart :as chart]
            [candlesticks.trip :as trip]
            [clojure.java.io :as io]))

(defn draw-chart
  [args]
  (->> (trip/load-trips)
       (chart/chart 117 (now) (add (now) (->duration 9 :month)))
       println))

(defn add-trip
  [args]
  (-> (trip/load-trips)
      (trip/add-trip args)
      (trip/save-trips)))

(defn delete-trip
  [args]
  (-> (trip/load-trips)
      (trip/delete-trip args)
      (trip/save-trips)))

(defn shift-trip
  [[pattern days]]
  (-> (trip/load-trips)
      (trip/shift-trip [pattern (read-string days)])
      (trip/save-trips)))

(defn list-trips
  [args]
  (->> (trip/load-trips)
       (map trip/format-trip)
       (clojure.string/join "\n")
       println))

(def actions
  {:draw   draw-chart
   :add    (juxt add-trip draw-chart)
   :delete (juxt delete-trip draw-chart)
   :shift  (juxt shift-trip draw-chart)
   :list   list-trips})

(defn -main [action & args]
  (some-> action
          keyword
          actions
          (apply [args])))

