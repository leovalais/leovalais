(ns leovalais.core
  (:require [reagent.core :as r]
            [goog.string :as gstring]
            [goog.string.format]))

(def skills (r/atom
             [{:skill "Common Lisp"
               :accent true}
              {:skill "Clojure(Script)"
               :accent true}
              {:skill "OCaml"
               :accent true}
              {:skill "Haskell"
               :accent true}
              {:skill "Python"}
              {:skill "Machine Learing"}
              {:skill "Image Processing"}
              {:skill "Rust"}
              {:skill "Java"}
              {:skill "C++"}
              {:skill "C"}
              {:skill "RDBMS"}
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
               :font-size "0.75em"
               :font-weight (if accent :bold :normal)
               :margin-left "10px"
               :margin-bottom "3px"
               :padding "1px 10px"
               :border-width "2px"
               :border-style :solid
               :border-color (if accent skill-accent-color skill-color)
               :border-radius "20px"}]
    (if accent
      [:span.skill.accent {:style style} skill]
      [:span.skill {:style style} skill])))

(defn skillset [skills]
  [:div.skillset {:style {:width "90%"
                          :margin "25px auto"
                          :text-align :center}}
   (map skill skills)])

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
                           :width "auto"}}
     [:h3 {:style {:margin 0}} [:a {:href link} title]]
     [:div.tags {:style {:display :flex
                         :justify-content :flex-end
                         :flex-grow 1}}
      tags]]
    [:div.description content]]])

(defn section [title icon & content]
  [:section {:style {:margin-bottom 25}}
   [:h2 {:style {:margin-bottom 0}}
    [:span {:style {:margin-right "8px"}} icon]
    title]
   [:div.content {:style {:margin-left "10px"}}
    content]])


(def lrde-logo "https://www.lrde.epita.fr/skins/common/images/logo.png")
(defn cv-view []
  [:div.cv
   [:div.heading {:style {:display :flex}}
    [:h1 {:style {:margin "25px 0px"
                  :flex-grow 0}} "Léo VALAIS"]
    [:div.badges {:style {:display :flex
                          :align-items :center
                          :justify-content :flex-end
                          :flex-grow 1
                          :color "#777"}}
     (for [flag ["france" "uk" "spain"]]
       [:img {:src (str flag ".png")
              :width "40mm"
              :style {:margin-right "5px"}}])]]
   [:div.contact {:style {:width "60%" :margin "auto" :text-align "center"}}
    [contact [icon "fas" "fa-envelope"] "leo.valais97@gmail.com" "mailto:leo.valais97@gmail.com"] contact-separator
    [contact [icon "fas" "fa-phone-alt"] "+33 7 60 06 39 14" "tel:+33760063914"] contact-separator
    [contact [icon "fab" "fa-github-alt"] "leovalais" "https://github.com/leovalais"] contact-separator
    [contact [icon "fab" "fa-linkedin"] "leovalais" "https://www.linkedin.com/in/leovalais/"] contact-separator
    [contact [icon "fab" "fa-twitter"] "_letrov" "https://twitter.com/_letrov"] contact-separator
    [contact [icon "fas" "fa-globe-europe"] "leovalais.netlify.com" "https://leovalais.netlify.com"]]
   ;; [:h2 "Computer Science research & IT engineering (graduating 2020)"]
   ;; [:p "Looking for a " [:em "6-month Spring internship"] " in "
   ;;  [:strong "Lisp/Clojure"] " or "
   ;;  [:strong "image processing"] "."]
   [:p "Some introduction"]

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
     :content [:p {:style {:margin 0}} "Computer Science engineering school. Specialization in image processing and machine learning."]]]

   [section "Publications" [icon "fas" "fa-scroll"]
    [entry
     :picture lrde-logo
     :title "European Lisp Symposium 2019"
     :link "https://www.lrde.epita.fr/wiki/Publications/valais.19.els"
     :tags (list [article-tag [:a {:href "https://european-lisp-symposium.org/2019/index.html"}
                               "Article"]]
                 [agenda-tag "April 1, 2019"])
     :content [:p "“Implementing Baker’s SUBTYPEP Decision Procedure”, based on my research work at LRDE."]]]

   [section "Experience" [icon "fas" "fa-user-tie"]
    [entry
     :picture "https://european-lisp-symposium.org/static/favicon.png"
     :title "Talk at European Lisp Symposium 2019"
     :link "https://european-lisp-symposium.org/2019/index.html"
     :tags (list [place-tag "Genova, Italy"]
                 [agenda-tag "April 1, 2019"])
     :content [:p "hello"]]
    [entry
     :picture "acu.png"
     :title "Teaching assistant C/Unix/C++/Java/SQL"
     :tags (list [place-tag "Le Kremlin-Bicêtre"]
                 [agenda-tag "January-December 2019"])
     :content "TPAS"]
    [entry
     :picture "https://www.besport.com/images/be-red.svg"
     :title "Dev. internship at BeSport"
     :link "https://european-lisp-symposium.org/2019/index.html"
     :tags (list [code-tag "OCaml"]
                 [place-tag "Paris"]
                 [agenda-tag "September-December, 2018"])
     :content [:p "hello"]]
    [entry
     :picture lrde-logo
     :title "Summer internship at LRDE"
     :link "https://european-lisp-symposium.org/2019/index.html"
     :tags (list [place-tag "Le Kremlin-Bicêtre"]
                 [agenda-tag "May-July, 2017"])
     :content [:p "hello"]]]

   [section "Skills" [icon "fas fa-toolbox"]
    [skillset @skills]]

   [section "Miscellaneous" [icon "fas" "fa-rocket"]
    [:ul
     [:li "a"]
     [:li "b"]]]
   ])


(defn mount-root []
  (r/render [cv-view]
            (.getElementById js/document "app")))

;; -------------------------
;; Initialize app

(defn init! []
  (mount-root))
