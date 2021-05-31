package com.capstone.watuku.detail


object DataDummy {

    private val listTitle = arrayListOf(
        "Biotite",
        "Bornite",
        "Chrysocolla",
        "Malachite",
        "Muscovite",
        "Pyrite",
        "Quartz"
    )

    private val listDefinition = arrayListOf(
        "Biotite is a name used for a large group of black mica minerals that are commonly found in igneous and metamorphic rocks. These include annite, phlogopite, siderophyllite, fluorophlogopite, fluorannite, eastonite, and many others. These micas vary in chemical composition but are all sheet silicate minerals with very similar physical properties.",

        "Bornite is a copper iron sulfide mineral with a chemical composition of Cu5FeS4. It occurs in igneous, metamorphic, and sedimentary rocks. Minable concentrations of bornite occur in hydrothermal veins, contact metamorphic zones, and in the enriched zone of many sulfide mineral deposits.",

        "Chrysocolla is a mineral of secondary origin, commonly associated with other secondary copper minerals, it is typically found as glassy botryoidal or rounded masses or bubbly crusts, and as jackstraw mats of tiny acicular crystals or tufts of fibrous crystals. There are no known crystals of Chrysocolla. The chrysocolla \"crystals\" are all pseudomorphs. Copper-bearing allophane can look similar.",

        "Malachite is a green copper carbonate hydroxide mineral with a chemical composition of Cu2(CO3)(OH)2. It was one of the first ores used to produce copper metal. It is of minor importance today as an ore of copper because it is usually found in small quantities and can be sold for higher prices for other types of use." ,

        "Muscovite is the most common mineral of the mica family. It is an important rock-forming mineral present in igneous, metamorphic, and sedimentary rocks. Like other micas it readily cleaves into thin transparent sheets. Muscovite sheets have a pearly to vitreous luster on their surface. If they are held up to the light, they are transparent and nearly colorless, but most have a slight brown, yellow, green, or rose-color tint.",

        "Pyrite is a brass-yellow mineral with a bright metallic luster. It has a chemical composition of iron sulfide (FeS2) and is the most common sulfide mineral. It forms at high and low temperatures and occurs, usually in small quantities, in igneous, metamorphic, and sedimentary rocks worldwide. Pyrite is so common that many geologists would consider it to be a ubiquitous mineral.",

        "Quartz is a chemical compound consisting of one part silicon and two parts oxygen. It is silicon dioxide (SiO2). It is the most abundant mineral found at Earth's surface, and its unique properties make it one of the most useful natural substances."
    )

    private val listUsage = arrayListOf(
        "Biotite has a small number of commercial uses. Ground mica is used as a filler and extender in paints, as an additive to drilling muds, as an inert filler and mold-release agent in rubber products, and as a non-stick surface coating on asphalt shingles and rolled roofing. It is also used in the potassium-argon and argon-argon methods of dating igneous rocks.",
        "Bornite often used primarily an ore of copper and made into jewelry",
        "Mainly used as a gemstone for carvings and ornamental use and also as a jewelry",
        "Malachite has been used as a pigment and gem material",
        "Muscovite has been used as a joint compound and also as ingredient of paint, drilling mud, making of plastics, rubber, asphalt roofing, cosmetics",
        "Pyrite used to be an important ore for the production of sulfur and sulfuric acid. Today most sulfur is obtained as a byproduct of oil and gas processing. Some sulfur continues to be produced from pyrite as a byproduct of gold production.",
        "Quartz has been used as an ingredient in glass making, as an abrasive, as a foundry sand, and also being used in petroleum industry"
    )

    private val listImage = arrayListOf(
        "@drawable/muscovite",
        "@drawable/muscovite",
        "@drawable/muscovite",
        "@drawable/muscovite",
        "@drawable/muscovite",
        "@drawable/muscovite",
        "@drawable/muscovite"

    )

    val listData: ArrayList<WatuRecources>
        get() {
            val list = arrayListOf<WatuRecources>()
            for (position in listTitle.indices) {
                val watu = WatuRecources(
                    listTitle[position],
                    listDefinition[position],
                    listUsage[position],
                    listImage[position]
                )
                list.add(watu)
            }
            return list
        }
}
