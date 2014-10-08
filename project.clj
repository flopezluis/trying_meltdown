(defproject trying_meltdown "0.1.0-SNAPSHOT"
  :description "pet project to learn clojure and meltdown"
  :url "http://www.shuttlecloud.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"], [clojurewerkz/meltdown "1.0.0"], [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
  :main ^:skip-aot trying-meltdown.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
