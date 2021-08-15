(ns candlesticks.trip
  (:require [candlesticks.date :as date]
            [clojure.java.io :as io]))

(defn add-trip
  [trips [what start end]]
  (let [trip (cond 
               (and start end) {::what what ::start (date/create start) ::end (date/create end) ::fixed false}
               start {::what what ::start (date/create start) ::end (date/create start) ::fixed false}
               :else {::what what})]
    (conj trips trip)))

(defn starts-with
  [trip pattern]
  (clojure.string/starts-with? (::what trip) pattern))

(defn delete-trip
  [trips [pattern]]
  (filter (complement #(starts-with % pattern)) trips))

(defn modify-trip
  [trips pattern modifier]
  (map #(if (starts-with % pattern) (modifier %) %) trips))

(defn extend-trip
  [trips [pattern days]]
  (modify-trip trips 
               pattern 
               (fn [trip] (update trip ::end #(date/add-days % days))))) 

(defn shift-trip
  [trips [pattern days]]
  (modify-trip trips 
               pattern 
               (fn [trip] (-> trip
                              (update ::start #(date/add-days % days)) 
                              (update ::end #(date/add-days % days)))))) 

(defn fix-trip
  [trips [pattern]]
  (modify-trip trips 
               pattern 
               (fn [trip] (assoc trip ::fixed true))))

(defn format-trip
  [{:keys [::what ::start ::end]}]
  (if (and start end) 
    (format "%-14s%s - %s" what (date/->str start ::date/short) (date/->str end ::date/short))
    what))

(defn format-trips
  [trips]
  (clojure.string/join "\n" (map format-trip trips)))

(defn ->edn
  [{:keys [::what ::start ::end ::fixed]}]
  (if (and start end) 
    [what (date/->str start ::date/long) (date/->str end ::date/long) fixed] 
    [what]))

(defn ->trip
  [[what start end fixed]]
  (if (and start end) 
    {::what what ::start (date/create start) ::end (date/create end) ::fixed (true? fixed)}
    {::what what}))

(defn sort-trips
  [trips]
  (sort-by ::start trips))

(defn load-trips
  []
  (if (.exists (io/file "trips.edn"))
    (->> "trips.edn"
         slurp
         read-string
         (map ->trip)
         sort-trips)
    []))

(defn save-trips
  [trips]
  (spit "trips.edn" (pr-str (map ->edn trips))))
