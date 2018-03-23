(ns candlesticks.util-test
  (:require 
    [candlesticks.util :as subject]
    [expectations :refer [expect]]
    [clojure.spec.test.alpha :as stest]))


(expect "ABClo world"
        (subject/replace-at 0 "hello world" "ABC"))

(expect "hellABCorld"
        (subject/replace-at 4 "hello world" "ABC"))

(expect "hello woABC"
        (subject/replace-at 8 "hello world" "ABC"))

(expect "hello worlABC"
        (subject/replace-at 10 "hello world" "ABC"))

(expect "hello world ABC"
        (subject/replace-at 12 "hello world" "ABC"))

(-> (stest/enumerate-namespace 'candlesticks.util) stest/check)
