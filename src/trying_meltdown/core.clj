(ns trying-meltdown.core
  (:gen-class)
  (:use [trying-meltdown.endpoint_source :only [createVcard]])
  (:require [clojurewerkz.meltdown.reactor :as mr]
            [clojurewerkz.meltdown.selectors :as ms :refer [$ R]]
            [clojurewerkz.meltdown.streams :as mstr]))

;;Counters to tet how many contacts were sent and received
(def counter (agent 0))
(def received (agent 0))

(def channel (mstr/create))

(defn publish [data]
  "send data to the channel"
  (send counter inc)
  (mstr/accept channel data)
  (println "Sent " @counter))

(defn consume []
  "consumer messages in batches of 10 and wait 300 ms"
  (let [one-batch (mstr/batch* 10 channel)]
      (mstr/consume one-batch (fn [i]
                                (send received inc)
                                ( Thread/sleep 300)
                                ))))

(defn send_batch []
  (dorun (map publish (map createVcard (take  1000 (iterate inc 1)))))
)

(defn -main
  [& args]
  (def reactor (mr/create :dispatcher-type :ring-buffer))
  (.start (Thread. consume))
  (.start (Thread. send_batch))
  (.start (Thread. send_batch))
  (println (read-line))
  (println "Sent " @counter)
  (println "Received" (* 10 @received))
  )



