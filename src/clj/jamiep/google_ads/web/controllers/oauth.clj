(ns jamiep.google-ads.web.controllers.oauth
  (:require
   [ring.util.http-response :as http-response]))

(defn token!
  [req]
  (http-response/ok
   (:oauth2/access-tokens req)))
