(ns candlestick
  (:require [date :refer [->date ->duration now add]]
            [clojure.java.io :as io]
            [chart :as chart]
            [trip :as trip]))

(def actions
  {:draw (fn [args](->> (trip/load-trips)
                        (chart/chart 100 (now) (add (now) (->duration 8 :month)))))
   :add  (fn [args] (-> (trip/load-trips)
                        (trip/add-trip args)
                        (trip/save-trips)))
   :list (fn [args] (clojure.string/join "\n" (map trip/format-trip (trip/load-trips))))})


(defn -main [action & args]
  (let [func (get actions (keyword action))
        result (func args)]
    (when result (println result))))

