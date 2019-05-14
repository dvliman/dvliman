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
     (include-css "//mrmrs.github.io/writing/css/tachyons.min.css")
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
    (bullet-point-with-section "Resume: " "https://github.com/dvliman/dvliman.github.io/blob/gh-pages/limandavid.pdf" "limandavid.pdf")
    (bullet-point-with-section "Linkedin: " "https://www.linkedin.com/in/limandavid/" "https://www.linkedin.com/in/limandavid/")]])

(defn home []
  [:main
   [:header.pa3.ph6-ns.mt3.mtr-ns.mb0
    [:h1.ttu.tracked.f3 "David Liman"]
    [:p.f3.lh-copy.measure
     "I am a (backend) Software Engineer living in Los Angeles.
     I love solving complex problems with simple solutions"]
    [:p.f3.lh-copy.measure
     "I have experience working at different software businesses. See my " [:a.link.blue {:href "/work-experience.html"} "work experience."]]
    (links)
    (articles)]])

(defn experience []
  [:main
   [:header.pa3.ph6-ns.mt3.mtr-ns.mb0
    [:h1.ttu.tracked.f3 "Work Experience"]

    [:h3.tracked.f4.bb.measure "Guardtime (2018 - Present)"]
    [:p.f3.lh-copy.measure "I am designing supply chain solutions with Guardtime's proprietary digital timestamping service
     called KSI (Keyless Signing Infrastructure). I am also looking into suitable architecture for permissioned blockchain applications."]

    [:h3.tracked.f4.bb.measure "Tigertext (2015 - 2018)"]
    [:p.f3.lh-copy.measure
     "I was a Platform Engineer at TigerText in charge of a real-time messaging platform that was written in Erlang.
     I worked on many product features such as chat rooms and chat bots, do-not-disturb auto-forwarding, etc."]

    [:p.f3.lh-copy.measure "As the business grows, we also helped evolved our chat server from pure secure messaging system to a communication
     platform (moving off from XMPP to HTTP Server Sent Event with API support). We built a number of services such as
     SMTP email gateway, push notifications services for iOS (APNS) and Android (GCM),
     document / image conversion system powered by a Redis-based worker queues"]

    [:p.f3.lh-copy.measure "Thanks to our efforts, TigerText is used as a reliable message delivery system by many hospitals
     and health care organizations in the U.S."]

    [:p.f3.lh-copy.measure "Personally, I learned a lot building highly concurrent, fault-tolerant servers and functional programming.
    Thanks to Erlang and OTP Framework"]

    [:h3.tracked.f4.bb.measure "Zipfworks (2014 - 2015)"]
    [:p.f3.lh-copy.measure "Zipfworks is an ideation / media lab that owns a few digital products such as
     BluePromoCode (a coupon site), StyleSpotter (fashion aggregator site)."]

    [:p.f3.lh-copy.measure "As a Backend Engineer, I worked on building API services that powers our
    deals database, product listings and images that we scrapped from merchants and manufacturers. Some features include
    custom coupon ranking, similar coupons, social network integrations."]

    [:p.f3.lh-copy.measure "I enjoyed my time here as the company culture allows us to experiment; be creative.
    I hacked up a product-category classifier using Naive Bayes algorithm from python " [:a {:href "https://www.nltk.org/"} "NLTK"]
    " library, built image resizing http proxy in Scala with " [:a {:href "https://github.com/sksamuel/scrimage"} "sksamuel/scrimage"],
    ". Played with Docker before orchestration layer matures. Fun time."]

    [:h3.tracked.f4.bb.measure "OneScreen, Data Engineer (2013 - 2014)"]
    [:h3.tracked.f4.bb.measure "IBM, Software Engineer (2011 - 2013)"]]])

(defroutes app-routes
  (GET "/" [] (base "dvliman.com" (home)))
  (GET "/work-experience.html" [] (base "dvliman.com" (experience)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
