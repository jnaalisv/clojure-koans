(ns koans.10-runtime-polymorphism
  (:require [koan-engine.core :refer :all]))

(defn hello
  ([] "Hello World!")
  ([a] (str "Hello, you silly " a "."))
  ([a & more] (str "Hello to this group: "
                   (apply str
                          (interpose ", " (cons a more)))
                   "!")))

(defmulti diet (fn [x] (:eater x)))
(defmethod diet :herbivore [a] "herbi")
(defmethod diet :carnivore [a] "Carni")
(defmethod diet :default [a] "def")

(meditations
  "Some functions can be used in different ways - with no arguments"
  (= "Hello World!" (hello))

  "With one argument"
  (= "Hello, you silly world." (hello "world"))

  "Or with many arguments"
  (= "Hello to this group: Peter, Paul, Mary!"
     (hello "Peter" "Paul" "Mary"))

  "Multimethods allow more complex dispatching"
  (= "herbi"
     (diet {:species "deer" :name "Bambi" :age 1 :eater :herbivore}))

  "Animals have different names"
  (= "herbi"
     (diet {:species "rabbit" :name "Thumper" :age 1 :eater :herbivore}))

  "Different methods are used depending on the dispatch function result"
  (= "Carni"
     (diet {:species "lion" :name "Simba" :age 1 :eater :carnivore}))

  "You may use a default method when no others match"
  (= "def"
     (diet {:name "Rich Hickey"})))
