(ns candlestick
  (:require [date :refer [->date ->duration now add]]
            [clojure.java.io :as io]
            [chart :as chart]
            [trip :as trip]))

(def actions
  {:draw (fn [args](->> (trip/load-trips)
                        (chart/chart 116 (now) (add (now) (->duration 9 :month)))))
   :add  (fn [args] (-> (trip/load-trips)
                        (trip/add-trip args)
                        (trip/save-trips)))
   :list (fn [args] (->> (trip/load-trips)
                         (map trip/format-trip)
                         (clojure.string/join "\n")))})


(defn -main [action & args]
  (let [func (get actions (keyword action))
        result (func args)]
    (when result (println result))))

