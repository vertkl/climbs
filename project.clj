(defproject climbs "0.1.0-SNAPSHOT"
  :description "An api server for climbing routes"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.5.1"]
                           [com.novemberain/monger "1.4.1"]
                           [cheshire "5.0.0"]
                           [clj-time "0.4.4"]
                           [midje "1.4.0"]
                           [clj-yaml "0.4.0"]
                           [metrics-clojure "0.9.2"]
                           [clj-http "0.6.3"]
                           [compojure "1.1.1"]
                           [ring-mock "0.1.3"]
                       ;;    [org.clojure/tools.logging "0.2.6"]
                       ;;    [clj-logging-config "1.9.11-SNAPSHOT"]
                           [ring/ring-json "0.1.2"]
                           [ring/ring-jetty-adapter "1.1.7" :exclusions [org.slf4j/slf4j-nop
                                                                         org.slf4j/slf4j-log4j12]]
                           [endjinn "0.1.0-SNAPSHOT"]                 
                           ]
            :ring {:handler climbs.server/app}
            :plugins [[lein-midje "2.0.1"]
                      [lein-ring "0.7.3"]]
            :main climbs.server
)
