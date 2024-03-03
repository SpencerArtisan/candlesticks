(ns candlesticks.mark
  (:require [candlesticks.date :as date]
            [clojure.java.io :as io]))

(defn add-mark
  [marks [start end]]
  (let [mark {::start (date/create start) ::end (date/create end)}]
  (conj marks mark))
)

(defn ->edn
  [{:keys [::start ::end]}]
  [(date/->str start ::date/long) (date/->str end ::date/long)])

(defn ->mark
  [[start end]]
  {::start (date/create start) ::end (date/create end)})

(defn load-marks
  []
  (if (.exists (io/file "marks.edn"))
    (->> "marks.edn"
         slurp
         read-string
         (map ->mark))
    []))

(defn save-marks
  [marks]
  (spit "marks.edn" (pr-str (map ->edn marks))))
