package com.example.mohamedazizfekih.data

import com.example.mohamedazizfekih.R
import com.example.mohamedazizfekih.model.Difficulty
import com.example.mohamedazizfekih.model.HeritageCategory
import com.example.mohamedazizfekih.model.Question

// Based on Lab 7.2 - Collections: lists store categories, questions, and answers.
object HeritageRepository {
    const val ISLAMIC_HERITAGE_ID = "islamic_heritage"

    private val placeholderQuestions = listOf(
        Question(
            imageRes = R.drawable.placeholder,
            question = "Placeholder question: identify this Tunisian heritage site.",
            options = listOf("Option A", "Option B", "Option C", "Option D"),
            correctAnswer = "Option A",
            difficulty = Difficulty.EASY
        ),
        Question(
            imageRes = R.drawable.placeholder,
            question = "Placeholder question: choose the correct city.",
            options = listOf("Tunis", "Sousse", "Kairouan", "Bizerte"),
            correctAnswer = "Tunis",
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            imageRes = R.drawable.placeholder,
            question = "Placeholder question: match the image with its category.",
            options = listOf("Roman", "Islamic", "Modern", "Natural"),
            correctAnswer = "Roman",
            difficulty = Difficulty.HARD
        )
    )

    val categories: List<HeritageCategory> = listOf(
        HeritageCategory(
            id = "roman_heritage",
            title = "Roman Heritage",
            subtitle = "Placeholder data until Roman site images are added",
            questions = placeholderQuestions
        ),
        HeritageCategory(
            id = ISLAMIC_HERITAGE_ID,
            title = "Islamic Heritage",
            subtitle = "Mosques, ribats, medinas, and historic cities",
            questions = islamicHeritageQuestions()
        ),
        HeritageCategory(
            id = "punic_pre_roman",
            title = "Punic & Pre-Roman",
            subtitle = "Placeholder data for future Carthaginian questions",
            questions = placeholderQuestions
        ),
        HeritageCategory(
            id = "modern_heritage",
            title = "Modern Heritage",
            subtitle = "Placeholder data for modern landmarks",
            questions = placeholderQuestions
        ),
        HeritageCategory(
            id = "natural_mixed_sites",
            title = "Natural & Mixed Sites",
            subtitle = "Placeholder data for landscapes and mixed sites",
            questions = placeholderQuestions
        ),
        HeritageCategory(
            id = "extra_placeholder",
            title = "Extra Placeholder Category",
            subtitle = "Ready for a new topic chosen by the teacher",
            questions = placeholderQuestions
        )
    )

    // Based on Lab 7.3 - Higher-order functions: firstOrNull searches the list.
    fun getCategory(categoryId: String): HeritageCategory? {
        return categories.firstOrNull { it.id == categoryId }
    }

    // Based on Lab 7.3 - Higher-order functions: filter chooses questions by difficulty.
    fun questionsFor(categoryId: String, difficulty: Difficulty): List<Question> {
        val category = getCategory(categoryId) ?: getCategory(ISLAMIC_HERITAGE_ID)

        return category
            ?.questions
            ?.filter { question -> question.difficulty == difficulty }
            .orEmpty()
            .ifEmpty { category?.questions.orEmpty() }
    }

