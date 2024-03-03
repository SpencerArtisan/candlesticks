(ns candlesticks.core
  (:require [candlesticks.date :as date]
            [candlesticks.chart :as chart]
            [candlesticks.mark :as mark]
            [candlesticks.trip :as trip]
            [clojure.java.io :as io]))

(declare actions)

(defn with-trips
  [trip-mutator]
  (some-> (trip/load-trips)
          (trip-mutator)
          (trip/save-trips)))

(defn draw-chart
  [& _]
  (let [marks (mark/load-marks)
  		    start (date/now)
        end   (date/add start (date/duration 9 ::date/month))]
    (with-trips (comp println (partial chart/colour-chart 117 start end marks)))))

(defn add-trip
  [args]
  (with-trips #(trip/add-trip % args)))

(defn delete-trip
  [args]
  (with-trips #(trip/delete-trip % args)))

(defn shift-trip
  [[pattern days]]
  (with-trips #(trip/shift-trip % [pattern (read-string days)])))

(defn fix-trip
  [[pattern]]
  (with-trips #(trip/fix-trip % [pattern])))

(defn extend-trip
  [[pattern days]]
  (with-trips #(trip/extend-trip % [pattern (read-string days)])))

(defn list-trips
  [& _]
  (with-trips (comp println trip/format-trips)))

(defn add-mark
  [args]
    (some-> (mark/load-marks)
    		      (#(mark/add-mark % args))
            (mark/save-marks)))
  
(defn describe
  [[_ usage description]] 
  (format "    %-30s%s" usage description))

(defn help 
  [& _]
  (doall (map #(println (describe %)) (vals actions))))

(def actions
  {:draw   [draw-chart                    
            "draw"                      
            "Draws a visual representation of your trips."]
   :add    [(juxt add-trip draw-chart)    
            "add [name] [start] [end]"  
            "Adds a new trip. eg. add USA 1/4 15/4. You may miss off dates for uncertain trips."]
   :rm 	    [(juxt delete-trip draw-chart) 
            "rm [name]"             
            "Removes a trip. You may enter just the start of the name."]
   :extend [(juxt extend-trip draw-chart)  
            "extend [name] [days]"       
            "Increases the length of the trip (or decreases for negatives). You may enter just the start of the name."]
   :shift  [(juxt shift-trip draw-chart)  
            "shift [name] [days]"       
            "Moves the trip into the future (or past for negatives). You may enter just the start of the name."]
   :fix    [(juxt fix-trip draw-chart)  
            "fix [name]"       
            "Flags the trip as having fixed dates. Simply acts as a visual indication on the chart. You may enter just the start of the name."]
   :list   [list-trips                    
            "list"                      
            "Lists all the trips."]
   :mark   [(juxt add-mark draw-chart)    
            "mark [start] [end]"  
            "Adds a shaded area. eg. mark 1/4 15/4."]
   :help   [help                          
            "help"                      
            "Displays this page."]})

(defn -main 
  ([]
   (draw-chart))
  ([action & args]
   (if-let [[func :as action-vector] ((keyword action) actions)]
     (try
       (func (vec args))
       (catch Exception e 
         (do
           (println "Command failed")
           (println (describe action-vector))
           (println (.getMessage e)))))
     (println "Unknown command " action))))

