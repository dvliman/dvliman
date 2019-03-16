(ns dvliman.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [hiccup.page :refer [html5 include-css]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn base [title content]
  (html5 {:lang "en"}
    [:head
     [:title title]
     (include-css "//edwardtufte.github.io/tufte-css/tufte.css")
     [:body content]]))

(def writings
  {:spring-webflux-kotlin-postgresql "Building a reactive web service with Spring Webflux, Kotlin, and PostgreSQL"})
(defn home-page []
  [:article
   [:section
    [:p {:class "subtitle"} "About David"]
    [:figure
     [:img {:src "/img/davidliman.jpg"}]]]
   [:section
    [:p "Hi, my name is David Liman. I am a software craftsman who loves solving problems with functional paradigm. I write simple, robust, and easy-to-change code"]]
   [:section
    [:h2 "Articles"]
    [:ul
     [:li "Building a reactive web service with Spring Webflux, Kotlin, and PostgreSQL"]]]])

(defroutes app-routes
  (GET "/" [] (base "dvliman.com" (home-page)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
