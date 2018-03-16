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
  [& _]
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
  [& _]
  (with-trips (comp println trip/format-trips)))

(declare actions)
(defn help 
  [& _]
  (letfn [(->line [[func usage description]] (format "    %-30s%s" usage description))]
    (doall (map #(println (->line %)) (vals actions)))))

(def actions
  {:draw   [draw-chart                    
            "draw"                      
            "Draws a visual representation of your trips."]
   :add    [(juxt add-trip draw-chart)    
            "add [name] [start] [end]"  
            "Adds a new trip to the map. eg. add USA 1/4 15/4. You may miss off dates for uncertain trips."]
   :delete [(juxt delete-trip draw-chart) 
            "delete [name]"             
            "Deletes a trip. You may enter just the start of the name."]
   :shift  [(juxt shift-trip draw-chart)  
            "shift [name] [days]"       
            "Moves the trip into the future (or past for negatives). You may enter just the start of the name."]
   :list   [list-trips                    
            "list"                      
            "Lists all the trips."]
   :help   [help                          
            "help"                      
            "Displays this page."]})

(defn -main 
  ([]
   (help))
  ([action & args]
   (-> action
       keyword
       actions
       first
       (apply [args]))))

