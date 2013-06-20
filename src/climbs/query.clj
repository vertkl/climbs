(ns climbs.query
  (:use [endjinn.protocol
 :only [tags-match?]]))


(defn climb-query? [tags]
  (tags-match? tags :climb :query))



(defn climb-query [tags body]
  {:is :climb
   :data "Here is some information about a climb"})



