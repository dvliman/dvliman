(ns dvliman.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [hiccup.page :refer [html5 include-css]]
            [hiccup.def :refer [defelem]]
            [hiccup.element :refer [javascript-tag]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn google-analytics []
  (javascript-tag
    (str
      "window.dataLayer = window.dataLayer || [];"
      "function gtag() { dataLayer.push(arguments); }"
      "gtag('js', new Date());"
      "gtag('set', {'user_id': 'USER_ID'});"
      "gtag('config', 'UA-140213129-1');"
      )))

(defn base [title content]
  (html5 {:lang "en"}
    [:head
     [:script {:src "https://www.googletagmanager.com/gtag/js?id=UA-140213129-1" :async true}]
     (google-analytics)
     [:title title]
     (include-css "//unpkg.com/tachyons@4.10.0/css/tachyons.min.css")
     [:body content]]))

(defn bullet-point [href title]
  [:li.p10
   [:a.f4.link.blue.dim {:href href} title]])

(defn bullet-point-with-section [section href title]
  [:li.p10
   [:span.f4 section]
   [:a.f4.link.blue.dim {:href href} title]])

(defn articles []
  [:div.f3 "Articles"
   [:ul.p10
    (bullet-point "https://medium.com/@dvliman/building-a-reactive-web-service-with-spring-webflux-kotlin-and-postgresql-71c4e0c2f870"
      "Building a reactive web service with Spring Webflux, Kotlin, and PostgreSQL")
    (bullet-point "https://flutter.io/" "Flutter app tutorial (coming Soon)")]])

(defn links []
  [:div.f3 "Links"
   [:ul.p10
    (bullet-point-with-section "Github: " "https://github.com/dvliman" "https://github.com/dvliman")
    (bullet-point-with-section "Resume: " "https://github.com/dvliman/dvliman.github.io/blob/master/limandavid.pdf" "limandavid.pdf")
    (bullet-point-with-section "Linkedin: " "https://www.linkedin.com/in/limandavid/" "https://www.linkedin.com/in/limandavid/")]])

(defn home []
  [:main
   [:header.pa3.ph6-ns.mt3.mtr-ns.mb0
    [:h1.ttu.tracked.f3 "David Liman"]
    [:p.f3.lh-copy.measure
     "I am a (backend) Software Engineer living in Los Angeles.
     I love solving complex problems with simple solutions. See my "
     [:a.link.blue {:href "/work-experience.html"} "work experience."]]
    (links)
    (articles)]])

(defn experience []
  [:main
   [:header.pa3.ph6-ns.mt3.mtr-ns.mb0
    [:h1.ttu.tracked.f3 "Work Experience"]

    [:h3.tracked.f4.bb.measure "Guardtime (2018 - Present)"]
    [:p.f3.lh-copy.measure "I refactored an old Spring MVC application to Spring Boot with Webflux, Kotlin, and RxJava support ("
    [:a {:href "https://medium.com/@dvliman/building-a-reactive-web-service-with-spring-webflux-kotlin-and-postgresql-71c4e0c2f870"}
    "and blog about it"] ")
    I participate in multiple contract-based projects. I am also
    researching permissioned blockchain that fits the company's digital signatures / timestamping service."]

    [:h3.tracked.f4.bb.measure "Tigertext (2015 - 2018)"]
    [:p.f3.lh-copy.measure.
     "I was a Platform Engineer at TigerText in charge of a real-time messaging platform that was written in Erlang.
     I worked on many product features such as chat rooms, chat bots, do-not-disturb auto-forwarding, CSV importer for nurses's schedules, file uploads."]

    [:p.f3.lh-copy.measure "We also evolved our chat server from only a secure messaging system to a communication
     platform (moving off from XMPP to HTTP Server Sent Event with API support). We built a number of services such as
     SMTP email gateway, push notifications, document / image conversion system powered by a Redis-based worker queues"]

    [:p.f3.lh-copy.measure "Thanks to the team's efforts, TigerText is used by many hospitals
     and health care organizations in the U.S. as a reliable message delivery system."]

    [:p.f3.lh-copy.measure "Personally, I learned a lot building highly concurrent, fault-tolerant servers and functional programming.
    Thanks to Erlang and OTP Framework"]

    [:h3.tracked.f4.bb.measure "Zipfworks (2014 - 2015)"]
    [:p.f3.lh-copy.measure "Zipfworks is an ideation / media lab that owns a few digital products such as
     BluePromoCode (a coupon site), StyleSpotter (fashion aggregator site)."]

    [:p.f3.lh-copy.measure "As a Backend Engineer, I worked on building API that powers our
    deals database, product listings and images that we scrapped from merchants and manufacturers. Some features include
    custom coupon ranking, similar coupons, social network integrations."]

    [:p.f3.lh-copy.measure "I enjoyed my time here as the company culture allows us to experiment and be creative.
    I hacked up a product-category classifier using Naive Bayes algorithm from python " [:a {:href "https://www.nltk.org/"} "NLTK"]
    " library, built image resizing http proxy in Scala with " [:a {:href "https://github.com/sksamuel/scrimage"} "sksamuel/scrimage"],
    ". Played with Docker before orchestration layer matures. Fun time."]

    [:h3.tracked.f4.bb.measure "OneScreen, Data Engineer (2010 - 2011) and (2013 - 2014)"]
    [:p.f3.lh-copy.measure "I had one year internship before I was re-hired again as a Data Engineer.
    My first task was to maintain a data pipeline that runs " [:a {:href "https://github.com/twitter/scalding"} "Scalding"] " (MapReduce job)
    on log files from our ads servers that is already nicely bucketed per hour in S3. The end results get stored
    in " [:a {:href "https://www.vertica.com/"} "Vertica."]]

    [:p.f3.lh-copy.measure "The MapReduce job computes watch counts, video-view quartiles, device type, and
    a bunch of other dimensions that we use for reporting and billing purposes."]

    [:p.f3.lh-copy.measure "I really enjoyed the scales and technical challenges in ad-tech industry. "]

    [:h3.tracked.f4.bb.measure "IBM, Software Engineer (2011 - 2013)"]
    [:p.f3.lh-copy.measure "IBM was my first job out of college. I worked on a project called Lifecycle Query Engine. It indexes "
    [:a {:href "https://www.w3.org/RDF/"} "RDF triplets"] " and process " [:a {:href "https://en.wikipedia.org/wiki/SPARQL"} "SPARQL"] " queries"]

    [:p.f3.lh-copy.measure "I remember doing everything from building web UI with Dojo Toolkit (IBM's internal web framework)
    to working on Java server deployed on Tomcat and IBM Websphere. I was looking into some performance issues (aka tuning a bunch of knobs)
    so that we can index RDF resources as much as possible while serving concurrent queries"]]])

(defroutes app-routes
  (GET "/" [] (base "dvliman.com" (home)))
  (GET "/work-experience.html" [] (base "dvliman.com" (experience)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
