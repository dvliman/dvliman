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
   [:h1 "Building a reactive web service with Spring Webflux, Kotlin, and PostgreSQL"]
   [:p "This post shows how to create a reactive web service with Spring Webflux, Kotlin, PostgreSQL"]
   [:blockquote
    [:p [:strong "Context: "] [:a {:href "https://spring.io/"} "Spring Framework 5"] " introduced the so-called "
     [:strong "Reactive Stack"] ". The " [:code "reactive"] " keyword refers to the "
     [:a {:href "https://www.reactivemanifesto.org/"} "Reactive Manifesto"]
     ", which is a standard " [:a {:href "https://github.com/reactive-streams/reactive-streams-jvm/blob/v1.0.0/README.md"}
                               "specification"] " for asynchronous stream processing with non-blocking back-pressure in JVM languages.
     In short, Spring Webflux is a non-blocking web framework that uses " [:a {:href "https://projectreactor.io/"} "Reactor"]
     " library, which implements the Reactive Streams Specifications, to asynchronously manage HTTP requests."]]
   [:h2 "Requirements"]
   [:p "Say we want to build an HTTP service that can do the following:"]
   [:ul
    [:li [:code "POST /api/users/create"] " (write a user record)"]
    [:li [:code "POST /api/users/fetch"] " (read a user record)"]
    [:li [:code "POST /api/users/all"] " (to demonstrate Mono vs. Flux)"]]
   [:p "First, I would use "[:a {:href "https://start.spring.io/"} "Spring Initializr" ] " to bootstrap the project.
   This helps setup our dependencies and gradle tasks necessary to get the server running "]
   [:ul
    [:li [:strong "Project: "] "Gradle Project"]
    [:li [:strong "Language: "] "Kotlin"]
    [:li [:strong "Spring Boot: "] "2.2.0 (SNAPSHOT)"]
    [:li [:strong "Group: "] "com.dvliman"]
    [:li [:strong "Artifact: "] "demo"]
    [:li [:strong "Dependencies: "] "Reactive Web"]]
   [:p "Generate Project -  ⌘ + ⏎"]
   [:p "Next, let's add additional dependencies in " [:code "build.gradle"]]
   [:pre.code "
      dependencies {
          ...
          implementation 'com.github.davidmoten:rxjava2-jdbc:0.2.2'
          implementation 'org.postgresql:postgresql:42.2.5'
      }"]
   [:blockquote [:p [:strong "Note: "] "At the time of writing, there is no \"official\" reactive JDBC drivers. JDBC is inherently a blocking API.
    It will take while for JDBC async API to become a standard. However, there some third-party libraries that
    we can use. These libraries typically use non-blocking thread pool to execute IO calls and exposes the
    reactive / reactor interfaces such as Mono and Flux. I picked David Moten's RxJava2 library because it has good
    documentation and I can understand the code"]]
   [:h2 "Coding..."]
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
