(ns climbs.endjinn
  (:require [clj-yaml.core :as yaml]
           [clojure.set :as set]))

(defn local-url 
  ([request] (local-url request ""))
  ([request path] (let [{:keys [server-name server-port]} request]
                    (format "http://%s:%s%s" server-name server-port path))))

(defn load-config [key] ((yaml/parse-string 
 (slurp (clojure.java.io/resource "config.yml"))) key))

(defn tags-match? [tags & match]
  (let [match-tags (apply hash-set match)]
    (= (count match) (count (set/intersection tags match-tags)))))


