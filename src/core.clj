(ns core
  (:require [ring.adapter.jetty :as jetty]))

(defonce server (atom nil))

(defn app [req]
  (case (:uri req)
    "/" {:status 200
         :body "<body>
                <h1>TimothyPew.com</h1>
                <p>This is my website.</p>
                </body>"
         :headers {"Content-Type" "text/html"}}
    {:status 404
     :body "Page not found."
     :headers {"Content-Type" "text/plain"}}))

(defn start-server
  ([] (start-server 8080))
  ([port]
   (when @server (stop-server))
   (reset! server
           (jetty/run-jetty (fn [req] (app req))
                            {:port port
                             :join? false}))))

(defn stop-server []
  (when-some [s @server]
    (.stop s)
    (reset! server nil)))

(defn -main []
  (start-server))

(comment
  (start-server)
  (stop-server))



