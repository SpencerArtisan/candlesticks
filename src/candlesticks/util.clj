(ns candlesticks.util
  (:require [clojure.spec.alpha :as s]))

(defn colour
  [code text]
  (str "\033[" code "m" text "\033[0m"))

(defn replace-at
  [index text replacement]
  (let [end-index (+ index (count replacement))
        text (if (< (count text) index) (str text "  ") text)]
    (if (< index (count text))
      (str (subs text 0 index) replacement
        (if (< end-index (count text)) (subs text end-index)))
      text)))
      
(s/fdef replace-at
        :args (s/and (s/cat :index nat-int? :text string? :replacement string?)
                     (s/or :nil-in  #(nil? (:text %))
                           :not-nil #(< (:index %) (count (:text %)))))
        :ret (s/nilable string?) 
        :fn (s/or
              ;:nil-in  #(nil? (:text %)) 
              :not-nil #(>= (-> :ret count) (-> % :args :text count))))

;(s/exercise-fn `replace-at)
;(stest/instrument `replace-at)
;(stest/summarize-results (stest/check `replace-at))

