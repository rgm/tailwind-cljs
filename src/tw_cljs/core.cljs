(ns tw-cljs.core)

(defn- tw->str [x] (if (keyword? x) (name x) (str x)))

(defn tw
  "Merge tailwind class collections into props. Gives the tailwind call sites a
   bit more meaning to spot/get a hold of them later.

   Accepts one or more collections of keywords or strings, plus an optional
   props map (similar to stylefy.core/use-style). Keywords tend to be less
   noisy as ClojureScript syntax. But many of the responsive tailwind styles
   will have to be supplied as strings (eg.  \"hover:text-bold\"), since the
   inboard colon means the reader can't parse it as a keyword.

   Examples:
   (tw [:a :b] {:on-click f}) => {:class [\"a\" \"b\"] :on-click f}
   (tw [:a] [\"b:c\"] [:d])   => {:class [\"a\" \"b:c\" \"d\"]}
   "
  ([& xs]
   (let [has-props? (map? (last xs))
         props      (if has-props? (last xs) {})
         classes    (->> (if has-props? (butlast xs) xs)
                         (apply concat)
                         (mapv tw->str))]
     (merge-with merge {:class classes} props))))