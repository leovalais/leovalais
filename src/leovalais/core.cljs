(ns leovalais.core
  (:require [reagent.core :as r]
            [goog.string :as gstring]
            [goog.string.format]))

;; (def cv {:name "Valais Léo"
;;          :header

(def max-awesomeness 100)
(def skills (r/atom
             [{:skill "Common Lisp"
               :awesomeness 94
               :accent :strong>em
               :image "https://www.european-lisp-symposium.org/static/logos/cl-foundation.png"}
              {:skill "Clojure(Script)"
               :awesomeness 80
               :accent :strong>em
               :image "https://upload.wikimedia.org/wikipedia/commons/5/5d/Clojure_logo.svg"}
              {:skill "OCaml"
               :awesomeness 85
               :accent :strong>em
               :image "https://www.logolynx.com/images/logolynx/0f/0f520e29cef40f9db320d1807726ba9b.jpeg"}
              {:skill "Haskell"
               :awesomeness 70
               :accent :strong>em
               :image "https://cdn.jsdelivr.net/npm/@programming-languages-logos/haskell@0.0.0/haskell_256x256.png"}
              {:skill "Python"
               :awesomeness 80
               :image "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Python-logo-notext.svg/1024px-Python-logo-notext.svg.png"}
              {:skill "Rust"
               :awesomeness 70
               :image "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Rust_programming_language_black_logo.svg/1200px-Rust_programming_language_black_logo.svg.png"}
              {:skill "Java"
               :awesomeness 75
               :image "http://assets.stickpng.com/thumbs/58480979cef1014c0b5e4901.png"}
              {:skill "C++"
               :awesomeness 60
               :image "https://cdn.iconscout.com/icon/free/png-256/c-plus-569563.png"}
              {:skill "C"
               :awesomeness 65
               :image "https://cdn.jsdelivr.net/npm/@programming-languages-logos/c@0.0.3/c_256x256.png"}
              {:skill "JavaScript"
               :awesomeness 45
               :image "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/JavaScript-logo.png/600px-JavaScript-logo.png"}
              {:skill "HTML5"
               :awesomeness 65
               :image "https://d2eip9sf3oo6c2.cloudfront.net/tags/images/000/000/206/square_256/html5.png"}
              {:skill "CSS3"
               :awesomeness 45
               :image "https://cv.beneyt.fr/images/logocss.png"}
              {:skill "Gimp"
               :awesomeness 40
               :image "https://img-19.ccm2.net/A32teLO0gaI8gWItzOYPOXfzMP0=/0129df8cd66948a9982deae91b956987/ccm-faq/UdNoIiE2Mc0cL0hXoYsExsy60V-2-gimp-s-.png"}
              {:skill "Premiere Pro"
               :awesomeness 40
               :image "https://pic.clubic.com/v1/images/1501439/raw"}
              {:skill "Illustrator"
               :awesomeness 30
               :image "https://pic.clubic.com/v1/images/1501704/raw"}
              {:skill "Apple"
               :awesomeness 75
               :image "https://www.rbcafe.fr/wp-content/uploads/apple_256.png"}]))

;; -------------------------
;; Views

(defn heatmap-color [heat]
  (let [h (+ 30 (* heat 200))
        s "90%"
        l "45%"]
    (gstring/format "hsl(%d, %s, %s)" h s l)))

(defn icon [& classes]
  [:i.icon {:class (apply str
                          (interleave (map str classes)
                                      (repeat " ")))}])

(defn skill [{:keys [skill awesomeness color image accent]}]
  (let [width 3
        box 30
        r 12.5
        perimeter (* 2 Math/PI r)
        color (or color
                  (heatmap-color (/ awesomeness max-awesomeness)))]
    [:div
     [:svg.activity-ring {:view-box (gstring/format "0 0 %d %d" box box)}
      [:g.ring {:style {:transform "scale(1) rotate(-90deg)"}}
       [:circle.background {:cx "50%"
                            :cy "50%"
                            :stroke-width width
                            :r r
                            :style {:stroke color
                                    :opacity "0.2"}}]
       [:circle.completed {:cx "50%"
                           :cy "50%"
                           :stroke-width width
                           :r r
                           :stroke-dasharray (gstring/format "%f, %f"
                                                             (* (/ awesomeness max-awesomeness)
                                                                perimeter)
                                                             perimeter)
                           :style {:stroke color}}]]
      [:image {:x "25%" :y "25%"
               :width "50%" :height "50%"
               :href image}]]
     [:p {:style {:margin 0
                  :text-align "center"}}
      (if accent
        [accent skill]
        skill)]]))

(def skillring-size "42%")

(defn skillset [skills]
  [:div.skillset {:style {:margin-top "35px"}} ; XXX pifomètre
   (map (fn [s]
          [:span {:style {:display "inline-block"
                          :width skillring-size
                          :height skillring-size
                          :margin-left "10px"
                          :margin-bottom "10px"
                          :font-size "70%"}}
           [skill s]])
        skills)])

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
   [:div.picture {:style {:width "10%"
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
  [:section
   [:h2 {:style {:margin-bottom 0}}
    [:span {:style {:margin-right "8px"}} icon]
    title]
   [:div.content {:style {:margin-left "10px"}}
    content]])


(def lrde-logo "https://www.lrde.epita.fr/skins/common/images/logo.png")
(def left-panel-width 25)

(defn cv-view []
  [:div.cv {:style {:display "flex"}}
   [:div.left-panel {:style {:width (str left-panel-width "%")}}
    [skillset @skills]]
   [:div.middle-panel {:style {:width (str (- 100 left-panel-width) "%")}}
    [:div.heading {:style {:display :flex}}
     [:h1 {:style {:margin "25px 20px"
                   :flex-grow 0}} "Léo Valais"]
     [:div.badges {:style {:display :flex
                           :align-items :center
                           :justify-content :flex-end
                           :flex-grow 1
                           :color "#777"}}
      (for [flag ["france" "uk" "spain"]]
        [:img {:src (str flag ".png")
               :width "40mm"
               :style {:margin-right "5px"}}])]]
    [:div.contact {:style {:width "80%" :margin "auto" :text-align "center"}}
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

    [section "Formation" [icon "fas" "fa-graduation-cap"]
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
      :picture "https://www.besport.com/images/be-red.svg"
      :title "Dev. internship at BeSport"
      :link "https://european-lisp-symposium.org/2019/index.html"
      :tags (list [code-tag "OCaml"]
                  [place-tag "Paris"]
                  [agenda-tag "Sept-Dec, 2018"])
      :content [:p "hello"]]
     [entry
      :picture lrde-logo
      :title "Summer internship at LRDE"
      :link "https://european-lisp-symposium.org/2019/index.html"
      :tags (list [place-tag "Le Kremlin-Bicêtre"]
                  [agenda-tag "May-July, 2017"])
      :content [:p "hello"]]]

    [section "Miscellaneous" [icon "fas" "fa-rocket"]
     [:ul
      [:li "a"]
      [:li "b"]]
     ]]])


(defn mount-root []
  (r/render [cv-view]
            (.getElementById js/document "app")))

;; -------------------------
;; Initialize app

(defn init! []
  (mount-root))
