(ns improvista.sandbox
  (:require [improvista.playback :as playback]))

(defn c-major-scale
  [synth]
  (let [c-major  [60 62 64 65 67 69 71 72]]
    (doseq [note c-major]
      (playback/play-note synth 0 note 127 100))))

(defn c-major-chord
  [synth]
  (playback/play-chord synth
                       [60 64 67]
                       127
                       500))

