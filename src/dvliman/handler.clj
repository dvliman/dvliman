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

(def articles
  {:spring-webflux-kotlin-postgresql
     "Building a reactive web service with Spring Webflux, Kotlin, and PostgreSQL"})

(defn home-page []
  [:article
   [:section
    [:p {:class "subtitle"} "About David"]
    [:figure
     [:img {:src "/img/davidliman.jpg"}]]
    [:p "Hi, my name is David Liman. I am a software craftsman who loves solving problems with functional paradigm. I look for simple solutions to complex problems"]]
   [:section
    [:h2 "Articles"]
    [:ul
     [:li "Building a reactive web service with Spring Webflux, Kotlin, and PostgreSQL"]]]])

(defn webflux-article []
  [:section
   [:h2 "Building a reactive web service with Spring Webflux, Kotlin, and PostgreSQL"]
   [:p "This post shows how to create a reactive web service with Spring Webflux, Kotlin, PostgreSQL"]
   [:blockquote [:p "For the context, " [:a {:href "https://spring.io/"} "Spring Framework 5"] " introduced the so-called "
                 [:strong "Reactive Stack"] ". The " [:code "reactive"] " keyword here refers to the "
                 [:a {:href "https://www.reactivemanifesto.org/"} "Reactive Manifesto"]
                 ", which is a standard " [:a {:href "https://github.com/reactive-streams/reactive-streams-jvm/blob/v1.0.0/README.md"}
                                           "specification"] " for asynchronous stream processing with non-blocking back-pressure in JVM languages (and javascript).
    In short, Spring Webflux is a non-blocking web framework that uses " [:a {:href "https://projectreactor.io/"} "Reactor"]
                 " library, which implements the Reactive Streams Specifications, to asynchronously manage HTTP requests."]]
   [:h3 "Requirements"]
   [:p "Okay, let's assume we will build an HTTP service that does the following:"]
   [:ul
    [:li [:code "POST /api/users/create"]]
    [:li [:code "POST /api/users/fetch"]]
    [:li [:code "POST /api/users/update-name"]]
    [:li [:code "POST /api/users/delete"]]
    [:li [:code "POST /api/users/fetch-all"] " (to demonstrate Mono vs. Flux)"]]
   [:p "For any Spring Boot project, " [:a {:href "https://start.spring.io/"} "Spring Initializr" ] " is the go-to generators, like: " [:code "rails new blog"]]
   [:ul
    [:li [:strong "Project: "] "Gradle Project"]
    [:li [:strong "Language: "] "Kotlin"]
    [:li [:strong "Spring Boot: "] "2.1.3"]
    [:li [:strong "Group: "] "com.dvliman"]
    [:li [:strong "Artifact: "] "demo"]
    [:li [:strong "Dependencies: "] "Reactive Web"]]
   [:p "Generate Project -  ⌘ + ⏎"]
   [:p "Next, edit " [:code "build.gradle"], " add additional dependencies:"]
   [:pre.code "
      dependencies {
          ...
          implementation 'com.github.davidmoten:rxjava2-jdbc:0.2.2'
          implementation 'org.postgresql:postgresql:42.2.5'
      }"]
   [:p "At the time of writing, there are the 'official' reactive libraries like Spring Data Reactive Redis, Reactive MongoDB, etc
   but not for JDBC drivers. JDBC is inherently a blocking API. "]
   [:p "However, there are a couple third-party libraries
   that uses non-blocking thread pool to execute blocking JDBC calls and exposes
   reactive / reactor interfaces such as Mono and Flux. I picked David Moten's RxJava2 library
   simply because it has good documentation and I can understand the code"]
   [:strong [:h3 "Coding..."]]
   [:p "Now, with all dependencies we need, we can start with writing configurations i.e
   your database connection url. Say we define a " [:code "config file."]]
   [:p "Then we add " [:code "Beans.kt"] ", which would contains all the beans in the your system"]
   [:p "Here, there is a bean that connects to your database, and other singletons in your system you need"]
   [:p "Well, we have an active database connection. It is a good time to start defining (data) models we need
   to express the business flows (\"CRUD on users\")"]
   [:p "That looks good! Now we need write code that would persist your data into database, and, of course, allows you
   to query back the data."]])

(defroutes app-routes
  (GET "/" [] (base "dvliman.com" (home-page)))
  (GET "/articles/spring-webflux-kotlin-postgresql" []
    (base "Building a reactive web service with Spring Webflux, Kotlin, and PostgreSQL"
      (webflux-article)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