    // Based on Lab 7.2 - Collections: returns the real Islamic Heritage question list.
    private fun islamicHeritageQuestions(): List<Question> {
        return listOf(
            // Easy level - 10 questions
            Question(
                imageRes = R.drawable.kairouan_mosque,
                question = "Which monument is known as one of the oldest and most important mosques in North Africa?",
                options = listOf("Great Mosque of Kairouan", "Ribat of Monastir", "Dar Hussein", "Amphitheatre of El Jem"),
                correctAnswer = "Great Mosque of Kairouan",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.zitouna_mosque,
                question = "Which historic mosque stands in the heart of the Medina of Tunis?",
                options = listOf("Zitouna Mosque", "Sidi Okba Mosque", "Bourguiba Mosque", "Malik ibn Anas Mosque"),
                correctAnswer = "Zitouna Mosque",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.ribat_sousse,
                question = "Which fortified Islamic landmark is located in the city of Sousse?",
                options = listOf("Ribat of Sousse", "Kasbah of Kef", "Borj Ghazi Mustapha", "Dar Lasram"),
                correctAnswer = "Ribat of Sousse",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.ribat_monastir,
                question = "Which ribat is one of Monastir's most famous historic landmarks?",
                options = listOf("Ribat of Monastir", "Ribat of Sfax", "Kasbah of Tunis", "Great Mosque of Mahdia"),
                correctAnswer = "Ribat of Monastir",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.medina_tunis,
                question = "Which old city is shown in the image?",
                options = listOf("Medina of Tunis", "Medina of Sfax", "Medina of Tozeur", "Medina of Bizerte"),
                correctAnswer = "Medina of Tunis",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.kairouan_medina,
                question = "Which historic Islamic city is shown in the image?",
                options = listOf("Kairouan", "Mahdia", "Sousse", "Nabeul"),
                correctAnswer = "Kairouan",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.sousse_medina,
                question = "Which medina is shown in this image?",
                options = listOf("Medina of Sousse", "Medina of Tunis", "Medina of Kairouan", "Medina of Gabes"),
                correctAnswer = "Medina of Sousse",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.great_mosque_sfax,
                question = "Which mosque is shown in the image?",
                options = listOf("Great Mosque of Sfax", "Zitouna Mosque", "Sidi Mahrez Mosque", "Mosque of the Three Doors"),
                correctAnswer = "Great Mosque of Sfax",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.great_mosque_mahdia,
                question = "This Great Mosque is located in which coastal city?",
                options = listOf("Mahdia", "Tunis", "Kef", "Djerba"),
                correctAnswer = "Mahdia",
                difficulty = Difficulty.EASY
            ),
            Question(
                imageRes = R.drawable.three_doors_mosque,
                question = "Which mosque is known for three entrances?",
                options = listOf("Mosque of the Three Doors", "Youssef Dey Mosque", "Zitouna Mosque", "Great Mosque of Sfax"),
                correctAnswer = "Mosque of the Three Doors",
                difficulty = Difficulty.EASY
            ),

            // Medium level - 10 questions
            Question(
                imageRes = R.drawable.youssef_dey_mosque,
                question = "Which Ottoman-period mosque in Tunis is shown in the image?",
                options = listOf("Youssef Dey Mosque", "Sidi Mahrez Mosque", "Zitouna Mosque", "Great Mosque of Sfax"),
                correctAnswer = "Youssef Dey Mosque",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.sidi_mahriz_mosque,
                question = "Which mosque is known for its Ottoman-style domes?",
                options = listOf("Sidi Mahrez Mosque", "Ribat of Sousse", "Great Mosque of Mahdia", "Mosque of the Three Doors"),
                correctAnswer = "Sidi Mahrez Mosque",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.kasbah_mosque_tunis,
                question = "Which mosque is located in the Kasbah district of Tunis?",
                options = listOf("Kasbah Mosque", "Al Hawa Mosque", "Great Mosque of Testour", "Zitouna Mosque"),
                correctAnswer = "Kasbah Mosque",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.hammouda_pacha_mosque,
                question = "Which Tunis mosque is named after Hammouda Pacha?",
                options = listOf("Hammouda Pacha Mosque", "Sahib Etabaa Mosque", "Kasbah Mosque", "Ribat of Monastir"),
                correctAnswer = "Hammouda Pacha Mosque",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.al_hawa_mosque,
                question = "Which mosque is shown in this image?",
                options = listOf("Al Hawa Mosque", "Great Mosque of Sfax", "Youssef Dey Mosque", "Zitouna Mosque"),
                correctAnswer = "Al Hawa Mosque",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.sahib_etabaa_mosque,
                question = "Which mosque is shown in the image?",
                options = listOf("Sahib Etabaa Mosque", "Kasbah Mosque", "Three Doors Mosque", "Ribat of Sousse"),
                correctAnswer = "Sahib Etabaa Mosque",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.tourbet_el_bey,
                question = "Which royal mausoleum in Tunis is shown?",
                options = listOf("Tourbet El Bey", "Zaouia Sidi Sahbi", "Dar Hussein", "Skifa Kahla"),
                correctAnswer = "Tourbet El Bey",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.dar_hussein,
                question = "Which historic palace in the Medina of Tunis is shown?",
                options = listOf("Dar Hussein", "Dar Ben Abdallah", "Tourbet El Bey", "Bab Bhar"),
                correctAnswer = "Dar Hussein",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.dar_ben_abdallah,
                question = "Which traditional house museum in Tunis is shown?",
                options = listOf("Dar Ben Abdallah", "Dar Hussein", "Kasbah Mosque", "Sidi Mahrez Mosque"),
                correctAnswer = "Dar Ben Abdallah",
                difficulty = Difficulty.MEDIUM
            ),
            Question(
                imageRes = R.drawable.souk_el_attarine,
                question = "Which traditional souk in Tunis is shown?",
                options = listOf("Souk El Attarine", "Souk El Blat", "Souk El Grana", "Souk El Trouk"),
                correctAnswer = "Souk El Attarine",
                difficulty = Difficulty.MEDIUM
            ),

            // Hard level - 10 questions
            Question(
                imageRes = R.drawable.bab_bhar_tunis,
                question = "Which famous gate marks an entrance to the Medina of Tunis?",
                options = listOf("Bab Bhar", "Bab Diwan", "Skifa Kahla", "Bab Saadoun"),
                correctAnswer = "Bab Bhar",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.skifa_kahla_mahdia,
                question = "Which fortified Fatimid-era gate is located in Mahdia?",
                options = listOf("Skifa Kahla", "Bab Bhar", "Bab Diwan", "Kasbah Gate"),
                correctAnswer = "Skifa Kahla",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.bab_diwan_sfax,
                question = "Which gate belongs to the historic medina of Sfax?",
                options = listOf("Bab Diwan", "Bab Bhar", "Skifa Kahla", "Bab El Khadra"),
                correctAnswer = "Bab Diwan",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.sfax_ramparts,
                question = "Which city's medina ramparts are shown?",
                options = listOf("Sfax", "Tunis", "Kairouan", "Mahdia"),
                correctAnswer = "Sfax",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.monastir_medina,
                question = "Which coastal medina is shown in this image?",
                options = listOf("Monastir Medina", "Mahdia Medina", "Tunis Medina", "Kairouan Medina"),
                correctAnswer = "Monastir Medina",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.mahdia_medina,
                question = "Which historic coastal medina is shown?",
                options = listOf("Mahdia Medina", "Sousse Medina", "Sfax Medina", "Tunis Medina"),
                correctAnswer = "Mahdia Medina",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.testour_great_mosque,
                question = "Which mosque in northern Tunisia is known for Andalusian influence?",
                options = listOf("Great Mosque of Testour", "Great Mosque of Sfax", "Zitouna Mosque", "Great Mosque of Mahdia"),
                correctAnswer = "Great Mosque of Testour",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.sidi_bou_makhlouf_mausoleum,
                question = "Which mausoleum in Kef is shown?",
                options = listOf("Sidi Bou Makhlouf Mausoleum", "Sidi Youssef Mausoleum", "Zaouia Sidi Sahbi", "Tourbet El Bey"),
                correctAnswer = "Sidi Bou Makhlouf Mausoleum",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.zaouia_sidi_sahbi,
                question = "Which famous religious complex in Kairouan is shown?",
                options = listOf("Zaouia Sidi Sahbi", "Tourbet El Bey", "Dar Hussein", "Al Hawa Mosque"),
                correctAnswer = "Zaouia Sidi Sahbi",
                difficulty = Difficulty.HARD
            ),
            Question(
                imageRes = R.drawable.sidi_youssef_mausoleum,
                question = "Which mausoleum is shown in this image?",
                options = listOf("Sidi Youssef Mausoleum", "Sidi Bou Makhlouf Mausoleum", "Zaouia Sidi Sahbi", "Tourbet El Bey"),
                correctAnswer = "Sidi Youssef Mausoleum",
                difficulty = Difficulty.HARD
            )
        )
    }
}
