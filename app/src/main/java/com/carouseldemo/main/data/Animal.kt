package com.carouseldemo.main.data

import androidx.annotation.DrawableRes

/**
 * Data class representing an animal in the carousel
 */
data class Animal(
    val id: Int,
    val name: String,
    val shortName: String,
    val description: String,
    @DrawableRes val imageRes: Int,
    val category: AnimalCategory = AnimalCategory.MAMMAL
)

enum class AnimalCategory {
    MAMMAL,
    BIRD,
    REPTILE,
    AQUATIC
}

/**
 * Repository for animal data
 */
object AnimalRepository {
    
    fun getAnimals(): List<Animal> = listOf(
        Animal(
            id = 0,
            name = "The Cat",
            shortName = "Cat",
            description = "The cat (Felis catus), also known as the domestic cat or housecat to distinguish it from other felids and felines, is a small, usually furry, domesticated, carnivorous mammal that is valued by humans for its companionship and for its ability to hunt vermin and household pests. Cats have been associated with humans for at least 9,500 years, and are currently the most popular pet in the world. Owing to their close association with humans, cats are now found almost everywhere in the world.",
            imageRes = com.carouseldemo.main.R.drawable.cat
        ),
        Animal(
            id = 1,
            name = "The Hippopotamus",
            shortName = "Hippo",
            description = "The hippopotamus (Hippopotamus amphibius), or hippo, from the ancient Greek for \"river horse\" (ἱπποπόταμος), is a large, mostly herbivorous mammal in sub-Saharan Africa, and one of only two extant species in the family Hippopotamidae (the other is the Pygmy Hippopotamus.) After the elephant, the hippopotamus is the third largest land mammal and the heaviest extant artiodactyl.",
            imageRes = com.carouseldemo.main.R.drawable.hippo
        ),
        Animal(
            id = 2,
            name = "The Monkey",
            shortName = "Monkey",
            description = "A monkey is a primate, either an Old World monkey or a New World monkey. There are about 260 known living species of monkey. Many are arboreal, although there are species that live primarily on the ground, such as baboons. Monkeys are generally considered to be intelligent. Unlike apes, monkeys usually have tails. Tailless monkeys may be called \"apes\", incorrectly according to modern usage; thus the tailless Barbary macaque is called the \"Barbary ape\".",
            imageRes = com.carouseldemo.main.R.drawable.monkey
        ),
        Animal(
            id = 3,
            name = "The Mouse",
            shortName = "Mouse",
            description = "A mouse (plural: mice) is a small mammal belonging to the order of rodents. The best known mouse species is the common house mouse (Mus musculus). It is also a popular pet. In some places, certain kinds of field mice are also common. This rodent is eaten by large birds such as hawks and eagles. They are known to invade homes for food and occasionally shelter.",
            imageRes = com.carouseldemo.main.R.drawable.mouse
        ),
        Animal(
            id = 4,
            name = "The Giant Panda",
            shortName = "Panda",
            description = "The giant panda, or panda (Ailuropoda melanoleuca, literally meaning \"black and white cat-foot\") is a bear native to central-western and south western China. It is easily recognized by its large, distinctive black patches around the eyes, over the ears, and across its round body. Though it belongs to the order Carnivora, the panda's diet is 99% bamboo.",
            imageRes = com.carouseldemo.main.R.drawable.panda
        ),
        Animal(
            id = 5,
            name = "The Rabbit",
            shortName = "Rabbit",
            description = "Rabbits (or, colloquially, bunnies) are small mammals in the family Leporidae of the order Lagomorpha, found in several parts of the world. There are eight different genera in the family classified as rabbits, including the European rabbit (Oryctolagus cuniculus), cottontail rabbits (genus Sylvilagus; 13 species), and the Amami rabbit (Pentalagus furnessi, an endangered species on Amami Ōshima, Japan)",
            imageRes = com.carouseldemo.main.R.drawable.rabbit
        )
    )
}