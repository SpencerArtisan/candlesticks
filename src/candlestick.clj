(ns candlestick
  (:require [date :refer [->date ->duration now add]]
            [clojure.java.io :as io]
            [chart :as chart]
            [trip :as trip]))

(defn draw-chart
  [trips args]
  (->> trips
       (chart/chart 116 (now) (add (now) (->duration 9 :month)))))

(defn add-trip
  [trips args]
  (-> trips
      (trip/add-trip args)
      (trip/save-trips)))

(defn list-trips
  [trips args]
  (->> trips
       (map trip/format-trip)
       (clojure.string/join "\n")))

(def actions
  {:draw draw-chart
   :add  add-trip
   :list list-trips})

(defn -main [action & args]
  (let [trips (trip/load-trips)]
    (some-> action
            keyword
            actions
            (apply [trips args])
            println)))

