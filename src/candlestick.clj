(ns candlestick
  (:require [clj-time.core :as t]
            [clj-time.format :as f]
            [clojure.tools.cli :refer [parse-opts]]))


(defn day-str
  "Returns a string representation of a datetime in the local time zone."
  [dt]
  (f/unparse
    (f/with-zone (f/formatter "dd MMM") (t/default-time-zone))
    dt))


(defn today-str
  []
  (day-str (t/now)))


(def itinerary "
Townsville │█
Cornwall   │  ─██──
Sicily     │        ─███──
           ┼─────┬─────┬─────┬─
             Mar   Apr   May")
   
;(def cli-options
  ;;; An option with a required argument
  ;[["-" "--port PORT" "Port number"
    ;:default 80
    ;:parse-fn #(Integer/parseInt %)
    ;:validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ;["-h" "--help"]])

(def actions
  {:draw #(println itinerary)
   :add  #(println "Added trip")})

(defn -main [action]
  (let [func (get actions (keyword action))]
    (func)))

  ;(parse-opts args cli-options))
