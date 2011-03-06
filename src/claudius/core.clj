(ns claudius.core
  (:use name.choi.joshua.fnparse))

(def ^{:dynamic true} *fail-fn* (partial println 'fail))
(def ^{:dynamic true} *incomplete-fn* (partial println 'incomplete))

(defn simple-match [rule input]
  (rule-match rule
              *fail-fn*
              *incomplete-fn*
              {:remainder input}))

;; This is conceptually a hashmap, but order matters: the parser will
;; return the first thing that matches, so it would be wrong if (for
;; example) i came before im
(def tokens (map (fn [[tok val]]
                   [(str tok) val])
                 (partition 2
                            '[m 1000
                              d 500
                              l 50
                              v 5
                              im 999
                              ic 99
                              ix 9
                              iv 4
                              xm 990
                              xc 90
                              xl 40
                              cm 900
                              cd 400
                              c 100
                              x 10
                              i 1])))

(defn token-rule [[tok val]]
  (constant-semantics (lit-conc-seq tok) val))

(def parser (rep+ (lit-alt-seq tokens token-rule)))

(defn read-numerals [s]
  (reduce + (simple-match parser s)))
