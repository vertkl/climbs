(ns climbs.views
  (:use [ring.util.response]
        [endjinn.web])
  (:require [climbs.db :as db]
            [climbs.query :as query]))

(defn index [request]
  (response (array-map :is ["climbs" "index"]
                       :home (local-url request)
                       :metrics (local-url request "/metrics")
                       :climbs (local-url request "/climbs"))))

(defn ping [request]  
  (response (array-map :is "pong" 
                       :home (local-url request))))

(defn get-metrics-index [request]
  (response (array-map :is ["climbs" "metrics" "index"]
                       :db (local-url request "/metrics/db")
                       :home (local-url request))))

(defn get-db-metrics [request]
  (response {:db (db/db-metrics)
             :climbs (db/climbs-metrics)
             :home (local-url request)}))

(defn get-climbs [request]
  (response (array-map :is ["climb" "list"]
                       :list ["climb1" "climb2"]
                       :home (local-url request))))

(defn post-climbs [request]
  (let [body (get-in request [:body])
        tags  (apply hash-set (map keyword (get-in request [:body (name :is)])))]
    (println tags)
    (println (get-in body [(name :UID)]))
    (if (query/climb-query? tags)
      (header 
       (response (query/climb-query tags body))
       "Location" (local-url request (format "/climbs?q=climb&qv=%s" (body (name :UID)))))
      (response {
                 :is "error"
                 :message (str "I didn't understand your query: " ((apply (fn [x] (str (name x) " ")) tags)))}))))

(defn get-climb [request]
  (response {:is "climb"
             :home (local-url request)}))

(defn post-climb [request]
  (response {
             :is "climb"
             :home (local-url request)}))

(defn request-printer [handler]
  (fn [request]
    (println (format "INCOMING REQUEST: %s" request))
    (handler request)))
