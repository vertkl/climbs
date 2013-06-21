(ns climbs.server
  (:use [compojure.core]
        [ring.util.response])
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [compojure.route :as route]                        
            [ring.adapter.jetty :as jetty]
            [climbs.views :as v])
  (:gen-class))

(defroutes app-routes
  (GET "/" request (v/index request))
  (route/resources "/")

  (GET "/ping" request (v/ping request))

  (context "/metrics" []
           (defroutes metrics-routes
             (GET "/" request (v/get-metrics-index request))
             (GET "/db" request (v/get-db-metrics request))))

  (context "/climbs" []
           (defroutes climbs-routes
             (GET "/" request (v/get-climbs request))
             (POST "/" request (v/post-climbs request))))

  (context "/climbs/:id" [id]
           (defroutes climb-route
             (GET "/" request (v/get-climb request))))

  (context "/crags" []
           (defroutes climbs-routes
             (GET "/" request (v/get-crags request))))

  (context "/crags/:id" [id]
           (defroutes climb-route
             (GET "/" request (v/get-crag request)))))


(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      ;;(v/request-printer)
      ))


(defn -main [& args]
  (if (not (empty? args))
    (jetty/run-jetty app {:port (read-string (first args))})
    (jetty/run-jetty app {:port 8090}))
)
