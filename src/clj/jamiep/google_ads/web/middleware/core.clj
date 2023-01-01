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


(defn wrap-google-oauth2 [_]
  (fn [handler]
    (wrap-oauth2
     handler
     {:google
      {:authorize-uri    "https://accounts.google.com/o/oauth2/auth"
       :access-token-uri "https://accounts.google.com/o/oauth2/token"
       :client-id        "957244641744-ikcvq7noulb3fouud2d5mi8kd6godv00.apps.googleusercontent.com"
       :client-secret    "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"
       :scopes           ["https://www.googleapis.com/auth/adwords"]
       :launch-uri       "/oauth2/google"
       :redirect-uri     "https://oauth2.jamiep.org/oauth2/google/callback"
       :landing-uri      "/"}})))
