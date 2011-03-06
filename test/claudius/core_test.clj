(ns claudius.core-test
  (:use claudius.core
        clojure.test))

(defmacro parser-test [name & tests]
  `(deftest ~name
     ~@(for [[val num] (partition 2 tests)]
         `(is (= ~val (read-numerals ~(str num)))))))

(parser-test sequence-test
             1 i
             3 iii
             2000 mm)

(parser-test subtraction-test
             4 iv
             900 cm
             400 cd
             999 im)

(parser-test complex-test
             49 xlix
             1997 mcmxcvii
             888 dccclxxxviii)
