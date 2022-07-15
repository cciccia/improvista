(ns improvista.core
  (:import (javax.sound.midi MidiSystem))
  (:require [improvista.sandbox :as sandbox]
            [clojure.tools.logging :as log]))

(defn- log-uncaught-exceptions!
  []
  (Thread/setDefaultUncaughtExceptionHandler
    (reify Thread$UncaughtExceptionHandler
      (uncaughtException [_ _thread throwable]
        (log/info :uncaught-exception throwable)))))

(defn -main
  [& args]
  (log-uncaught-exceptions!)
  (with-open [synth (doto (MidiSystem/getSynthesizer) .open)]
    (log/info "Loaded synth with polyphony " (.getMaxPolyphony synth))
    (Thread/sleep 1000)
    (sandbox/c-major-scale synth)
    (Thread/sleep 1000)
    (sandbox/c-major-chord synth)
    (Thread/sleep 1000)))

