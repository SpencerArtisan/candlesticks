(ns candlesticks.util)

(defn colour
  [code text]
  (str "\033[" code "m" text "\033[0m"))

(defn replace-at
  [index text replacement]
  (let [end-index (+ index (count replacement))
        text (if (< (count text) index) (str text "  ") text)]
    (str (subs text 0 index) replacement
      (if (< end-index (count text)) (subs text end-index)))))
      
