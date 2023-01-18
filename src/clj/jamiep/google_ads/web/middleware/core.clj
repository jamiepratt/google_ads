(ns jamiep.google-ads.web.middleware.core
  (:require
   [jamiep.google-ads.env :as env]
   [ring.middleware.defaults :as defaults]
   [ring.middleware.session.cookie :as cookie]
   [ring.middleware.oauth2 :refer [wrap-oauth2]]))

(defn wrap-base
  [{:keys [metrics site-defaults-config cookie-secret] :as opts}]
  (let [cookie-store (cookie/cookie-store {:key (.getBytes ^String cookie-secret)})]
    (fn [handler]
      (cond-> ((:middleware env/defaults) handler opts)
        true (defaults/wrap-defaults
              (-> site-defaults-config
                  (assoc-in [:session :store] cookie-store)))
        ))))


(defn wrap-google-oauth2 [{:keys [oauth2]}]
  (fn [handler]
    (wrap-oauth2 handler oauth2)))
