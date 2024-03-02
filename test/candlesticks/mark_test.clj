(ns candlesticks.mark-test
  (:require 
    [candlesticks.mark :as subject]
    [candlesticks.date :as date]
    [expectations :refer [expect]]))

(def mark {::subject/start (date/create "1/2") ::subject/end (date/create "3/4")})

(expect [mark]
        (subject/add-mark [] ["1/2" "3/4"]))

;; Round mark to and from edn format
(expect mark
        (subject/->mark (subject/->edn mark)))
