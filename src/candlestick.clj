(ns candlestick
  (:require [java-time :as time]
            [clojure.java.io :as io]
            [chart :as chart]
            [trip :as trip]))

(def actions
  {:draw (fn [args] (println (chart/chart 100 (time/local-date 2018 3) (time/local-date 2019) (trip/load-trips))))
   :add  (fn [args] (spit "trips.edn" (pr-str (map trip/->edn (trip/add-trip (trip/load-trips) args))))) 
   :list (fn [args] (println (clojure.string/join "\n" (map trip/format-trip (trip/load-trips)))))})


(defn -main [action & args]
  (let [func (get actions (keyword action))]
    (func args)))

