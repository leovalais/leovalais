(ns leovalais.core
  (:require [reagent.core :as r]
            [goog.string :as gstring]
            [goog.string.format]))

;; (def cv {:name "Valais Léo"
;;          :header

(def skills (r/atom [{:skill "Java"
                      :awesomeness 70
                      :image "http://assets.stickpng.com/thumbs/58480979cef1014c0b5e4901.png"
                      :color "#E30"}
                     {:skill "C"
                      :awesomeness 80
                      :image "https://cdn.jsdelivr.net/npm/@programming-languages-logos/c@0.0.3/c_256x256.png"
                      :color "#555"}
                     {:skill "Common Lisp"
                      :awesomeness 90
                      :image "https://www.european-lisp-symposium.org/static/logos/cl-foundation.png"
                      :color "#16E"}]))

;; -------------------------
;; Views

(defn skill [{:keys [skill awesomeness color image]}]
  (let [width 3
        box 30
        r 12.5
        perimeter (* 2 Math/PI r)]
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
                                                             (* (/ awesomeness 100)
                                                                perimeter)
                                                             perimeter)
                           :style {:stroke color}}]]
      [:image {:x "22.5%" :y "22.5%"
               :width "55%" :height "55%"
               :href image}]]
     [:p {:style {:margin 0
                  :font-size "100%"
                  :text-align "center"}} skill]]))

(defn cv-view []
  [:div
   [:h1 "Léo Valais"]
   [:h2 "Computer Science research & IT engineering (graduating 2020)"]
   [:p "Looking for a " [:em "6-month Spring internship"] " in "
    [:strong "Lisp/Clojure"] " or "
    [:strong "image processing"] "."]
   [:div.skills
    (let [size "15%"]
      (map (fn [s]
             [:span {:style {:display "inline-block"
                             :width size
                             :height size}}
              [skill s]])
           @skills))]])

(defn mount-root []
  (r/render [cv-view]
            (.getElementById js/document "app")))

;; -------------------------
;; Initialize app

(defn init! []
  (mount-root))
