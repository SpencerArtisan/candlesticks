(ns candlestick
  (:require [java-time :refer [local-date]]))


(def itinerary "
Townsville │█
Cornwall   │  ─██──
Sicily     │        ─███──
           ┼─────┬─────┬─────┬─
             Mar   Apr   May")
  
(defn add-trip
  [trips [what start end]]
  (letfn [(->date [text] (local-date "d/M/yy" (str text "/18")))] 
    (conj trips {:what what :start (->date start) :end (->date end)})))

(def actions
  {:draw #(println itinerary)
   :add  (fn [& args] (add-trip [] args))}) 

(defn -main [action]
  (let [func (get actions (keyword action))]
    (func)))

