(ns climbs.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [monger.joda-time])
  (:use     [monger.command :only [db-stats collection-stats top]]
            [monger.conversion :only [from-db-object]]
            [clj-time.core :only [now]]
            [endjinn.config :only [load-config]]))



(defn db-metrics []
  {:connection-string "foo/bar"
   :status "connected"})

(defn climbs-metrics []
  {:count 0})

