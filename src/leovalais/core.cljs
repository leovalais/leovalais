(ns leovalais.core
  (:require [reagent.core :as r]
            [goog.string :as gstring]
            [goog.string.format]))

(def skills (r/atom
             [{:skill "Common Lisp"}
              {:skill "Clojure(Script)"}
              {:skill "OCaml"}
              {:skill "Haskell"
               :accent false}
              {:skill "AI/ML"}
              {:skill "Image Processing"}
              {:skill "Image Synthesis"}
              {:skill "Distributed Computing"}
              {:skill "HPC"}
              {:skill "Language theory"}
              {:skill "Python"}
              {:skill "Rust"}
              {:skill "Java"}
              {:skill "C++"}
              {:skill "C"}
              {:skill "JavaScript"}
              {:skill "HTML5/CSS3"}]))

;; -------------------------
;; Views

(defn icon [& classes]
  [:i.icon {:class (apply str
                          (interleave (map str classes)
                                      (repeat " ")))}])

(defn contact [icon text url]
  [:a {:href url
       :style {:display "inline-block"
               :font-size "75%"
               :color "#777"}}
   [:span {:style {:margin-right "6px"}} icon]
   text])

(def contact-separator [:span.contact-separator {:style {:margin "0px 10px"
                                                         :color "#777"}}
                        "•"])

(def skill-color "rgb(88, 86, 214)")
(def skill-accent-color "rgb(125, 122, 255)")

(defn skill [{:keys [skill accent]}]
  (let [style {:display :inline-block
               :color (if accent "white" skill-color)
               :background-color (if accent skill-accent-color "white")
               :font-size "75%"
               :font-weight (if accent :bold :normal)
               :margin-left "5px"
               :margin-bottom "3px"
               :padding "0px 10px"
               :border-width "1.5px"
               :border-style :solid
               :border-color (if accent skill-accent-color skill-color)
               :border-radius "20px"}]
    (if accent
      [:span.skill.accent {:style style} skill]
      [:span.skill {:style style} skill])))

(defn skillset [skills]
  [:div.skillset {:style {:width "100%"
                          :margin "20px auto"
                          :text-align :center}}
   (map skill skills)])

(defn hlist [min-width & items]
  [:ul.hlist
   (map (fn [i] [:li.hlist-item {:style {:float :left
                                         :min-width min-width}} i])
        items)])

(defn tag [icon text]
  [:span.tag {:style {:display "inline-block"
                      :font-size "75%"
                      :color "#777"}}
   [:span {:style {:margin-right "6px"}} icon]
   text])

(def place-tag (partial tag [icon "fas" "fa-map-marker-alt"]))
(def agenda-tag (partial tag [icon "far" "fa-calendar-alt"]))
(def article-tag (partial tag  [icon "far" "fa-newspaper"]))
(def code-tag (partial tag  [icon "fas" "fa-laptop-code"]))
(def github-tag (tag [icon "fab" "fa-github"] "Hosted on GitHub"))
(def gitlab-tag (tag [icon "fab" "fa-gitlab"] "Hosted on GitLab"))
(def wip-tag (tag [icon "fas" "fa-hard-hat"] "Work in progress"))

(defn entry [& {:keys [picture title tags content link]}]
  [:div.entry {:style {:display :flex
                       :margin-top "10px"}}
   [:div.picture {:style {:width "7.5%"
                          :flex-shrink 0
                          :margin-right "10px"}}
    [:img {:src picture :width "100%"}]]
   [:div.content {:style {:display :flex
                          :flex-grow 1
                          :flex-direction :column}}
    [:div.heading {:style {:display :flex
                           :width :auto}}
     [:h3 {:style {:margin 0}} [:a {:href link} title]]
     [:div.tags {:style {:display :flex
                         :justify-content :flex-end
                         :flex-grow 1}}
      tags]]
    [:div.description content]]])

(defn section [title icon & content]
  [:section {:style {:margin-bottom 20}}
   [:h2 {:style {:margin-bottom 0
                 :padding-bottom 2
                 :border-bottom "1px solid #DDD"}}
    [:span {:style {:margin-right "8px"}} icon]
    title]
   [:div.content {:style {:margin-left "10px"}}
    content]])


(defn emph [& things]
  [:span.emph things])

(defn flag [country]
  [:img {:src (str country ".png")
         :width "11.667mm"}])

