(ns improvista.playback
  (:require [clojure.tools.logging :as log])
  (:import (javax.sound.midi Synthesizer MidiChannel)
           (java.util.concurrent Executors Future)))

(defn play-note [^Synthesizer synth channel-num note velocity duration]
  (let [^MidiChannel channel (aget (.getChannels synth) channel-num)]
    (log/info "Note" note "Velocity" velocity "Duration" duration)
    (. channel noteOn note velocity)
    (Thread/sleep duration)
    (. channel noteOff note)))

(def chord-channels [0 1 2 3 4 5 6 7])

(defn play-chord
  [^Synthesizer synth notes velocity duration]
  (let [chord-service (Executors/newFixedThreadPool 8)
        tasks (map-indexed
                (fn [i note]
                  (fn []
                    (let [channel-num (get chord-channels i)]
                      (play-note synth channel-num note velocity duration))))
                notes)]
    (doseq [^Future f (.invokeAll chord-service tasks)]
      (.get f))
    (.shutdown chord-service)))


