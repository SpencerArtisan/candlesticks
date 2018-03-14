(ns date-test
  (:require 
    [date :as subject]
    [expectations :refer [expect]]))

(def feb1 (subject/date "1/2"))

(expect "1 Feb"
        (subject/->str feb1 :short))

(expect "01/02/2018"
        (subject/->str feb1 :long))