(def lrde-logo "https://www.lrde.epita.fr/skins/common/images/logo.png")
(defn cv-view []
  [:div.cv
   [:div.heading {:style {:display :flex}}
    [:h1 {:style {:margin "15px 0px"
                  :font-variant :small-caps
                  :flex-grow 0
                  :min-width "30%"}} "Léo Valais"]
    [:div.badges {:style {:display :flex
                          :align-items :center
                          :justify-content :flex-end
                          :flex-grow 1
                          :color "#777"}}
     [:div.contact {:style {:width "100%" :margin "auto" :text-align "center"}}
      [contact [icon "fas" "fa-envelope"] "leo.valais97@gmail.com" "mailto:leo.valais97@gmail.com"] contact-separator
      [contact [icon "fas" "fa-phone-alt"] "+33 7 60 06 39 14" "tel:+33760063914"] contact-separator
      [contact [icon "fab" "fa-github-alt"] "leovalais" "https://github.com/leovalais"] contact-separator
      [contact [icon "fab" "fa-linkedin"] "leovalais" "https://www.linkedin.com/in/leovalais/"] contact-separator
      [contact [icon "fab" "fa-twitter"] "_letrov" "https://twitter.com/_letrov"] contact-separator
      [contact [icon "fas" "fa-globe-europe"] "leovalais.netlify.com" "https://leovalais.netlify.com"] contact-separator
      [contact [flag "france"] "Native" "#"] contact-separator
      [contact [flag "uk"] "TOEIC & Semester abroad" "#"]]]]

   [:p.abstract {:style {:margin 0 :margin-top 5 :font-size "120%"}}
    "Looking for a six-month internship in " [emph "image processing"] ", " [emph "machine learning"]
    " or about " [emph "Lisp languages"] "."]
  

   [section "Education" [icon "fas" "fa-graduation-cap"]
    [entry
     :picture lrde-logo
     :title "LRDE"
     :link "https://www.epita.fr/nos-formations/diplome-ingenieur/cycle-ingenieur/les-majeures/"
     :tags (list [code-tag "Common Lisp"]
                 [place-tag "Le Kremlin-Bicêtre"]
                 [agenda-tag "2017-2020"])
     :content [:p {:style {:margin 0}}
               "ÉPITA's research and developement laboratory. Research specialization and a project supervized by laboratory's full-time researchers."]]
    [entry
     :picture "http://convention.epitanime.com/wp-content/uploads/2017/02/epita.jpg"
     :title [:span "ÉPITA" " " [:em "(graduating 2020)"]]
     :link "https://www.epita.fr/nos-formations/diplome-ingenieur/cycle-ingenieur/les-majeures/#majeure-IMAGE"
     :tags (list [place-tag "Le Kremlin-Bicêtre"] [agenda-tag "2015-2020"])
     :content [:p {:style {:margin 0}} "Computer Science engineering school. Specialization in image processing and machine learning.
Experience in raytracing, distributed computing, GPGPU computing, medical imagery, deep learning, etc."]]]

   [section "Publications" [icon "fas" "fa-scroll"]
    [entry
     :picture lrde-logo
     :title "European Lisp Symposium 2019"
     :link "https://www.lrde.epita.fr/wiki/Publications/valais.19.els"
     :tags (list [code-tag "Common Lisp"]
                 [article-tag [:a {:href "https://european-lisp-symposium.org/2019/index.html"}
                               "In proceedings"]]
                 [agenda-tag "April 1, 2019"])
     :content [:p "“Implementing Baker’s " [:code "SUBTYPEP"] " Decision Procedure”, based on my research
work at LRDE. Presentation of an alternative implementation for " [:code "SUBTYPEP"] ", a standard
Common Lisp predicate. Involves type theory, type representation and performance concerns."]]]

   [section "Experience" [icon "fas" "fa-user-tie"]
    [entry
     :picture "https://european-lisp-symposium.org/static/favicon.png"
     :title "Talk at European Lisp Symposium 2019"
     :link "https://european-lisp-symposium.org/2019/index.html"
     :tags (list [place-tag "Genova, Italy"]
                 [agenda-tag "April 1, 2019"])
     :content [:p "Presentation of my research work at LRDE, described in “Implementing Baker’s "
[:code "SUBTYPEP"] " Decision Procedure”. "]]
    [entry
     :picture "acu.png"
     :title "Teaching assistant C • Unix • C++ • Java • SQL"
     :tags (list [place-tag "Le Kremlin-Bicêtre"]
                 [agenda-tag "January-December 2019"])
     :content "Teaching assistant for third year students. Reponsible of tutorials, workshops and
school projects in several languages and technologies."]
    [entry
     :picture "https://www.besport.com/images/be-red.svg"
     :title "Software development internship at BeSport"
     :link "https://european-lisp-symposium.org/2019/index.html"
     :tags (list [code-tag "OCaml"]
                 [place-tag "Paris"]
                 [agenda-tag "September-December, 2018"])
     :content [:p "Modernization and deployement of a static website generator written in OCaml. "
"Currently used to generate the website " [:a {:href "https://ocsigen.org/"} "ocsigen.org"] " holding the
documentation of the Ocsigen project."]]]

   [section "Skills" [icon "fas fa-toolbox"]
    [skillset @skills]]

   [section "Some selected projects" [icon "fas fa-tools"]
    [entry
     :picture "tree.jpg"
     :title [:code {:style {:font-size "120%"}} "cl-lsystem"]
     :link "https://github.com/leovalais/cl-lsystem"
     :tags (list github-tag
                 [code-tag "Common Lisp"]
                 [agenda-tag "April 2018"])
     :content "L-system (fractal description language) interpretor with a custom, Turing-complete,
fully extensible DSL written in Common Lisp. Supports 2D and 3D L-systems."]
    [entry
     :picture "abeille.jpg"
     :title [:code {:style {:font-size "120%"}} "bee-wing-intersection"]
     :link "#"
     :tags (list gitlab-tag
                 [code-tag "Pythton/OpenCV+Scikit Image"]
                 [agenda-tag "May-June 2019"])
     :content "Automatic detection process of bee wings' vein intersection."]]

   [:footer {:style {:position :absolute :bottom 0 :right 20 :font-size "60%" :color "#777"}}
    "Made with ClojureScript and Reagent. "
    "An interactive version of this résumé is available at "
    [:a {:href "https://leovalais.netlify.com"} "leovalais.netlify.com"]
    "."]])


(defn mount-root []
  (r/render [cv-view]
            (.getElementById js/document "app")))

;; -------------------------
;; Initialize app

(defn init! []
  (mount-root))
