(ns climbs.views
  (:use [ring.util.response]
        [endjinn.web])
  (:require [climbs.db :as db]
            [climbs.query :as query]))

(defn index [request]
  (response (array-map :is ["climbing" "index"]
                       :home (local-url request)
                       :metrics (local-url request "/metrics")
                       :climbs (local-url request "/climbs"))))


(defn ping [request]  
  (response (array-map :is "pong" 
                       :home (local-url request))))

(defn get-metrics-index [request]
  (response (array-map :is ["climbs" "metrics" "index"]
                       :db (local-url request "/metrics/db"))))

(defn get-db-metrics [request]
  (response {:db (db/db-metrics)
             :climbs (db/climbs-metrics)}))

(defn get-climbs [request]
  (response (array-map :is ["climb" "queryable" "list"]
                       :numberOfItems 12333)))

(defn post-climbs [request]
  (let [body (get-in request [:body])
        tags  (apply hash-set (map keyword (get-in request [:body (name :is)])))]
    (println tags)
    (println (get-in body [(name :UID)]))
    (if (query/climb-query? tags)
      (header 
       (response (query/climb-query tags body))
       "Location" (local-url request (format "/climbs.json?q=climb&qv=%s" (body (name :UID)))))
      (response {
                 :is "error"
                 :message (str "I didn't understand your query: " ((apply (fn [x] (str (name x) " ")) tags)))}))))

(defn get-climb [request]
  (response {:is "climb"
             :name "Doorway"
             :grade "HS"
             :crag (local-url request "/crags/234234234.json")}))

(defn post-climb [request]
  (response {
             :is "climb"
             :home (local-url request)}))

(defn get-crags [request]
  (response (array-map :is ["crag" "queryable" "list"]
                       :numberOfItems 333)))

(defn get-crag [request]
  (response {:is "crag"
             :name "Bosigran"
             :location {:lat 0.23 :long 0.12}}))


(defn request-printer [handler]
  (fn [request]
    (println (format "INCOMING REQUEST: %s" request))
    (handler request)))
