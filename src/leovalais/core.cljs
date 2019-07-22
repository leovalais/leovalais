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
               :accent :strong
               :image "https://www.european-lisp-symposium.org/static/logos/cl-foundation.png"}
              {:skill "Clojure(Script)"
               :awesomeness 80
               :accent :strong
               :image "https://upload.wikimedia.org/wikipedia/commons/5/5d/Clojure_logo.svg"}
              {:skill "OCaml"
               :awesomeness 85
               :accent :em
               :image "https://www.logolynx.com/images/logolynx/0f/0f520e29cef40f9db320d1807726ba9b.jpeg"}
              {:skill "Haskell"
               :awesomeness 70
               :accent :em
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
              ]))

;; -------------------------
;; Views

(defn heatmap-color [heat]
  (let [h (* (- 1.0 heat) 300)
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
                                    :filter "opacity(0.2)"}}]
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
  [:div.skillset
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

(defn entry [& {:keys [picture title tags content]}]
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
     [:h3 {:style {:margin 0}} title]
     [:div.tags {:style {:display :flex
                         :flex-direction :row-reverse
                         :flex-grow 1}}
      (reverse tags)]]
    [:div.description content]]])

(defn section [title icon & content]
  [:section
   [:h2 {:style {:margin-bottom 0}}
    [:span {:style {:margin-right "8px"}} icon]
    title]
   [:div.content {:style {:margin-left "10px"}}
    content]])


(def left-panel-width 25)

(defn cv-view []
  [:div.cv {:style {:display "flex"}}
   [:div.left-panel {:style {:width (str left-panel-width "%")}}
    [skillset @skills]]
   [:div.middle-panel {:style {:width (str (- 100 left-panel-width) "%")}}
    [:h1 {:style {:margin-left "20px"}} "Léo Valais"]
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
      :picture "https://www.lrde.epita.fr/skins/common/images/logo.png"
      :title "LRDE"
      :tags (list [place-tag "Le Kremlin-Bicêtre"] [agenda-tag "2017-2020"])
      :content [:p {:style {:margin 0}}
                "ÉPITA's research and developement laboratory. Research specialization."]]
     [entry
      :picture "http://convention.epitanime.com/wp-content/uploads/2017/02/epita.jpg"
      :title [:span "ÉPITA" " " [:em "(graduating 2020)"]]
      :tags (list [place-tag "Le Kremlin-Bicêtre"] [agenda-tag "2015-2020"])
      :content [:p {:style {:margin 0}} "Computer Science engineering school. Specialization in image processing and machine learning."]]]

    [section "Experience" [icon "fas" "fa-user-tie"]
     ]]])



(defn mount-root []
  (r/render [cv-view]
            (.getElementById js/document "app")))

;; -------------------------
;; Initialize app

(defn init! []
  (mount-root))
