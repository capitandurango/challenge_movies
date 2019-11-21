package com.capps.themoviedb

import com.capps.themoviedb.application.extensions.popularityRanking
import com.capps.themoviedb.domain.responses.Movie
import org.junit.Test
import org.junit.Assert.*

class MoviesExtensionTest {

    @Test
    fun popularityRankingTest() {

        val movie1 = Movie(
            popularity = 0.7,
            id = 1L, posterPath = "", title = "", overview = "", releaseDate = ""
        )

        val expected1 = "70%"

        assertEquals(
            movie1.popularityRanking(),
            expected1
        )

    }
}