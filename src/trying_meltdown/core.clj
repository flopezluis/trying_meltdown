(ns trying-meltdown.core
  (:gen-class)
  (:use [trying-meltdown.endpoint_source :only [createVcard]])
  (:require [clojurewerkz.meltdown.reactor :as mr]
            [clojurewerkz.meltdown.selectors :as ms :refer [$ R]]
            [clojurewerkz.meltdown.streams :as mstr]))

;;Counters to know how many contacts were sent and received
(def counter (agent 0))
(def received (agent 0))

(def reactor (mr/create :dispatcher-type :ring-buffer))
(def channel (mstr/create))

(defn consume []
  "consumer messages, wait 300 ms"
  (mr/on reactor ($ "key") (fn [event]
     (send received inc)
    (Thread/sleep 300)
    (println "received" @received)
  ))
)

(defn publish [vcard]
  (send counter inc)
  (mr/notify reactor "key" vcard)
  (println "sent" @counter)
)

(defn send_batch []
  (dorun (map publish (map createVcard (take 10000 (iterate inc 1)))))
)

(defn -main
  [& args]
  (.start (Thread. consume))
  (.start (Thread. send_batch))
  (.start (Thread. send_batch))
  (println (read-line))
  (println "Sent " @counter)
  (println "Received" @received)
  )



