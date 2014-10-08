(ns trying-meltdown.endpoint_source)
(def path (str (System/getProperty "user.dir") "/src/trying_meltdown/"  "fake_vcard.vcf"))

(def vTemplate (slurp path))

(defn createVcard [id]
  (apply format vTemplate (repeat 6 id)))

