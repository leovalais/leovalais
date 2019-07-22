(ns leovalais.core
  (:require [reagent.core :as r]
            [goog.string :as gstring]
            [goog.string.format]))

;; (def cv {:name "Valais Léo"
;;          :header

(def max-awesomeness 100)
(def skills (r/atom
             {:languages
              [{:skill "Common Lisp"
                :awesomeness 95
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
               {:skill "C"
                :awesomeness 65
                :image "https://cdn.jsdelivr.net/npm/@programming-languages-logos/c@0.0.3/c_256x256.png"}
               {:skill "C++"
                :awesomeness 60
                :image "https://cdn.iconscout.com/icon/free/png-256/c-plus-569563.png"}
               ]

              :web
              [{:skill "JavaScript"
                :awesomeness 45
                :image "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/JavaScript-logo.png/600px-JavaScript-logo.png"}
               {:skill "HTML5"
                :awesomeness 65
                :image "https://d2eip9sf3oo6c2.cloudfront.net/tags/images/000/000/206/square_256/html5.png"}
               {:skill "CSS3"
                :awesomeness 45
                :image "https://cv.beneyt.fr/images/logocss.png"}
               {:skill "Gimp"
                :awesomeness 50
                :image "https://img-19.ccm2.net/A32teLO0gaI8gWItzOYPOXfzMP0=/0129df8cd66948a9982deae91b956987/ccm-faq/UdNoIiE2Mc0cL0hXoYsExsy60V-2-gimp-s-.png"}
               {:skill "Premiere Pro"
                :awesomeness 40
                :image "https://pic.clubic.com/v1/images/1501439/raw"}
               {:skill "Illustrator"
                :awesomeness 30
                :image "https://pic.clubic.com/v1/images/1501704/raw"}
               ]}))

;; -------------------------
;; Views

(defn heatmap-color [heat]
  (let [h (* (- 1.0 heat) 300)
        s "90%"
        l "45%"]
    (gstring/format "hsl(%d, %s, %s)" h s l)))

(defn icon [& classes]
  [:i {:class (apply str
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
      [:image {:x "24%" :y "24%"
               :width "52%" :height "52%"
               :href image}]]
     [:p {:style {:margin 0
                  :font-size "100%"
                  :text-align "center"}}
      (if accent
        [accent skill]
        skill)]]))

(def skillring-size "15%")

(defn skillset [category skills]
  [:div.skillset
   [:h2 category]
   (map (fn [s]
          [:span {:style {:display "inline-block"
                          :width skillring-size
                          :height skillring-size
                          :margin-left "10px"}}
           [skill s]])
        skills)])

(defn cv-view []
  [:div.cv
   [:h1 "Léo Valais"] [icon "fab" "fa-github-alt"]
   [:h2 "Computer Science research & IT engineering (graduating 2020)"]
   [:p "Looking for a " [:em "6-month Spring internship"] " in "
    [:strong "Lisp/Clojure"] " or "
    [:strong "image processing"] "."]
   [skillset "Programming languages" (:languages @skills)]
   [skillset "Web & graphics" (:web @skills)]])

(defn mount-root []
  (r/render [cv-view]
            (.getElementById js/document "app")))

;; -------------------------
;; Initialize app

(defn init! []
  (mount-root))
